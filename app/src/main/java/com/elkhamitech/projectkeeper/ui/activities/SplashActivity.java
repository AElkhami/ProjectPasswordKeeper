package com.elkhamitech.projectkeeper.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.dagger.AppComponent;
import com.elkhamitech.projectkeeper.dagger.ContextModule;
import com.elkhamitech.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.presenter.SplashPresenter;
import com.elkhamitech.projectkeeper.presenter.SplashPresenterListener;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashPresenterListener {

    private Intent intent;

    @Inject
    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setTitleFont(R.id.appTitle);

        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);

        presenter.setListener(this);

        splashRunner();
    }

    public void splashRunner() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                presenter.checkLoginStatus();

                finish();
            }
        }, Constants.SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void loggedIn() {
        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void notLoggedIn() {
        intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
