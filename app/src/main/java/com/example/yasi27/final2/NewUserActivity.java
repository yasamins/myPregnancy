package com.example.yasi27.final2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper myDb;
    EditText editText;
    Button start;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    TextView countdown;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        //its going to call the constructor of this databasehelper class and in the contructor we are creating the database and the tables
        OnClickListener();

        editText = (EditText)findViewById(R.id.editText);
        start = (Button)findViewById(R.id.start);
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setTypeface(myTypeface);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void OnClickListener(){

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        now we put the insertData method here using the instance of the datahelper class and we will use boolean value because we used it for inserData method
//                        boolean isInserted = myDb.insertData(editText.getText().toString(), dateView.getText().toString());
                        boolean isInserted = myDb.insertData(editText.getText().toString() , dateView.getText().toString() , "" , "");
                        System.out.println("save result :" + isInserted);
                        String sUsername = editText.getText().toString();
                        String dDate = dateView.getText().toString();
                        if (sUsername.matches("") || dDate.matches("")) {
                            Toast.makeText(NewUserActivity.this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(NewUserActivity.this,"Data has been saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent("com.example.yasi27.final2.HomeActivity");
//                            intent.putExtra("day",day);
//                            intent.putExtra("month", month);
//                            intent.putExtra("year", year);
//                            intent.putExtra("dDate", dDate);
                            intent.putExtra("username", editText.getText().toString());
                            intent.putExtra("duedate", dateView.getText().toString());
                            startActivity(intent);
                        }

//                        if (isInserted == true)
//                            Toast.makeText(NewUserActivity.this,"Data has been saved", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(NewUserActivity.this,"Data not saved", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent("com.example.yasi27.final2.HomeActivity");
//                        startActivity(intent);
//                        countdown.setText(year+"-"+month+"-"+day);

                        }



                }
        );
    }



    public void DueDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}