package com.elkhamitech.projectkeeper.presenter;

import android.os.Environment;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WelcomePresenterTest {

    private static final String pinCode_correct = "123456";
    private static final String pinCode_wrongLength = "123";
    private static final String pinCode_empty = "";
    private static final boolean isNumericKeyboard = true;
    private static final boolean isNotNumericKeyboard = false;

    private WelcomePresenter welcomePresenter;

    @Mock
    private WelcomePresenterListener listener;
    @Mock
    private Repository repository;
    @Mock
    private UserCrud crud;
    @Mock
    private BasePresenterImpl basePresenter;
    @Mock
    File sd;
    @Mock
    File data;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        welcomePresenter = new WelcomePresenter(repository, crud);
        welcomePresenter.setListener(listener);
    }

    @Test
    public void validateInputs_emptyInput() {
        welcomePresenter.validateInputs(pinCode_empty);
        listener.userMessage(Constants.ERROR_EMPTY_TEXT);
    }

    @Test
    public void validateInputs_wrongPasswordLength() {
        welcomePresenter.validateInputs(pinCode_wrongLength);
        listener.userMessage(Constants.ERROR_PASSWORD_LENGTH);
    }

    @Test
    public void validateInputs_CorrectInputs() {
        welcomePresenter.validateInputs(pinCode_correct);
        listener.onInputValidationSuccess(pinCode_correct);
    }


    @Test
    public void createNewUser() {
        welcomePresenter.createNewUser(pinCode_correct);
        verify(listener).onPasswordCreatedSuccessfully();
    }

    @Test
    public void setListener() {
        assertThat(listener, instanceOf(WelcomePresenterListener.class));
    }

    @Test
    public void saveKeyboardType_numeric() {
        //todo test it correctly
        basePresenter.saveKeyboardType(isNumericKeyboard);
    }

    @Test
    public void saveKeyboardType_nonNumeric() {
        //todo test it correctly
        basePresenter.saveKeyboardType(isNotNumericKeyboard);
    }

    @Test
    public void getKeyboardStatus_numeric() {
        when(basePresenter.getKeyboardStatus()).thenReturn(true);
        assertTrue(basePresenter.getKeyboardStatus());
    }

    @Test
    public void getKeyboardStatus_nonNumeric() {
        when(basePresenter.getKeyboardStatus()).thenReturn(false);
        assertFalse(basePresenter.getKeyboardStatus());
    }

}