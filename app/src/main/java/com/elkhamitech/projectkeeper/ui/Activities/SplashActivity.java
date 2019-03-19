package com.elkhamitech.projectkeeper.ui.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.elkhamitech.Constants;
import com.elkhamitech.MyApplication;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        TextView tv = findViewById(R.id.splash_text);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "Audiowide-Regular.ttf");
        tv.setTypeface(face);

            splashRunner();

    }


    public void splashRunner(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                checkLogin();

                finish();
            }
        }, Constants.SPLASH_DISPLAY_LENGTH);
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {

        // Check login status
        if (!SessionManager.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(this, WelcomeActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);

        }else {

            Intent e = new Intent(this, MainActivity.class);
            // Closing all the Activities
            e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            e.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(e);
        }
    }
}
