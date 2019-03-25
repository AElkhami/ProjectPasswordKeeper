package com.elkhamitech.projectkeeper;

import android.app.Application;

import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        SessionManager.initPref(this);
    }
}
