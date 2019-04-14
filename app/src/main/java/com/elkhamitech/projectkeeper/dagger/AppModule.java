package com.elkhamitech.projectkeeper.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elkhamitech.projectkeeper.data.roomdatabase.PasswordsDatabase;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.data.sharedpreferences.SharedPrefsCacheRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class AppModule {

    @Binds
    abstract CacheRepository bindCacheRepository(SharedPrefsCacheRepository prefs);

//    @Binds
//    abstract LocalDbRepository bindLocalDBRepository(UserCrud userCrud);

    @Provides
    static SharedPreferences prefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    static PasswordsDatabase db(Context context){
        return PasswordsDatabase.initDatabase(context);
    }

}
