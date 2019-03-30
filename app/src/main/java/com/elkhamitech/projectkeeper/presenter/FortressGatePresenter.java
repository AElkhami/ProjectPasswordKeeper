package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;

import javax.inject.Inject;

public class FortressGatePresenter
        implements BasePresenterContract
        , SetPresenterListener<FortressGatePresenterListener> {

    private UserCrud crud;
    private FortressGatePresenterListener listener;

    @Inject
    BasePresenterImpl basePresenter;

    @Inject
    FortressGatePresenter(UserCrud crud) {
        this.crud = crud;
    }

    public void checkPassword(String userPin) {

        if (!userPin.equals("")) {
            String dbPin = getUserPin(userPin);
            if (!dbPin.equals(userPin)) {
                listener.userMessage(Constants.Error_WRONG_PIN);
            } else {
                listener.onCorrectPassword();
            }
        } else {
            listener.userMessage(Constants.ERROR_EMPTY_TEXT);
        }
    }

    private String getUserPin(String pinCode) {

        UserModel user = crud.getUser(pinCode);

        if (user == null) {
            //todo improve security.
            return "";
        } else {
            return user.getPin();
        }
    }

    @Override
    public void setListener(FortressGatePresenterListener listener) {
        this.listener = listener;
    }

    @Override
    public void saveKeyboardType(boolean isNumericKeyboard) {
        basePresenter.saveKeyboardType(isNumericKeyboard);
    }

    @Override
    public boolean getKeyboardStatus() {
        return basePresenter.getKeyboardStatus();
    }
}
