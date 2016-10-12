package com.example.yasi27.final2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by yasi27 on 7.10.2016.
 */
public class Profile extends Activity {

    TextView profinfo;
    String username;
    String duedate;
    String preweight;
    String currentweight;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        System.out.println("print this");
        setContentView(R.layout.profile);
        Intent i = getIntent();
        username = i.getStringExtra("username");
        duedate = i.getStringExtra("duedate");
        Intent intentweight = getIntent();
        preweight = intentweight.getStringExtra("preweight");
        currentweight = intentweight.getStringExtra("currentweight");


        profinfo = (TextView)findViewById(R.id.profinfo);
        profinfo.setText(username);
        profinfo.setText(duedate);
//        System.out.println("profinfo is:" + profinfo);
    }
}
