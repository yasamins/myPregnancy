package com.example.yasi27.final2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WeightActivity extends Activity {

    DatabaseHelper myDb;
    Button save;
    Button cancel;
    EditText startdate;
    EditText preweight;
    EditText currentweight;
    String username;
    String duedate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        Intent intentapn = getIntent();
        username = intentapn.getStringExtra("username");
        duedate = intentapn.getStringExtra("duedate");
        System.out.println("username  " + username);
        System.out.println("duedate : "+ duedate);
        myDb = new DatabaseHelper(this);
        OnClickListener();

        preweight = (EditText) findViewById(R.id.preweight);
        currentweight = (EditText) findViewById(R.id.currentweight);

    }


    public void OnClickListener() {
        cancel = (Button) findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preweight.setText("");
                currentweight.setText("");


            }
        });

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.insertWeight(username, preweight.getText().toString(), currentweight.getText().toString());
//                        boolean isInserted = myDb.insertData(username,duedate,preweight.getText().toString(), currentweight.getText().toString());
//                        System.out.println("the result is:" + isInserted);
//                        String pweight = preweight.getText().toString();
//                        String cweight = currentweight.getText().toString();
//                        if (pweight.matches("") || cweight.matches("")) {
//                            Toast.makeText(WeightActivity.this, "please fill in the blank part", Toast.LENGTH_SHORT).show();
//                            return;
//                        } else {
//                            Toast.makeText(WeightActivity.this, "Your data has been saved", Toast.LENGTH_LONG).show();
//
//
//                        }
                    }

                });
    }
}