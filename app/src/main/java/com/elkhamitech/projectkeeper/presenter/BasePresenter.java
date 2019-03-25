package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;

import java.util.HashMap;

public abstract class BasePresenter<E extends BasePresenterListener> {

    E listener;
    protected Repository repository;
    protected UserCrud crud;

    BasePresenter(Repository repository) {
        this.repository = repository;
    }

    BasePresenter(Repository repository, UserCrud crud) {
        this.repository = repository;
        this.crud = crud;
    }

    public void setListener(E listener) {
        this.listener = listener;
    }

    public void saveKeyboardType(boolean isNumericKeyboard) {
        repository.createKeyboardType(isNumericKeyboard);
    }

    public boolean getKeyboardStatus() {
        HashMap<String, Boolean> hashMap = repository.getKeyboardDetails();
        return hashMap.get(SessionManager.KEYBOARD_TYPE);
    }
}
