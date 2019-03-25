package com.elkhamitech.projectkeeper.presenter;

public interface WelcomePresenterListener extends BasePresenterListener {
    void onInputValidationSuccess(String validPinCode);
    void onPasswordCreatedSuccessfully();
}
