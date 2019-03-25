package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import javax.inject.Inject;

public class FortressGatePresenter extends BasePresenter {

    private FortressGatePresenterListener listener;
    private Repository repository;
    private UserCrud crud;

    @Inject
    FortressGatePresenter(Repository repository, UserCrud crud) {
        this.repository = repository;
        this.crud = crud;
    }

    public void checkPassword(String userPin){

        if(!userPin.equals("")){
            String dbPin = getUserPin(userPin);
            if(!dbPin.equals(userPin)){
                listener.userMessage(Constants.Error_WRONG_PIN);
            }else {
                listener.onCorrectPassword();
            }
        }else {
            listener.userMessage(Constants.ERROR_EMPTY_TEXT);
        }
    }

    private String getUserPin(String pinCode){

         UserModel user = crud.getUser(pinCode);

         if(user == null){
             //todo improve security.
             return "";
         }else {
             return user.getPin();
         }
    }

    public void saveKeyboardType(boolean isNumericKeyboard){
        repository.createKeyboardType(isNumericKeyboard);
    }

    public void setListener(FortressGatePresenterListener listener) {
        this.listener = listener;
    }
}
