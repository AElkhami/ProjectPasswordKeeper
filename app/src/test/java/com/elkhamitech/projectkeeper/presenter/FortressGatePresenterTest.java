package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class FortressGatePresenterTest {

    private boolean isNumericKeyboard_true = true;
    private boolean isNumericKeyboard_false = false;

    private String pinCode_correct = "123456";
    private String pinCode_wrong = "123";
    private String pinCode_empty = "";

    private FortressGatePresenter presenter;

    @Mock
    private WelcomePresenter welcomePresenter;
    @Mock
    private WelcomePresenterListener welcomeListener;

    @Mock
    private FortressGatePresenterListener listener;
    @Mock
    private Repository repository;
    @Mock
    private UserCrud crud;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new FortressGatePresenter(repository,crud);
        presenter.setListener(listener);

        welcomePresenter = new WelcomePresenter(repository,crud);
        welcomePresenter.setListener(welcomeListener);
    }

    @Test
    public void checkPassword_empty() {
        presenter.checkPassword(pinCode_empty);
        verify(listener).userMessage(Constants.ERROR_EMPTY_TEXT);
    }

    @Test
    public void checkPassword_wrong() {
        presenter.checkPassword(pinCode_wrong);
        verify(listener).userMessage(Constants.Error_WRONG_PIN);
    }

    @Test
    public void checkPassword_correct() {
        welcomePresenter.createPassword(pinCode_correct);
        presenter.checkPassword(pinCode_correct);
        verify(listener).onCorrectPassword();
    }

    @Test
    public void saveKeyboardType_true() {
        presenter.saveKeyboardType(isNumericKeyboard_true);
    }

    @Test
    public void saveKeyboardType_false() {
        presenter.saveKeyboardType(isNumericKeyboard_false);
    }

    @Test
    public void setListener() {
        assertThat(listener,instanceOf(FortressGatePresenterListener.class));
    }
}