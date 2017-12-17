package com.elkhamitech.projectkeeper.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.AccessHandler.AESHelper;
import com.elkhamitech.projectkeeper.Fonts.FontCache;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.AccessHandler.SecurityModerator;
import com.elkhamitech.projectkeeper.AccessHandler.SessionManager;
import com.elkhamitech.sqlite.helper.DatabaseHelper;
import com.elkhamitech.sqlite.model.UserModel;

public class WelcomeActivity extends AppCompatActivity {

    private Button signUp,logIn,enter;
    private DatabaseHelper db;
    private UserModel user;
    private String sPin;
    private EditText pin;
    private SessionManager session;
    private Long id;
    private boolean newUser = true;
    private boolean NumericKeyboard;


    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tv = (TextView) findViewById(R.id.appTitle);

        Typeface tf = FontCache.get("Audiowide-Regular.ttf", this);
        tv.setTypeface(tf);

        signUp = (Button)findViewById(R.id.signUp);
        logIn = (Button)findViewById(R.id.logIn);
        enter = (Button)findViewById(R.id.pinEnter);

        pin = (EditText)findViewById(R.id.editPin);

        session = new SessionManager(getApplicationContext());


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMain = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(gotoMain);
                gotoMain.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMain = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(gotoMain);
                gotoMain.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            }
        });


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sPin = pin.getText().toString();

                int charCount = pin.getText().length();


                if (sPin.equals("")) {

                    Toast.makeText(getBaseContext(),
                            "Please Fill all the Required Filed.",
                            Toast.LENGTH_LONG).show();

                }else if (pinExist()==false){

                    Toast.makeText(getBaseContext(),
                            "The pin already exist.",
                            Toast.LENGTH_LONG).show();
                }else if (charCount<6){
                    Toast.makeText(getBaseContext(),
                            "Your Pin must contain more than 6 characters.",
                            Toast.LENGTH_LONG).show();

                } else {


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
                    gotoMain.putExtra("boolean", newUser);
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

    public void pinKeyboard(){
        pin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        NumericKeyboard = true;
    }

    public void textKeyboard(){
        pin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        NumericKeyboard = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mInflater = getMenuInflater();

        if (NumericKeyboard) {
            mInflater.inflate(R.menu.pin_keyboard, menu);
        } else  {
            mInflater.inflate(R.menu.normal_keyboard, menu);
        }

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.pin:
                invalidateOptionsMenu();
                textKeyboard();
                return true;

            case R.id.normal:
                invalidateOptionsMenu();
                pinKeyboard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (NumericKeyboard) {
            session.createKeyboardType(NumericKeyboard);
        }else {
            session.createKeyboardType(NumericKeyboard);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SecurityModerator.lockAppStoreTime();
    }

    @Override
    public void onBackPressed() {
            moveTaskToBack(true);
    }
}


