package com.elkhamitech.projectkeeper.presenter;

import android.content.Context;

public interface WelcomePresenterListener {

    void userMessage(String msg);
    void onInputValidationSuccess(String validPinCode);
    void onPasswordCreatedSuccessfully();
}
