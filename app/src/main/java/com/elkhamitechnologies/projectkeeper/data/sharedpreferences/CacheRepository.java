package com.elkhamitechnologies.projectkeeper.data.sharedpreferences;

import java.util.HashMap;

public interface CacheRepository {

    void createLoginSession(long id, String pin);
    void setKeyboardType(boolean keyboardType);
    HashMap<String, Long> getUserId();
    HashMap<String, Boolean> getKeyboardType();
    boolean isLoggedIn();
}
