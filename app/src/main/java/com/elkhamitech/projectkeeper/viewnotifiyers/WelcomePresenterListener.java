package com.elkhamitech.projectkeeper.viewnotifiyers;

public interface WelcomePresenterListener extends BasePresenterListener {
    void onInputValidationSuccess(String validPinCode);
    void onPasswordCreatedSuccessfully();
}
