package com.ahmed.projectkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmed.sqlite.helper.DatabaseHelper;
import com.ahmed.sqlite.model.UserModel;

public class WelcomeActivity extends AppCompatActivity {

    private Button signUp,logIn,enter;
    private DatabaseHelper db;
    private UserModel user;
    private String sPin;
    private EditText pin;
    private SessionManager session;
    private Long id;


    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        signUp = (Button)findViewById(R.id.signUp);
        logIn = (Button)findViewById(R.id.logIn);
        enter = (Button)findViewById(R.id.pinEnter);

        pin = (EditText)findViewById(R.id.editPin);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMain = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(gotoMain);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMain = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(gotoMain);
            }
        });


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sPin = pin.getText().toString();

                if (sPin.equals("")) {

                    Toast.makeText(getBaseContext(),
                            "Please Fill all the Required Filed.",
                            Toast.LENGTH_LONG).show();

                }else if (pinExist()==false){

                    Toast.makeText(getBaseContext(),
                            "The pin already exist.",
                            Toast.LENGTH_LONG).show();
                }else {

                    session = new SessionManager(getApplicationContext());
                    db = new DatabaseHelper(getApplicationContext());
                    user = new UserModel();

                    try {
                        normalTextEnc = AESHelper.encrypt(seedValue, sPin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    user.setPin(normalTextEnc);

                    db.createUser(user);

                    id = user.getRow_id();

                    session.createLoginSession(id, normalTextEnc);

                    Intent gotoMain = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(gotoMain);
                    finish();

                }
            }
     });
    }

    public boolean pinExist() {

        db = new DatabaseHelper(getApplicationContext());
        user = new UserModel();

        try {
            normalTextEnc = AESHelper.encrypt(seedValue, sPin);
            normalTextDec = AESHelper.decrypt(seedValue, db.getPinUser(normalTextEnc).getPin());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sPin.equals(normalTextDec)) {

            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.actionbar_keyboard, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_keyboard:

                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //<<<<< IMPORTANT NOTE : Activity Stack is not cleared YET >>>>> SECURITY ISSUE
            moveTaskToBack(true);
    }
}


