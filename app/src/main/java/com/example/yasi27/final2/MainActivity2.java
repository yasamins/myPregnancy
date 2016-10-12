package com.example.yasi27.final2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
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

    DbHelper myDb;
    Button notes;
    Button weight;
    Button profile;
    Button myweight;
    EditText editText;
    private TextView dateView;

    Button askmydoc;
    private int year, month, day;

    String username;
    String duedate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front);
        myDb = new DbHelper(this);
        editText = (EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        duedate = intent.getStringExtra("duedate");
        System.out.println("username is " + username);
        System.out.println("duedateis : "+ duedate);
        notes = (Button)findViewById(R.id.notes);
        notes.setOnClickListener(this);
        profile = (Button)findViewById(R.id.profile);
        profile.setOnClickListener(this);
        myweight = (Button) findViewById(R.id.myweight);
        myweight.setOnClickListener(this);
        askmydoc = (Button)findViewById(R.id.askmydoc);
        askmydoc.setOnClickListener(this);




    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notes:
                Intent intent = new Intent("com.example.yasi27.final2.MainActivity3");
                startActivity(intent);
                break;

            case R.id.profile:
                Intent i = new Intent("com.example.yasi27.final2.Profile");
                startActivity(i);
//                i.putExtra("username", username);
//                i.putExtra("duedate", duedate);
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    showMessage("Error", "No Data to show");

//                    return;

                } else {

                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Username: " + res.getString(0) + "\n");
                        buffer.append("DueDate: " + res.getString(1) + "\n");
                        buffer.append("PreWeight: " + res.getString(2) + "\n");
                        buffer.append("CurrentWeight: " + res.getString(3) + "\n\n");
                    }


                    showMessage("Data", buffer.toString());
                    break;
                }


            case R.id.myweight:
                Intent intentapn = new Intent("com.example.yasi27.final2.Weight");
                intentapn.putExtra("username", username);
                intentapn.putExtra("duedate", duedate);
                startActivity(intentapn);
                break;


            case R.id.askmydoc:
                Intent intentdoc = new Intent("com.example.yasi27.final2.appointment");
                startActivity(intentdoc);
                break;


        }
    }


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
}
