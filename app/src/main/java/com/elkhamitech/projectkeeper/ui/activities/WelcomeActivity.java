package com.elkhamitech.projectkeeper.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.data.dagger.AppComponent;
import com.elkhamitech.projectkeeper.data.dagger.ContextModule;
import com.elkhamitech.projectkeeper.data.dagger.DaggerAppComponent;
import com.elkhamitech.projectkeeper.presenter.WelcomePresenter;
import com.elkhamitech.projectkeeper.presenter.WelcomePresenterListener;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SecurityModerator;

import javax.inject.Inject;

public class WelcomeActivity extends BaseActivity
        implements WelcomePresenterListener {

    private EditText pinCodeEditText;
    private boolean NumericKeyboard;

    @Inject
    WelcomePresenter presenter;

    private final int EXTERNAL_STORAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setTitleFont(R.id.appTitle);

        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);

        presenter.setListener(this);

        pinCodeEditText = findViewById(R.id.editPin);

        NumericKeyboard = presenter.getKeyboardStatus();

    }

    public void pinKeyboard() {
        pinCodeEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        pinCodeEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        NumericKeyboard = true;
    }

    public void textKeyboard() {
        pinCodeEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pinCodeEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        NumericKeyboard = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mInflater = getMenuInflater();

        if (NumericKeyboard) {
            mInflater.inflate(R.menu.pin_keyboard, menu);
            pinKeyboard();
        } else {
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
            presenter.saveKeyboardType(NumericKeyboard);
        } else {
            presenter.saveKeyboardType(NumericKeyboard);
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

        if (!checkPermissionForReadExternalStorage()) {
            requestPermissionForReadExternalStorage();
        }else {
            importDatabase();
        }
    }

    private void importDatabase(){
        presenter.importDB(getPackageName());
    }

    public boolean checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionRead = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            return permissionRead == PackageManager.PERMISSION_GRANTED;

        }
        return false;
    }

    public void requestPermissionForReadExternalStorage() {
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    importDatabase();
                } else {

                    Toast.makeText(getApplicationContext(), Constants.PERMISSION_DENIED
                            , Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void userMessage(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInputValidationSuccess(String validPinCode) {
        presenter.createPassword(validPinCode);
    }

    @Override
    public void onPasswordCreatedSuccessfully() {
        Intent gotoMain = new Intent(WelcomeActivity.this, MainActivity.class);
        boolean newUser = true;
        gotoMain.putExtra("boolean", newUser);
        startActivity(gotoMain);
        finish();
    }

    public void OnEnterClick(View view) {
        String pinCodeString = pinCodeEditText.getText().toString();
        presenter.validateInputs(pinCodeString);
    }
}


