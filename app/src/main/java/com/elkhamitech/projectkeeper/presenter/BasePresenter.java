package com.elkhamitech.projectkeeper.presenter;

public abstract class BasePresenter<E> {

    protected E listener;

    public void setListener(E listener) {
        this.listener = listener;
    }
}
