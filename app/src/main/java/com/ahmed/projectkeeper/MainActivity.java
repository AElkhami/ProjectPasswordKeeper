package com.ahmed.projectkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean doubleBackToExitPressedOnce = false;
    private ImageButton lockButton, userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

        //Setting Home Fragment (Default)
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();

        navigationView.setCheckedItem(R.id.nav_passwords);

        manager.beginTransaction().replace(R.id.layoutttt,homeFragment).commit();

        //setting buttons
        userButton = (ImageButton)header.findViewById(R.id.imgbtnUser);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();

                FragmentManager manager = getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.layoutttt,profileFragment).commit();

                navigationView.getMenu().getItem(0).setChecked(false);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        lockButton = (ImageButton)header.findViewById(R.id.imgbtnLock);
        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FortressGate.class);
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

            manager.beginTransaction().replace(R.id.layoutttt,homeFragment).commit();
        } else if (id == R.id.nav_settings){

        }else if (id == R.id.nav_send_Feedback){

        }else if (id == R.id.nav_help){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
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
        } else {

        }
    }

}
