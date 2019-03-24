package com.elkhamitech.data.sharedpreferences;

import android.content.SharedPreferences;

import java.util.HashMap;

import javax.inject.Inject;

public class SharedPrefsRepository implements Repository{

    private static final String PREF_NAME = "Preference";

    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_PIN = "IsPin";
    public static final String KEY_ID = "Id";
    public static final String KEY_PIN = "Pin";
    public static final String KEYBOARD_TYPE = "KeyboardType";


    private final SharedPreferences prefs;

    @Inject
    SharedPrefsRepository(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public  void createLoginSession(long id, String pin) {
        // Storing login value as TRUE
        prefs.edit().putBoolean(IS_LOGIN, true).apply();

        prefs.edit().putLong(KEY_ID, id).apply();
        prefs.edit().putString(KEY_PIN, pin).apply();

    }

    public  void createKeyboardType( boolean keyboardType) {
        prefs.edit().putBoolean(KEYBOARD_TYPE, keyboardType).apply();
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

        user.put(KEYBOARD_TYPE, prefs.getBoolean(KEYBOARD_TYPE, true));

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
