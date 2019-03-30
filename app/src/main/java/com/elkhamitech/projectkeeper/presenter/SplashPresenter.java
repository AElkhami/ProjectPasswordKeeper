package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import javax.inject.Inject;

public class SplashPresenter implements SetPresenterListener<SplashPresenterListener>{


    private Repository repository;
    private SplashPresenterListener listener;


    @Inject
    SplashPresenter(Repository repository){
        this.repository = repository;
    }


    public void checkLoginStatus(){
        if(!repository.isLoggedIn()){
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
