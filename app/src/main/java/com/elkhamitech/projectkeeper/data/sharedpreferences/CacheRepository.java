package com.elkhamitech.projectkeeper.data.sharedpreferences;

import java.util.HashMap;

public interface CacheRepository {

    void createLoginSession(long id, String pin);
    void createKeyboardType( boolean keyboardType);
    HashMap<String, Long> getRowDetails();
    HashMap<String, String> getUserDetails();
    HashMap<String, Boolean> getKeyboardDetails();
    void logoutUser();
    boolean isLoggedIn();
    boolean isPin();

}
