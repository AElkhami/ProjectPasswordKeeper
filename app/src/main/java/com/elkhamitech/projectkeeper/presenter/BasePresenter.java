package com.elkhamitech.projectkeeper.presenter;

import javax.inject.Inject;

public abstract class BasePresenter<L, R, C> {

    protected L listener;
    protected R repository;
    protected C crud;

//    public BasePresenter(R repository, C crud){
//        this.repository = repository;
//        this.crud = crud;
//    }

    public void setListener(L listener) {
        this.listener = listener;
    }
}
