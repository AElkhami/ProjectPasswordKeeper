package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;
import com.elkhamitech.projectkeeper.viewnotifiyers.FortressGatePresenterListener;

import javax.inject.Inject;

public class FortressGatePresenter
        implements BasePresenterContract
        , SetPresenterListener<FortressGatePresenterListener> {

    private FortressGatePresenterListener listener;
    private LocalDbRepository<UserModel, String> crud;

    @Inject
    BasePresenterImpl basePresenter;

    @Inject
    FortressGatePresenter(UserCrud crud) {
        this.crud = crud;
    }

    public void checkPassword(String userPin){

        if (!userPin.equals("")) {
            if(crud.readFromDb(userPin)!= null
                    && crud.readFromDb(userPin).getPin() != null){

                UserModel dbPin = crud.readFromDb(userPin);

                String retrievedPin = dbPin.getPin();

                if (retrievedPin.equals(userPin)) {
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
