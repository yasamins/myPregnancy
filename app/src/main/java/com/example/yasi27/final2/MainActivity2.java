package com.example.yasi27.final2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by yasi27 on 26.9.2016.
 */
public class MainActivity2 extends Activity {
    Button notes;
    Button weight;
    Button bellysize;
    Button appointment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front);
        OnClickListener();
    }

    public void OnClickListener(){

        notes = (Button)findViewById(R.id.notes);
        notes.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.yasi27.final2.MainActivity3");
                        startActivity(intent);
                    }
                }
        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
}
