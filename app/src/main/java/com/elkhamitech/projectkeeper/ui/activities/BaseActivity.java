package com.elkhamitech.projectkeeper.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.presenter.BasePresenterListener;
import com.elkhamitech.projectkeeper.utils.Fonts.FontCache;

public abstract class BaseActivity extends AppCompatActivity
        implements BasePresenterListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public void userMessage(String msg) {

    }

    public void setTitleFont(@IdRes int appTitle){
        TextView tv = findViewById(appTitle);

        Typeface tf = FontCache.get("Audiowide-Regular.ttf", this);
        tv.setTypeface(tf);
    }
}
