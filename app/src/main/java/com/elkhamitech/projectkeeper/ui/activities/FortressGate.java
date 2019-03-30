package com.elkhamitech.projectkeeper.ui.activities;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.dagger.AppComponent;
import com.elkhamitech.projectkeeper.dagger.ContextModule;
import com.elkhamitech.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitech.projectkeeper.presenter.FortressGatePresenter;
import com.elkhamitech.projectkeeper.presenter.FortressGatePresenterListener;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SecurityModerator;

import javax.inject.Inject;

public class FortressGate extends BaseActivity
        implements FortressGatePresenterListener {

    private String sPin;
    private EditText edtxt_pin;
    private Button loginBtn;
    private boolean isNumericKeyboard;

    @Inject
    FortressGatePresenter presenter;

    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortress_gate);

        initView();

        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
        component.inject(this);

        presenter.setListener(this);

        isNumericKeyboard = presenter.getKeyboardStatus();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sPin = edtxt_pin.getText().toString();
//                    try {
//                        normalTextEnc = AESHelper.encrypt(seedValue, sPin);
//                        normalTextDec = AESHelper.decrypt(seedValue,db.getPinUser(normalTextEnc).getPin());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    presenter.checkPassword(sPin);
            }
        });
    }

    private void initView() {

        setTitleFont(R.id.appTitle);

        edtxt_pin = findViewById(R.id.editPinGate);
        edtxt_pin.requestFocus();
        loginBtn = findViewById(R.id.pinEnterGate);
    }

    public void pinKeyboard() {
        edtxt_pin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        edtxt_pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isNumericKeyboard = true;
    }

    public void textKeyboard() {
        edtxt_pin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtxt_pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isNumericKeyboard = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mInflater = getMenuInflater();

        if (isNumericKeyboard) {
            mInflater.inflate(R.menu.pin_keyboard, menu);
            pinKeyboard();
        } else {
            mInflater.inflate(R.menu.normal_keyboard, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // change between keyboards
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

        if (isNumericKeyboard) {
            presenter.saveKeyboardType(isNumericKeyboard);
        } else {
            presenter.saveKeyboardType(isNumericKeyboard);
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

    @Override
    public void onCorrectPassword() {
        finish();
    }

    @Override
    public void userMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
