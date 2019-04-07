package com.elkhamitech.projectkeeper.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.ui.fragments.HelpFragment;
import com.elkhamitech.projectkeeper.ui.fragments.HomeFragment;
import com.elkhamitech.projectkeeper.ui.fragments.ProfileFragment;
import com.elkhamitech.projectkeeper.ui.fragments.SendFeedbackFragment;
import com.elkhamitech.projectkeeper.ui.fragments.SettingsFragment;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SecurityModerator;


public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener{

    private boolean doubleBackToExitPressedOnce, newUser = false;
    private ImageButton lockButton, userButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-----------------------------setting nav drawer-------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        //-----------------------------------------------------------------------------------------

        //Setting Home Fragment (Default)
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();

        navigationView.setCheckedItem(R.id.nav_passwords);

        manager.beginTransaction().replace(R.id.layoutttt, homeFragment).commit();

        //setting buttons
        userButton = (ImageButton) header.findViewById(R.id.imgbtnUser);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();

                FragmentManager manager = getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.layoutttt, profileFragment).commit();

                navigationView.getMenu().getItem(0).setChecked(false);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        lockButton = (ImageButton) header.findViewById(R.id.imgbtnLock);
        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FortressGate.class);
                startActivity(i);

                Toast.makeText(MainActivity.this, "Your app has been locked", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_passwords) {

            HomeFragment homeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.layoutttt, homeFragment).commit();

        } else if (id == R.id.nav_settings) {

            SettingsFragment settingsFragment = new SettingsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layoutttt, settingsFragment).commit();

        } else if (id == R.id.nav_send_Feedback) {

            SendFeedbackFragment sendFeedbackFragment = new SendFeedbackFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layoutttt, sendFeedbackFragment).commit();

        } else if (id == R.id.nav_help) {

            HelpFragment helpFragment = new HelpFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layoutttt, helpFragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SecurityModerator.lockAppStoreTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
        //handle first login
        if (!newUser) {
            SecurityModerator.lockAppCheck(this);
        }

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();

        navigationView.setCheckedItem(R.id.nav_passwords);

        manager.beginTransaction().replace(R.id.layoutttt, homeFragment).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        //press back button twice to exit
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

        //close the drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

}
