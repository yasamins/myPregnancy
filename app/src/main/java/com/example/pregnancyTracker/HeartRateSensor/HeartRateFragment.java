package com.example.pregnancyTracker.HeartRateSensor;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.pregnancyTracker.HomeActivity;
import com.example.pregnancyTracker.v1.R;
import com.mbientlab.metawear.MetaWearBleService;

public class HeartRateFragment extends Fragment {

    private boolean measuringHeartRate = false;
    private final String HEART_RATE_SENSOR_FRAGMENT_KEY = "heart_rate_sensor_key";
    private ImageButton heartButton;

    private HeartRateSensorFragment heartRateSensorFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_heart_rate_sensor, container, false);

        heartButton = (ImageButton) view.findViewById(R.id.heart_icon);
        heartButton.setOnClickListener(heartRateButtonListener);

        heartRateSensorFragment = new HeartRateSensorFragment();
        getFragmentManager().beginTransaction().add(heartRateSensorFragment, HEART_RATE_SENSOR_FRAGMENT_KEY).commit();

        view.getContext().bindService(new Intent(getActivity(), MetaWearBleService.class), (ServiceConnection) getActivity(), Context.BIND_AUTO_CREATE);

        return view;
    }

    private View.OnClickListener heartRateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            HomeActivity activity = (HomeActivity) getActivity();
            Log.i("Heart button", "Button clicked");
            if (!activity.isConnected()) {
                Log.i("Heart button", "Metawear not connected");
                return;
            }
            if (!measuringHeartRate) {
                Log.i("Heart button", "Starting sensor");
                heartRateSensorFragment.startSensor(activity.getMwBoard());
                measuringHeartRate = true;
            } else {
                Log.i("Heart button", "Stopping sensor");
                heartRateSensorFragment.stopSensor();
                measuringHeartRate = false;
            }
        }
    };

}
