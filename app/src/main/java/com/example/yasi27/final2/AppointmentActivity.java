package com.example.yasi27.final2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


/**
 * Created by yasi27 on 3.10.2016.
 */
public class AppointmentActivity extends Activity {


    Button reminder;
    TextView info;

    final static int RQS_1 = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);



        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute1 = c.get(Calendar.MINUTE);
        final EditText txtTime = (EditText) findViewById(R.id.edittime);
        final EditText txtDate = (EditText) findViewById(R.id.editdate);
        info = (TextView) findViewById(R.id.info);
        reminder = (Button) findViewById(R.id.reminder);
//        setAlarm(c);

        Intent intentalarm = new Intent(this, Receiver.class);
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intentalarm, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        txtTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TimePickerDialog timepick = new TimePickerDialog(AppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay + ":" + minute);


                    }
                }, hour, minute1, true
                );

                timepick.setTitle("Select Time");
                timepick.show();
            }

        });


        txtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker = new DatePickerDialog(AppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtDate.setText(year + " - " + month + " - " + day);

                    }
                }, year, month, day
                );
                datepicker.setTitle("Select Date");
                datepicker.show();
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarm(c);

            }
        });


        }

    private void setAlarm(Calendar c){

        info.setText("\n\n***\n"
                + "Alarm is set@ " + c.getTime() + "\n"
                + "***\n");
    }


    }


