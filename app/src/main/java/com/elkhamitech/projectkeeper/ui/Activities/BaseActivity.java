package com.elkhamitech.projectkeeper.ui.Activities;

import android.arch.persistence.room.Room;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.elkhamitech.MyApplication;
import com.elkhamitech.data.PasswordsDatabase;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.utils.Fonts.FontCache;

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //todo make it work
        TextView tv = findViewById(R.id.appTitle);

        Typeface tf = FontCache.get("Audiowide-Regular.ttf", this);
        tv.setTypeface(tf);

    }
}
