package com.elkhamitech.projectkeeper.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elkhamitech.projectkeeper.data.roomdatabase.PasswordsDatabase;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;
import com.elkhamitech.projectkeeper.data.sharedpreferences.SharedPrefsRepository;

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

    @Provides
    static PasswordsDatabase db(Context context){
        return PasswordsDatabase.initDatabase(context);
    }

}
