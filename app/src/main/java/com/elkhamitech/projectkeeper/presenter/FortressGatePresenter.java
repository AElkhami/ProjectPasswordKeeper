package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import javax.inject.Inject;

public class FortressGatePresenter extends BasePresenter<FortressGatePresenterListener> {

    private UserCrud crud;

    @Inject
    FortressGatePresenter(Repository repository, UserCrud crud) {
        super(repository, crud);
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

}
