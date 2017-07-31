package com.elkhamitech.projectkeeper;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    SessionManager session;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session = new SessionManager(getApplicationContext());


        TextView tv = (TextView) findViewById(R.id.splash_text);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "Audiowide-Regular.ttf");
        tv.setTypeface(face);

            splashRunner();



    }


    public void splashRunner(){
		/*
		 * New Handler to start the Menu-Activity and close this Splash-Screen
		 * after some seconds.
		 */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
				/* Create an Intent that will start the Menu-Activity. */
                session.checkLogin();
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
