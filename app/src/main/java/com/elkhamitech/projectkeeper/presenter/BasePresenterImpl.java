package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.viewnotifiyers.BasePresenterListener;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 29,March,2019
 */
public class BasePresenterImpl implements BasePresenterContract,
        SetPresenterListener<BasePresenterListener> {

    protected CacheRepository cacheRepository;

    @Inject
    BasePresenterImpl(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    @Override
    public void setListener(BasePresenterListener listener) {
    }

    @Override
    public void saveKeyboardType(boolean isNumericKeyboard) {
        cacheRepository.createKeyboardType(isNumericKeyboard);
    }

    @Override
    public boolean getKeyboardStatus() {
        HashMap<String, Boolean> hashMap = cacheRepository.getKeyboardDetails();
        return hashMap.get(Constants.KEYBOARD_TYPE);
    }

}
