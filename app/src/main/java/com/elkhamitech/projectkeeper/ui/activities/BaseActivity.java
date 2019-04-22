package com.elkhamitech.projectkeeper.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.utils.fonts.FontCache;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.BaseNotifier;

public abstract class BaseActivity extends AppCompatActivity
        implements BaseNotifier {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
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
