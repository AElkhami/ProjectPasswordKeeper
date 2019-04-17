package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.WelcomeNotifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WelcomePresenterTest {

    private static final String pinCode_correct = "123456";
    private static final String pinCode_wrongLength = "123";
    private static final String pinCode_empty = "";

    private WelcomePresenter presenter;

    @Mock
    private WelcomeNotifier listener;
    @Mock
    private CacheRepository cacheRepository;
    @Mock
    private UserCrud crud;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new WelcomePresenter(cacheRepository, crud);
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

}