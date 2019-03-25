package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashPresenterListener>{


    @Inject
    SplashPresenter(Repository repository){
        super(repository);
    }


    public void checkLoginStatus(){
        if(!repository.isLoggedIn()){
            listener.notLoggedIn();
        }else {
            listener.loggedIn();
        }
    }
}
