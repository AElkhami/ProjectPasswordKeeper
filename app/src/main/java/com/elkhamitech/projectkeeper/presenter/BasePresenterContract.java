package com.elkhamitech.projectkeeper.presenter;

/**
 * Created by A.Elkhami on 28,March,2019
 */
public interface BasePresenterContract {
    void saveKeyboardType(boolean isNumericKeyboard);
    boolean getKeyboardStatus();
}
