package com.elkhamitechnologies.projectkeeper.ui.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.elkhamitechnologies.projectkeeper.Constants;
import com.elkhamitechnologies.projectkeeper.R;
import com.elkhamitechnologies.projectkeeper.dagger.AppComponent;
import com.elkhamitechnologies.projectkeeper.dagger.ContextModule;
import com.elkhamitechnologies.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitechnologies.projectkeeper.presenter.SplashPresenter;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.SplashNotifier;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashNotifier {

    private Intent intent;

    @Inject
    SplashPresenter presenter;

    @BindView(R.id.splash_appLogo)
    ImageView splashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        setTitleFont(R.id.appTitle);

        daggerInit();

        presenter.setListener(this);

        splashRunner();
    }

    private void daggerInit() {
        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);
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

        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(SplashActivity.this,
                        splashLogo, "lockApp");

        startActivity(intent, options.toBundle());
    }

    @Override
    public void notLoggedIn() {
        intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
