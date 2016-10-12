package com.example.yasi27.final2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
import java.sql.SQLOutput;

public class HomeActivity extends Activity implements View.OnClickListener{

    public static final int WEEKS_IN_PREGNANCY = 40;
    public static final int DAYS_IN_PREGNNCY = WEEKS_IN_PREGNANCY * 7;

    Button articles;
    Button weight;
    Button calendar;
    Button heartRate;
    private int year, month, day;
    TextView countdown;
    EditText editText;
    private TextView dateView;
    String username;
    String duedate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        articles = (Button)findViewById(R.id.articles);
        articles.setOnClickListener(this);
        calendar = (Button)findViewById(R.id.calendar);
        calendar.setOnClickListener(this);
        weight = (Button)findViewById(R.id.weight);
        weight.setOnClickListener(this);
        heartRate = (Button)findViewById(R.id.heartRate);
        heartRate.setOnClickListener(this);

        editText = (EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        duedate = intent.getStringExtra("duedate");
        System.out.println("username is " + username);
        System.out.println("duedateis : "+ duedate);

//
//        Bundle extras = getIntent().getExtras();
//        String newString = extras.getString("dDate");
//        countdown.setText(newString);
//
//        countdown = (TextView)findViewById(R.id.countdown);
//
//        Bundle extras = getIntent().getExtras();
////
//        int year = extras.getInt("year");
//        int month = extras.getInt("month");
//        int day = extras.getInt("day");
//        countdown.setText(year + month + day);

//        String dateSet = dateData.getString("Date");
//        countdown.setText(year +  month + day );
//        OnClickListener();

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.articles:
                Intent readArticles = new Intent("com.example.yasi27.final2.ArticlesActivity");
                startActivity(readArticles);
                break;

            case R.id.heartRate:
                Intent checkHeartRate = new Intent("com.example.yasi27.final2.HeartRateActivity");
                startActivity(checkHeartRate);
                break;

            case R.id.calendar:
                Intent showCalendar = new Intent("com.example.yasi27.final2.CalendarActivity");
                startActivity(showCalendar);
                break;

            case R.id.weight:
                Intent intentapn = new Intent("com.example.yasi27.final2.WeightActivity");
                intentapn.putExtra("username", username);
                intentapn.putExtra("duedate", duedate);
                startActivity(intentapn);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
}
