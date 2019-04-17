package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.FortressGateNotifier;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.SetViewNotifier;

import javax.inject.Inject;

public class FortressGatePresenter
        implements SetViewNotifier<FortressGateNotifier> {

    private FortressGateNotifier listener;
    private LocalDbRepository<UserModel, String> crud;

    @Inject
    BasePresenter basePresenter;

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
    public void setListener(FortressGateNotifier listener) {
        this.listener = listener;
    }

    public void saveKeyboardType(boolean isNumericKeyboard) {
        basePresenter.saveKeyboardType(isNumericKeyboard);
    }

    public boolean getKeyboardStatus() {
        return basePresenter.getKeyboardStatus();
    }
}
