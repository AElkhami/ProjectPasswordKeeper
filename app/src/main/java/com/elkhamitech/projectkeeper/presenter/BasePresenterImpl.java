package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;
import com.elkhamitech.projectkeeper.viewnotifiyers.BasePresenterListener;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 29,March,2019
 */
public class BasePresenterImpl implements BasePresenterContract,
        SetPresenterListener<BasePresenterListener> {

    protected Repository repository;
    protected UserCrud crud;

    @Inject
    BasePresenterImpl(Repository repository, UserCrud crud) {
        this.repository = repository;
        this.crud = crud;
    }

    @Override
    public void setListener(BasePresenterListener listener) {
    }

    @Override
    public void saveKeyboardType(boolean isNumericKeyboard) {
        repository.createKeyboardType(isNumericKeyboard);
    }

    @Override
    public boolean getKeyboardStatus() {
        HashMap<String, Boolean> hashMap = repository.getKeyboardDetails();
        return hashMap.get(Constants.KEYBOARD_TYPE);
    }

}
