package com.elkhamitech;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.elkhamitech.data.PasswordsDatabase;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PasswordsDatabase.initDatabase(this);
        SessionManager.initPref(this);
    }
}
