package com.elkhamitech.projectkeeper.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.dagger.AppComponent;
import com.elkhamitech.projectkeeper.dagger.ContextModule;
import com.elkhamitech.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitech.projectkeeper.presenter.WelcomePresenter;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.WelcomeNotifier;
import com.elkhamitech.projectkeeper.utils.accesshandler.SecurityModerator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity
        implements WelcomeNotifier {

    @BindView(R.id.editPin)
    EditText pinCodeEditText;

    private boolean NumericKeyboard;

    @Inject
    WelcomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        setTitleFont(R.id.appTitle);

        daggerInit();

        presenter.setListener(this);

        NumericKeyboard = presenter.getKeyboardStatus();

    }

    private void daggerInit() {
        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);
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

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case EXTERNAL_STORAGE_REQUEST: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    importDatabase();
//                } else {
//
//                    Toast.makeText(getApplicationContext(), Constants.PERMISSION_DENIED
//                            , Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }

    @Override
    public void userMessage(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInputValidationSuccess(String validPinCode) {
        presenter.createNewUser(validPinCode);
    }

    @Override
    public void onPasswordCreatedSuccessfully() {
        Intent gotoMain = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(gotoMain);
        finish();
    }

    public void OnEnterClick(View view) {
        String pinCodeString = pinCodeEditText.getText().toString();
        presenter.validateInputs(pinCodeString);
    }
}


