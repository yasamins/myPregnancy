package com.example.yasi27.final2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLOutput;

/**
 * Created by yasi27 on 26.9.2016.
 */
public class MainActivity2 extends Activity implements View.OnClickListener{

    TextView countdown;
    Button notes;
    Button weight;
    Button bellysize;
    Button myweight;
    EditText editText;
    private TextView dateView;

    Button appointment;
    private int year, month, day;

    String username;
    String duedate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front);
        editText = (EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
//        Intent intent1 = getIntent();
        duedate = intent.getStringExtra("duedate");
        System.out.println("username is " + username);
        System.out.println("duedateis : "+ duedate);
        notes = (Button)findViewById(R.id.notes);
        notes.setOnClickListener(this);
        bellysize = (Button)findViewById(R.id.bellysize);
        bellysize.setOnClickListener(this);
        myweight = (Button) findViewById(R.id.myweight);
        myweight.setOnClickListener(this);

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
            case R.id.notes:
                Intent intent = new Intent("com.example.yasi27.final2.MainActivity3");
                startActivity(intent);
                break;

            case R.id.bellysize:
                Intent i = new Intent("com.example.yasi27.final2.bellysize");
                startActivity(i);
                break;

            case R.id.myweight:
                Intent intentapn = new Intent("com.example.yasi27.final2.Weight");
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
