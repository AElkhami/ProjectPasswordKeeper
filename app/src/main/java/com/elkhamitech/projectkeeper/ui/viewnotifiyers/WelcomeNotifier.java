package com.elkhamitech.projectkeeper.ui.viewnotifiyers;

public interface WelcomeNotifier extends BaseNotifier {
    void onInputValidationSuccess(String validPinCode);
    void onPasswordCreatedSuccessfully();
}
