package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;
import com.elkhamitech.projectkeeper.viewnotifiyers.WelcomePresenterListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WelcomePresenterTest {

    private static final String pinCode_correct = "123456";
    private static final String pinCode_wrongLength = "123";
    private static final String pinCode_empty = "";

    private WelcomePresenter presenter;

    @Mock
    private WelcomePresenterListener listener;
    @Mock
    private Repository repository;
    @Mock
    private UserCrud crud;
    @Mock
    private BasePresenterImpl basePresenter;
    @Mock
    private BasePresenterContract presenterContract;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new WelcomePresenter(repository, crud);
        presenter.setListener(listener);
    }

    @Test
    public void validateInputs_emptyInput() {
        presenter.validateInputs(pinCode_empty);
        listener.userMessage(Constants.ERROR_EMPTY_TEXT);
    }

    @Test
    public void validateInputs_wrongPasswordLength() {
        presenter.validateInputs(pinCode_wrongLength);
        listener.userMessage(Constants.ERROR_PASSWORD_LENGTH);
    }

    @Test
    public void validateInputs_CorrectInputs() {
        presenter.validateInputs(pinCode_correct);
        listener.onInputValidationSuccess(pinCode_correct);
    }

    @Test
    public void createNewUser() {
        presenter.createNewUser(pinCode_correct);
        verify(listener).onPasswordCreatedSuccessfully();
    }

    @Test
    public void getKeyboardStatus_numeric() {
        when(basePresenter.getKeyboardStatus()).thenReturn(true);
        assertTrue(basePresenter.getKeyboardStatus());
    }

}