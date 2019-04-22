package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.FortressGateNotifier;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.SetViewNotifier;
import com.elkhamitech.projectkeeper.utils.accesshandler.Ciphering;

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

        String encrypted = Ciphering.encrypt(userPin, Constants.ENCRYPT_KEY);

        if (!userPin.equals("")) {
            if(crud.readFromDb(encrypted)!= null
                    && crud.readFromDb(encrypted).getPin() != null){

                UserModel dbPin = crud.readFromDb(encrypted);

                String retrievedPin = dbPin.getPin();

                if (retrievedPin.equals(encrypted)) {
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
