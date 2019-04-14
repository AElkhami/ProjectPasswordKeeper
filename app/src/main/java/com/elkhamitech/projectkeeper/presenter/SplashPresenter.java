package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.viewnotifiyers.SplashPresenterListener;

import javax.inject.Inject;

public class SplashPresenter implements SetPresenterListener<SplashPresenterListener>{


    private CacheRepository cacheRepository;
    private SplashPresenterListener listener;


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
    public void setListener(SplashPresenterListener listener) {
        this.listener = listener;
    }
}
