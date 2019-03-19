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

    private static SharedPreferences prefInstance;

    private static final String PREF_NAME = "Preference";

    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_PIN = "IsPin";
    public static final String KEY_ID = "Id";
    public static final String KEY_EMAIL = "Email";
    public static final String KEYBOARD_TYPE = "KeyboardType";


    public static void initPref(Context context) {
        prefInstance = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    }

    public static SharedPreferences getPrefs(){
        return prefInstance;
    }

    /**
     * Create login session
     **/
    public static void createLoginSession(long _id, String _email) {
        // Storing login value as TRUE
        prefInstance.edit().putBoolean(IS_LOGIN, true).apply();

        prefInstance.edit().putLong(KEY_ID, _id).apply();
        prefInstance.edit().putString(KEY_EMAIL, _email).apply();

    }

    public static void createKeyboardType( boolean _keyboardType) {
        prefInstance.edit().putBoolean(KEYBOARD_TYPE, _keyboardType).apply();
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

    public static HashMap<String, Long> getRowDetails(){
        HashMap<String, Long> user = new HashMap<>();

        user.put(KEY_ID, prefInstance.getLong(KEY_ID, 200L));

        return user;
    }
    public static HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_EMAIL, prefInstance.getString(KEY_EMAIL, null));

        return user;
    }

    public static HashMap<String, Boolean> getKeyboardDetails(){
        HashMap<String, Boolean> user = new HashMap<>();

        user.put(KEYBOARD_TYPE, prefInstance.getBoolean(KEYBOARD_TYPE, true));

        return user;
    }


    /**
     * Clear session details
     * */
    public static void logoutUser(){
        // Clearing all data from Shared Preferences
        prefInstance.edit().clear().apply();
    }


    // Get Login State
    public static boolean isLoggedIn() {
        return prefInstance.getBoolean(IS_LOGIN, false);
    }

    public boolean isPin() {
        return prefInstance.getBoolean(IS_PIN, false);
    }
}






