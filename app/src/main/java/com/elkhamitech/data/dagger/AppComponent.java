package com.elkhamitech.data.dagger;

import com.elkhamitech.projectkeeper.ui.Activities.SplashActivity;
import com.elkhamitech.projectkeeper.ui.Activities.WelcomeActivity;

import dagger.Component;

@Component (modules = {AppModule.class, ContextModule.class})
public interface AppComponent {
    void inject(WelcomeActivity activity);
    void inject(SplashActivity activity);
}
