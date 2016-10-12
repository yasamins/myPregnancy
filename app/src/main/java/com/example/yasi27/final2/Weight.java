package com.example.yasi27.final2;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by yasi27 on 5.10.2016.
 */
public class Weight extends Activity {

    DbHelper myDb;
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
        setContentView(R.layout.myweight);
        Intent intentapn = getIntent();
        username = intentapn.getStringExtra("username");
        duedate = intentapn.getStringExtra("duedate");
        System.out.println("username  " + username);
        System.out.println("duedate : "+ duedate);
        myDb = new DbHelper(this);
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
                        Intent intentweight = new Intent();
                        intentweight.putExtra("username", preweight.getText().toString());
                        intentweight.putExtra("duedate", currentweight.getText().toString());
                        boolean isInserted = myDb.insertData(username,duedate,preweight.getText().toString(), currentweight.getText().toString());
                        System.out.println("the result is:" + isInserted);
//                        String pweight = preweight.getText().toString();
//                        String cweight = currentweight.getText().toString();
                        if (preweight == null || currentweight == null) {
                            Toast.makeText(Weight.this, "please fill in the blank part", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(Weight.this, "Your data has been saved", Toast.LENGTH_LONG).show();


                        }
                    }

                });
    }
}