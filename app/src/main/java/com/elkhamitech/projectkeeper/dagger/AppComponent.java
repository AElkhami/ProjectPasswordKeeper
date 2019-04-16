package com.elkhamitech.projectkeeper.dagger;

import com.elkhamitech.projectkeeper.ui.activities.CreateEntryActivity;
import com.elkhamitech.projectkeeper.ui.activities.EntryActivity;
import com.elkhamitech.projectkeeper.ui.activities.EntryDetailsActivity;
import com.elkhamitech.projectkeeper.ui.activities.FortressGate;
import com.elkhamitech.projectkeeper.ui.activities.SplashActivity;
import com.elkhamitech.projectkeeper.ui.activities.WelcomeActivity;
import com.elkhamitech.projectkeeper.ui.fragments.HomeFragment;

import dagger.Component;

@Component (modules = {AppModule.class, ContextModule.class})
public interface AppComponent {
    void inject(WelcomeActivity activity);
    void inject(SplashActivity activity);
    void inject(FortressGate activity);
    void inject(CreateEntryActivity activity);
    void inject(EntryActivity activity);
    void inject(EntryDetailsActivity activity);
    void inject(HomeFragment fragment);
}
