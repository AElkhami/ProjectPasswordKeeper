package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.viewnotifiyers.FortressGatePresenterListener;

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

    public void checkPassword(String userPin){

        if (!userPin.equals("")) {
            if(crud.getUser(userPin)!= null
                    && crud.getUser(userPin).getPin() != null){

                String dbPin = crud.getUser(userPin).getPin();
                if (dbPin.equals(userPin)) {
                    listener.onCorrectPassword();
                }
            }else {
                listener.userMessage(Constants.Error_WRONG_PIN);
            }
        } else {
            listener.userMessage(Constants.ERROR_EMPTY_TEXT);
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
