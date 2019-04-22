package com.elkhamitech.projectkeeper.data.sharedpreferences;

import android.content.SharedPreferences;

import com.elkhamitech.projectkeeper.Constants;

import java.util.HashMap;

import javax.inject.Inject;

public class SharedPrefsCacheRepository implements CacheRepository {

    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_ID = "Id";

    private final SharedPreferences prefs;

    @Inject
    SharedPrefsCacheRepository(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public  void createLoginSession(long id, String pin) {
        // Storing login value as TRUE
        prefs.edit().putBoolean(IS_LOGIN, true).apply();
        prefs.edit().putLong(KEY_ID, id).apply();
    }

    @Override
    public void setKeyboardType(boolean keyboardType) {
        prefs.edit().putBoolean(Constants.KEYBOARD_TYPE, keyboardType).apply();
    }

    @Override
    public HashMap<String, Long> getUserId(){
        HashMap<String, Long> user = new HashMap<>();
        user.put(KEY_ID, prefs.getLong(KEY_ID, 200L));

        return user;
    }

    @Override
    public HashMap<String, Boolean> getKeyboardType(){
        HashMap<String, Boolean> user = new HashMap<>();
        user.put(Constants.KEYBOARD_TYPE, prefs
                .getBoolean(Constants.KEYBOARD_TYPE, true));

        return user;
    }

    @Override
    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }

}
