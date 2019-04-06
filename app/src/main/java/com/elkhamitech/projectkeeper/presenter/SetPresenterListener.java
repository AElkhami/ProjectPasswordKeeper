package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.viewnotifiyers.BasePresenterListener;

/**
 * Created by A.Elkhami on 30,March,2019
 */
public interface SetPresenterListener<E extends BasePresenterListener> {
    void setListener(E listener);
}
