package com.elkhamitech.projectkeeper.ui.Activities;

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

import com.elkhamitech.projectkeeper.utils.AccessHandler.AESHelper;
import com.elkhamitech.projectkeeper.utils.Fonts.FontCache;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SecurityModerator;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;
import com.elkhamitech.data.helper.DatabaseHelper;
import com.elkhamitech.data.model.UserModel;

import java.util.HashMap;

public class FortressGate extends AppCompatActivity {

    // Database Helper
    DatabaseHelper db;
    UserModel user;
    String sPin;
    EditText edtxt_pin;
    SessionManager session;
    private HashMap<String, Boolean> hashMap;
    long id;
    private boolean NumericKeyboard;
    private boolean isFirstTime = false;

    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortress_gate);

        TextView tv = (TextView) findViewById(R.id.appTitle);
        Typeface tf = FontCache.get("Audiowide-Regular.ttf", this);
        tv.setTypeface(tf);

        edtxt_pin = (EditText) findViewById(R.id.editPinGate);
        session = new SessionManager(getApplicationContext());

        hashMap = session.getKeyboardDetails();

        NumericKeyboard = hashMap.get(session.KEYBOARD_TYPE);

        isFirstTime = getIntent().getBooleanExtra("boolean",false);


        Button pinLogin = (Button)findViewById(R.id.pinEnterGate);
        pinLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                sPin = edtxt_pin.getText().toString();

                if (sPin.equals("")) {

                    Toast.makeText(getBaseContext(),
                            "Please Fill all the Required Filed.",
                            Toast.LENGTH_LONG).show();

                } else {

                    db = new DatabaseHelper(getApplicationContext());
                    user = new UserModel();

                    try {
                        normalTextEnc = AESHelper.encrypt(seedValue, sPin);
                        normalTextDec = AESHelper.decrypt(seedValue,db.getPinUser(normalTextEnc).getPin());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (sPin.equals(normalTextDec)) {
//                        Intent gotoMain = new Intent(FortressGate.this, MainActivity.class);
//                        id = db.getPinUser(normalTextEnc).getRow_id();
//                        session.createLoginSession(id, normalTextDec);
//                        startActivity(gotoMain);
                        if(isFirstTime){
                            Intent i = new Intent(FortressGate.this, MainActivity.class);
                            startActivity(i);
                            session.createLoginSession(id, normalTextEnc);
                        }else {
                            finish();
                        }

                    } else {
                        Toast.makeText(getBaseContext(),
                                "Pin is incorrect.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void pinKeyboard(){
        edtxt_pin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        edtxt_pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        NumericKeyboard = true;
}

    public void textKeyboard(){
        edtxt_pin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtxt_pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        NumericKeyboard = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mInflater = getMenuInflater();

        if (NumericKeyboard) {
            mInflater.inflate(R.menu.pin_keyboard, menu);
            pinKeyboard();
        } else  {
            mInflater.inflate(R.menu.normal_keyboard, menu);
        }

        return true;

    }

    // change between keyboards
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.pin: //opposite image
                invalidateOptionsMenu();
                textKeyboard();
                return true;

            case R.id.normal: //opposite image
                invalidateOptionsMenu();
                pinKeyboard();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);

        if (NumericKeyboard) {

            session.createKeyboardType(NumericKeyboard);
        }else {
            session.createKeyboardType(NumericKeyboard);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SecurityModerator.lockAppStoreTime();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SecurityModerator.lockAppStoreTime();
    }
}
