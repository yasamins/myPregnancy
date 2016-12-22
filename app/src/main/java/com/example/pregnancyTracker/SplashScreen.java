package com.example.pregnancyTracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.pregnancyTracker.Model.User;

import io.realm.Realm;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Splash screen timer
        int SPLASH_TIME_OUT = 1000;

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer.
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over

                Realm realm = Realm.getDefaultInstance();
                if (realm.where(User.class).count() > 0) {
                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashScreen.this, NewUserActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
