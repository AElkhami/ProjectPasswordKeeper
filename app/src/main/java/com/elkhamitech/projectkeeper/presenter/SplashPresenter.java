package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.data.sharedpreferences.Repository;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter{

    private Repository repository;
    private SplashPresenterListener listener;

    @Inject
    SplashPresenter(Repository repository){
        this.repository = repository;
    }

    public void setListener(SplashPresenterListener listener) {
        this.listener = listener;
    }

    public void checkLoginStatus(){
        if(!repository.isLoggedIn()){
            listener.notLoggedIn();
        }else {
            listener.loggedIn();
        }
    }
}
