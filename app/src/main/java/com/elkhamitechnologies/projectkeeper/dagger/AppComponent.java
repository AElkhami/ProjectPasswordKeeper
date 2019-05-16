package com.elkhamitechnologies.projectkeeper.dagger;

import com.elkhamitechnologies.projectkeeper.ui.activities.CreateEntryActivity;
import com.elkhamitechnologies.projectkeeper.ui.activities.EntryActivity;
import com.elkhamitechnologies.projectkeeper.ui.activities.EntryDetailsActivity;
import com.elkhamitechnologies.projectkeeper.ui.activities.FortressGateActivity;
import com.elkhamitechnologies.projectkeeper.ui.activities.MainActivity;
import com.elkhamitechnologies.projectkeeper.ui.activities.SplashActivity;
import com.elkhamitechnologies.projectkeeper.ui.activities.WelcomeActivity;

import dagger.Component;

@Component (modules = {AppModule.class, ContextModule.class})
public interface AppComponent {
    void inject(WelcomeActivity activity);
    void inject(SplashActivity activity);
    void inject(FortressGateActivity activity);
    void inject(CreateEntryActivity activity);
    void inject(EntryActivity activity);
    void inject(EntryDetailsActivity activity);
    void inject(MainActivity activity);
}
