package com.elkhamitechnologies.projectkeeper.ui.activities;

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

import com.elkhamitechnologies.projectkeeper.R;
import com.elkhamitechnologies.projectkeeper.dagger.AppComponent;
import com.elkhamitechnologies.projectkeeper.dagger.ContextModule;
import com.elkhamitechnologies.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitechnologies.projectkeeper.presenter.FortressGatePresenter;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.FortressGateNotifier;
import com.elkhamitechnologies.projectkeeper.utils.accesshandler.SecurityModerator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FortressGateActivity extends BaseActivity
        implements FortressGateNotifier {

    private String passString;

    @BindView(R.id.editPinGate)
    EditText editTextPass;
    @BindView(R.id.pinEnterGate)
    Button loginBtn;

    private boolean isNumericKeyboard;

    @Inject
    FortressGatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortress_gate);

        initView();
        daggerInit();

        presenter.setListener(this);

        isNumericKeyboard = presenter.getKeyboardStatus();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passString = editTextPass.getText().toString();

                presenter.checkPassword(passString);
            }
        });
    }

    private void daggerInit() {
        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
        component.inject(this);
    }

    private void initView() {

        getWindow().setSharedElementsUseOverlay(true);
        ButterKnife.bind(this);
        setTitleFont(R.id.appTitle);

        editTextPass.requestFocus();
    }

    public void pinKeyboard() {
        editTextPass.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        editTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isNumericKeyboard = true;
    }

    public void textKeyboard() {
        editTextPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
        finishAfterTransition();
    }

    @Override
    public void userMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
