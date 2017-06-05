package com.elkhamitech.projectkeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elkhamitech.sqlite.helper.DatabaseHelper;
import com.elkhamitech.sqlite.model.UserModel;

public class FortressGate extends AppCompatActivity {

    // Database Helper
    DatabaseHelper db;
    UserModel user;
    String sPin;
    EditText edtxt_pin;
    SessionManager session;
    long id;

    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortress_gate);

        Button pinLogin = (Button)findViewById(R.id.pinEnterGate);
        pinLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                edtxt_pin = (EditText) findViewById(R.id.editPinGate);

                sPin = edtxt_pin.getText().toString();

                if (sPin.equals("")) {

                    Toast.makeText(getBaseContext(),
                            "Please Fill all the Required Filed.",
                            Toast.LENGTH_LONG).show();

                } else {
                    session = new SessionManager(getApplicationContext());


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