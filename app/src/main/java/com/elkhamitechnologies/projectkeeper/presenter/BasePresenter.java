package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.Constants;
import com.elkhamitechnologies.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.BaseNotifier;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.SetViewNotifier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 29,March,2019
 */
public class BasePresenter implements SetViewNotifier<BaseNotifier> {

    protected CacheRepository cacheRepository;

    @Inject
    BasePresenter(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    @Override
    public void setListener(BaseNotifier listener) {
    }


    public void saveKeyboardType(boolean isNumericKeyboard) {
        cacheRepository.setKeyboardType(isNumericKeyboard);
    }


    public boolean getKeyboardStatus() {
        return cacheRepository.getKeyboardType().get(Constants.KEYBOARD_TYPE);
    }

    public String getCurrentDate(){
        return new SimpleDateFormat(Constants.DATE_FORMAT_PATTERN, Locale.ENGLISH)
                .format(new Date());
    }

}
