package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.SetViewNotifier;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.SplashNotifier;

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
