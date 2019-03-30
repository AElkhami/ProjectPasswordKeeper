package com.elkhamitech.projectkeeper.dagger;

import com.elkhamitech.projectkeeper.ui.activities.FortressGate;
import com.elkhamitech.projectkeeper.ui.activities.SplashActivity;
import com.elkhamitech.projectkeeper.ui.activities.WelcomeActivity;

import dagger.Component;

@Component (modules = {AppModule.class, ContextModule.class})
public interface AppComponent {
    void inject(WelcomeActivity activity);
    void inject(SplashActivity activity);
    void inject(FortressGate activity);
}
