package com.elkhamitech.projectkeeper.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.utils.AccessHandler.AESHelper;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SecurityModerator;
import com.elkhamitech.projectkeeper.data.helper.DatabaseHelper;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;

public class LoginActivity extends AppCompatActivity {

    // Database Helper
    private DatabaseHelper db;
    private UserModel user;
    private String eMail, password, sPin;
    private EditText edtxt_pass, edtxt_email, edtxt_pin;
    private long id;
    private boolean newUser = true;

    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtxt_pass = (EditText) findViewById(R.id.editTextPassLogin);
        edtxt_email = (EditText) findViewById(R.id.editTextMailLogin);
        edtxt_pin = (EditText) findViewById(R.id.editPinLogin);

        Button LogInButton = (Button) findViewById(R.id.btn_Login);
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eMail = edtxt_email.getText().toString();
                password = edtxt_pass.getText().toString();


                if (eMail.equals("") || password.equals("")) {

                    Toast.makeText(getBaseContext(),
                            "Please Fill all the Required Fields.",
                            Toast.LENGTH_LONG).show();

                } else {
                    db = new DatabaseHelper(getApplicationContext());
                    user = new UserModel();

                    try {
                        normalTextEnc = AESHelper.encrypt(seedValue, password);
                        normalTextDec = AESHelper.decrypt(seedValue,db.getRegisteredUser(eMail,normalTextEnc).getPassword());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    db.getRegisteredUser(eMail,normalTextEnc);


                    if (eMail.equals(db.getRegisteredUser(eMail,normalTextEnc).getEmail()) && password.equals(normalTextDec)){
                        Intent gotoMain = new Intent(LoginActivity.this, MainActivity.class);

                        id = db.getRegisteredUser(eMail,normalTextEnc).getRow_id();
//                        session.createLoginSession(id,eMail);
                        gotoMain.putExtra("boolean", newUser);
                        startActivity(gotoMain);
                        finish();
                    }else {
                        Toast.makeText(getBaseContext(),
                                "Email or Password is incorrect.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

        });


        Button pinLogin = (Button)findViewById(R.id.btn_Pin_Login);
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
                        Intent gotoMain = new Intent(LoginActivity.this, MainActivity.class);

                        id = db.getPinUser(normalTextEnc).getRow_id();
//                        session.createLoginSession(id, normalTextDec);
                        gotoMain.putExtra("boolean", newUser);

                        startActivity(gotoMain);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Pin is incorrect.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SecurityModerator.lockAppStoreTime();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
