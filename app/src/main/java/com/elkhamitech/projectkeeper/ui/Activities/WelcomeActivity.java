package com.elkhamitech.projectkeeper.ui.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class WelcomeActivity extends AppCompatActivity {

    private Button signUp,logIn,enter;
    private DatabaseHelper db;
    private UserModel user;
    private String sPin;
    private EditText pin;
    private SessionManager session;
    private Long id;
    private boolean newUser,firstTime = true;
    private boolean NumericKeyboard;


    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tv = (TextView) findViewById(R.id.appTitle);

        Typeface tf = FontCache.get("Audiowide-Regular.ttf", this);
        tv.setTypeface(tf);

//        signUp = (Button)findViewById(R.id.signUp);
//        logIn = (Button)findViewById(R.id.logIn);
        enter = (Button)findViewById(R.id.pinEnter);

        pin = (EditText)findViewById(R.id.editPin);

        session = new SessionManager(getApplicationContext());
        db = new DatabaseHelper(getApplicationContext());
        user = new UserModel();
        db.createUser(user);

//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gotoMain = new Intent(WelcomeActivity.this, SignupActivity.class);
//                startActivity(gotoMain);
//                gotoMain.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            }
//        });
//
//        logIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gotoMain = new Intent(WelcomeActivity.this, LoginActivity.class);
//                startActivity(gotoMain);
//                gotoMain.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//            }
//        });


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

    public void restoreBackup(View view) {

        if(checkPermissionForReadExternalStorage()){

        }else {
            try {
                requestPermissionForReadExternalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        importDB();
    }


    public boolean checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionRead = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            return permissionRead == PackageManager.PERMISSION_GRANTED;

        }
        return false;
    }

    public void requestPermissionForReadExternalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //importing database
    private void importDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + this.getPackageName()
                        + "//databases//" + "PassKeeper";
                String backupDBPath  = "/Password Wallet/PassKeeper";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(this, "Passwords have been restored, Thanks to re-enter the pin to confirm the passwords are yours.",
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent(this, FortressGate.class);
                i.putExtra("boolean", firstTime);
                startActivity(i);

                finish();

            }
        } catch (Exception e) {

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
}


