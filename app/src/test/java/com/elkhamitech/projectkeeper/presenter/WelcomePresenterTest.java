package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WelcomePresenterTest {

    private String pinCode_correct = "123456";
    private String pinCode_wrongLength = "123";
    private String pinCode_empty = "";

    private WelcomePresenter welcomePresenter;

    @Mock
    WelcomePresenterListener listener;
    @Mock
    Repository repository;
    @Mock
    UserCrud crud;

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
    public void importDB() {
    }
}