package com.example.pregnancyTracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.pregnancyTracker.Model.User;
import com.example.pregnancyTracker.v1.R;

import io.realm.Realm;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        // Splash screen timer
        int SPLASH_TIME_OUT = 2000;

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer.
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over

                Realm realm = Realm.getDefaultInstance();
                Intent i;
                if (realm.where(User.class).count() > 0) {
                    i = new Intent(SplashScreen.this, HomeActivity.class);
                } else {
                    i = new Intent(SplashScreen.this, NewUserActivity.class);
                }
                startActivity(i);
                overridePendingTransition(0, 0);


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
