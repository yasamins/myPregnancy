package com.example.yasi27.final2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.mbientlab.bletoolbox.scanner.BleScannerFragment;
import com.mbientlab.metawear.MetaWearBleService;
import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.module.Debug;

import java.util.UUID;

public class HeartRateActivity extends AppCompatActivity implements ServiceConnection,
        BleScannerFragment.ScannerCommunicationBus, MWDeviceConfirmFragment.DeviceConfirmCallback {

    private MetaWearBleService.LocalBinder mwBinder = null;
    private BluetoothDevice bluetoothDevice;
    private boolean btDeviceSelected;
    private MetaWearBoard mwBoard;
    private MWScannerFragment mwScannerFragment;
    private Menu menu;
    private SharedPreferences sharedPreferences;
    private Editor editor;
    private HeartRateSensorFragment heartRateSensorFragment;
    private boolean connected = false;
    private boolean measuringHeartRate = false;
    private boolean reconnecting = false;
    private final String HEART_RATE_SENSOR_FRAGMENT_KEY = "heart_rate_sensor_key";
    private final String MAC_ADDRESS = "MAC_ADDRESS";
    private ImageButton heartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        heartButton = (ImageButton) findViewById(R.id.heart_icon);
        heartButton.setOnClickListener(heartRateButtonListener);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.yasi27.final2", 0); // 0 - for private mode
        editor = sharedPreferences.edit();
        connected = false;

        if (savedInstanceState == null) {
            heartRateSensorFragment = new HeartRateSensorFragment();
            getFragmentManager().beginTransaction().add(heartRateSensorFragment, HEART_RATE_SENSOR_FRAGMENT_KEY).commit();
        } else {
            heartRateSensorFragment = (HeartRateSensorFragment) getFragmentManager().getFragment(savedInstanceState, HEART_RATE_SENSOR_FRAGMENT_KEY);
        }

        String macAddress = sharedPreferences.getString(MAC_ADDRESS, "");

        if (!macAddress.equals("")) {

            final BluetoothManager btManager =
                    (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothDevice =
                    btManager.getAdapter().getRemoteDevice(macAddress);
            reconnecting = true;
        }

        getApplicationContext().bindService(new Intent(this, MetaWearBleService.class), this, Context.BIND_AUTO_CREATE);
        Log.i("OnCreate", "done with calls");
        Log.i("MacAddress", macAddress + " foo ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.heart_rate_menu, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_connect) {
            if (mwBoard != null) {
                disconnectAdapter();
            } else {
                if (mwScannerFragment != null) {
                    Fragment metawearBlescannerPopup = getFragmentManager().findFragmentById(R.id.metawear_blescanner_popup_fragment);
                    if (metawearBlescannerPopup != null) {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.remove(metawearBlescannerPopup);
                        fragmentTransaction.commit();
                    }
                    mwScannerFragment.dismiss();
                }
                mwScannerFragment = new MWScannerFragment();
                mwScannerFragment.show(getFragmentManager(), "metawear_scanner_fragment");
                return true;
            }

        } else if (id == R.id.action_clear_log) {
            if (mwBoard != null) {
                reconnecting = true;
                mwBoard.disconnect();
            }
        } else if (id == R.id.action_reset_device) {
            try {
                mwBoard.getModule(Debug.class).resetDevice();
                setStatusToDisconnected();
                mwBoard = null;
            } catch (Exception e) {
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                disconnectAdapter();
                                Toast.makeText(getApplicationContext(), R.string.error_soft_reset, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Service Connection methods
     *
     * @param name
     * @param service
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        ///< Get a reference to the MetaWear service from the binder
        Log.i("onServiceConnected", service == null ? "null" : service.toString());
        mwBinder = (MetaWearBleService.LocalBinder) service;
        if (reconnecting) {
            connectDevice(bluetoothDevice);
        }
    }

    ///< Don't need this callback method but we must implement it
    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

    /**
     * callbacks for Bluetooth device scan
     */
    @Override
    public void onDeviceSelected(BluetoothDevice device) {
        bluetoothDevice = device;
        btDeviceSelected = true;
        connectDevice(device);
        Fragment metawearBlescannerPopup = getFragmentManager().findFragmentById(R.id.metawear_blescanner_popup_fragment);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(metawearBlescannerPopup);
        fragmentTransaction.commit();
        mwScannerFragment.dismiss();
    }

    @Override
    public UUID[] getFilterServiceUuids() {
        ///< Only return MetaWear boards in the scan
        return new UUID[]{UUID.fromString("326a9000-85cb-9195-d9dd-464cfbbae75a")};
    }

    @Override
    public long getScanDuration() {
        ///< Scan for 10000ms (10 seconds)
        return 10000;
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        if (mwBoard != null) {
            editor.putString(MAC_ADDRESS, bluetoothDevice.getAddress());
            editor.apply();
            editor.commit();
            mwBoard.disconnect();
        }
        super.onSaveInstanceState(state);
    }

    @Override
    protected void onResume() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
        }
        else if (mwBoard != null) {
            mwBoard.connect();
        }
        super.onResume();
    }

    /**
     * Device confirmation callbacks and helper methods
     */

    public void pairDevice() {
        setStatusToConnected();
//        heartRateSensorFragment.startSensor(mwBoard);
    }

    public void dontPairDevice() {
        mwBoard.disconnect();
        bluetoothDevice = null;
        mwScannerFragment.show(getFragmentManager(), "metawear_scanner_fragment");
    }


    /**
     * private helper method for device connection logic
     */
    private void connectDevice(BluetoothDevice device) {
        Log.i("connectedDevice", device == null ? "null" : device.getName());
        mwBoard = mwBinder.getMetaWearBoard(device);

        if (mwBoard != null) {
            mwBoard.setConnectionStateHandler(connectionStateHandler);
            mwBoard.connect();
        }
    }

    /**
     * local helper method for disconnecting from a board
     */
    private void disconnectAdapter() {
        mwBoard.disconnect();
        mwBoard = null;
        setStatusToDisconnected();
    }


    public void setStatusToConnected() {
        MenuItem connectMenuItem = menu.findItem(R.id.action_connect);
        connectMenuItem.setTitle(R.string.disconnect);
        TextView connectionStatus = (TextView) findViewById(R.id.connection_status);
        connectionStatus.setText(getText(R.string.metawear_connected));
        connected = true;
    }

    private void setStatusToDisconnected() {
        MenuItem connectMenuItem = menu.findItem(R.id.action_connect);
        connectMenuItem.setTitle(R.string.connect);
        TextView connectionStatus = (TextView) findViewById(R.id.connection_status);
        connectionStatus.setText(getText(R.string.no_metawear_connected));
        editor.remove(MAC_ADDRESS);
        editor.apply();
        editor.commit();
        reconnecting = false;
        connected = false;
    }

    private View.OnClickListener heartRateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i("Heart button", "Button clicked");
            if (!connected) {
                Log.i("Heart button", "Metawear not connected");
                return;
            }
            if (!measuringHeartRate) {
                Log.i("Heart button", "Starting sensor");
                heartRateSensorFragment.startSensor(mwBoard);
                measuringHeartRate = true;
            } else {
                Log.i("Heart button", "Stopping sensor");
                heartRateSensorFragment.stopSensor();
                measuringHeartRate = false;
            }
        }
    };

    /**
     * connection handlers
     */
    private MetaWearBoard.ConnectionStateHandler connectionStateHandler = new MetaWearBoard.ConnectionStateHandler() {
        @Override
        public void connected() {
            Log.i("Metawear Controller", "Device Connected");
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  Toast.makeText(getApplicationContext(), R.string.toast_connected, Toast.LENGTH_SHORT).show();
                              }
                          }
            );
            if (btDeviceSelected) {
                MWDeviceConfirmFragment mwDeviceConfirmFragment = new MWDeviceConfirmFragment();
                mwDeviceConfirmFragment.flashDeviceLight(mwBoard, getFragmentManager());
                btDeviceSelected = false;
            } else if (reconnecting) {
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      setStatusToConnected();
                                  }
                              }
                );
//                heartRateSensorFragment.startSensor(mwBoard);
                reconnecting = false;
            }

        }

        @Override
        public void disconnected() {
            Log.i("Metawear Controller", "Device Disconnected");
            if (reconnecting) {
                mwBoard.connect();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), R.string.toast_disconnected, Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void failure(int status, Throwable error) {
            if (mwBoard != null) {
                mwBoard.connect();
            }
            Log.i("Failure", "Connection Failed");
        }
    };

}
