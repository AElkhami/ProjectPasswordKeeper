package com.elkhamitech.projectkeeper.data.sharedpreferences;

import android.content.SharedPreferences;

import com.elkhamitech.projectkeeper.Constants;

import java.util.HashMap;

import javax.inject.Inject;

public class SharedPrefsCacheRepository implements CacheRepository {

    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_PIN = "IsPin";
    private static final String KEY_ID = "Id";
    private static final String KEY_PIN = "Pin";

    private final SharedPreferences prefs;

    @Inject
    SharedPrefsCacheRepository(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public  void createLoginSession(long id, String pin) {
        // Storing login value as TRUE
        prefs.edit().putBoolean(IS_LOGIN, true).apply();

        prefs.edit().putLong(KEY_ID, id).apply();
        prefs.edit().putString(KEY_PIN, pin).apply();

    }

    public void createKeyboardType( boolean keyboardType) {
        prefs.edit().putBoolean(Constants.KEYBOARD_TYPE, keyboardType).apply();
    }

    public HashMap<String, Long> getRowDetails(){
        HashMap<String, Long> user = new HashMap<>();

        user.put(KEY_ID, prefs.getLong(KEY_ID, 200L));

        return user;
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_PIN, prefs.getString(KEY_PIN, null));

        return user;
    }

    public HashMap<String, Boolean> getKeyboardDetails(){
        HashMap<String, Boolean> user = new HashMap<>();

        user.put(Constants.KEYBOARD_TYPE, prefs
                .getBoolean(Constants.KEYBOARD_TYPE, true));

        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        prefs.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }

    public boolean isPin() {
        return prefs.getBoolean(IS_PIN, false);
    }

}
