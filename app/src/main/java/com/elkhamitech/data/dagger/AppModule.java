package com.elkhamitech.data.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elkhamitech.data.sharedpreferences.Repository;
import com.elkhamitech.data.sharedpreferences.SharedPrefsRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class AppModule {

    @Binds
    abstract Repository bindRepository(SharedPrefsRepository prefs);

    @Provides
    static SharedPreferences prefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
