package com.elkhamitech.projectkeeper.viewnotifiyers;

import com.elkhamitech.projectkeeper.presenter.BasePresenterListener;

public interface WelcomePresenterListener extends BasePresenterListener {
    void onInputValidationSuccess(String validPinCode);
    void onPasswordCreatedSuccessfully();
}
