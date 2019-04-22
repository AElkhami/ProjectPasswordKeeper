package com.elkhamitech.projectkeeper;

public class Constants {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getTransformationFromJNI();

    public final static String TRANSFORMATION = getTransformationFromJNI();

    public static native String getAlgorithmFromJNI();

    public final static String ALGORITHM = getAlgorithmFromJNI();

    public static native String getEncodingFromJNI();

    public final static String ENCODING = getEncodingFromJNI();

    public static native String getEncryptKeyFromJNI();

    public final static String ENCRYPT_KEY = getEncryptKeyFromJNI();

    public static final String BACKUP_RESTORED = "Passwords have been restored," +
            " Thanks to re-enter the pin to confirm the passwords are yours.";
    public static final String ERROR_EMPTY_TEXT = "Please Fill all the Required Filed.";
    public static final String ERROR_PASSWORD_LENGTH = "Your Pin must contain more than 6 characters.";
    public static final String SEED = "I don't know what is this";
    public static final String PERMISSION_DENIED = "Permission denied, you can go to setting to enable it.";
    public static final int SPLASH_DISPLAY_LENGTH = 2000;
    public static final String Error_WRONG_PIN = "Pin code is incorrect.";
    public static final String KEYBOARD_TYPE = "KEYBOARD_TYPE";
    public static final String ERROR_DATA_RETRIEVE = "Error: Couldn't retrieve your data, Please try again later";
    public static final String SUCCESS_DELETE = "Entry has been deleted";
    public static final String SUCCESS_EDIT = "Entry has been Edited";
    public static final String DATE_FORMAT_PATTERN = "dd-MMM-yyyy HH:mm";
}
