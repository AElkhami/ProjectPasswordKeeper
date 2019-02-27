package com.elkhamitech.projectkeeper.utils.AccessHandler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import com.elkhamitech.projectkeeper.ui.Activities.MainActivity;
import com.elkhamitech.projectkeeper.ui.Activities.WelcomeActivity;

import java.util.HashMap;

/**
 * Created by Ahmed on 1/10/2017.
 */

public class SessionManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    //Shared Prefrence File Name
    private static final String PREF_NAME = "PrefrenceUser";
    //Shared Prefrence Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_PIN = "IsPin";
    public static final String KEY_ID = "Id";
    public static final String KEY_EMAIL = "Email";
    public static final String KEYBOARD_TYPE = "KeyboardType";

    //Constructor
    public SessionManager(Context context) {
        this._context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /**
     * Create login session
     **/
    public void createLoginSession(long _id, String _email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putLong(KEY_ID, _id);
        editor.putString(KEY_EMAIL, _email);

        //Commit changes
        editor.commit();
    }

    public void createKeyboardType( boolean _keyboardType) {
        // Storing login value as TRUE
        editor.putBoolean(KEYBOARD_TYPE, _keyboardType);

        //Commit changes
        editor.commit();
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */

    public void checkLogin() {

        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, WelcomeActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

        }else {

            Intent e = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            e.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(e);
        }
    }

    public void checkKeyboard(EditText edt) {

        if (!this.isPin()) {

            edt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            edt.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }else {
            edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }

    /**
     * Get stored session data
     * */

    public HashMap<String, Long> getRowDetails(){
        HashMap<String, Long> user = new HashMap<String, Long>();

        user.put(KEY_ID, preferences.getLong(KEY_ID, 200L));

        return user;
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));

        return user;
    }

    public HashMap<String, Boolean> getKeyboardDetails(){
        HashMap<String, Boolean> user = new HashMap<String, Boolean>();

        user.put(KEYBOARD_TYPE, preferences.getBoolean(KEYBOARD_TYPE, true));

        return user;
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent w = new Intent(_context, WelcomeActivity.class);
        // Closing all the Activities
        w.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        w.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(w);

    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public boolean isPin() {
        return preferences.getBoolean(IS_PIN, false);
    }
}






