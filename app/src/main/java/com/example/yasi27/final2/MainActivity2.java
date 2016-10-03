package com.example.yasi27.final2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by yasi27 on 26.9.2016.
 */
public class MainActivity2 extends Activity implements View.OnClickListener{
    public static final int WEEKS_IN_PREGNANCY = 40;
    public static final int DAYS_IN_PREGNNCY = WEEKS_IN_PREGNANCY * 7;
    TextView countdown;
    Button notes;
    Button weight;
    Button bellysize;
    Button appointment;
    private int year, month, day;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front);

        notes = (Button)findViewById(R.id.notes);
        notes.setOnClickListener(this);
        bellysize = (Button)findViewById(R.id.bellysize);
        bellysize.setOnClickListener(this);
        appointment = (Button)findViewById(R.id.appointment);
        appointment.setOnClickListener(this);
//        countdown = (TextView)findViewById(R.id.countdown);

//        Bundle extras = getIntent().getExtras();
//
//        int year = extras.getInt("year");
//        int month = extras.getInt("month");
//        int day = extras.getInt("day");

//        String dateSet = dateData.getString("Date");
//        countdown.setText(year +  month + day );
//        OnClickListener();

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.notes:
                Intent intent = new Intent("com.example.yasi27.final2.MainActivity3");
                startActivity(intent);
                break;

            case R.id.bellysize:
                Intent i = new Intent("com.example.yasi27.final2.bellysize");
                startActivity(i);
                break;

            case R.id.appointment:
                Intent intentapn = new Intent("com.example.yasi27.final2.appointment");
                startActivity(intentapn);
                break;
        }
    }


//    public void OnClickListener(){
//
//        notes = (Button)findViewById(R.id.notes);
//        notes.setOnClickListener(
//                new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent("com.example.yasi27.final2.MainActivity3");
//                        startActivity(intent);
//                    }
//                }
//
//        );
//
//
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
}
