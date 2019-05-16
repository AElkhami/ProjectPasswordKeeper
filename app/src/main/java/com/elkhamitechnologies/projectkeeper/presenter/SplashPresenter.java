package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.SetViewNotifier;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.SplashNotifier;

import javax.inject.Inject;

public class SplashPresenter implements SetViewNotifier<SplashNotifier> {


    private CacheRepository cacheRepository;
    private SplashNotifier listener;


    @Inject
    SplashPresenter(CacheRepository cacheRepository){
        this.cacheRepository = cacheRepository;
    }


    public void checkLoginStatus(){
        if(!cacheRepository.isLoggedIn()){
            listener.notLoggedIn();
        }else {
            listener.loggedIn();
        }
    }

    @Override
    public void setListener(SplashNotifier listener) {
        this.listener = listener;
    }
}
