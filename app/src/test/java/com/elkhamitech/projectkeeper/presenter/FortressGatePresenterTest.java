package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.FortressGateNotifier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FortressGatePresenterTest {

    private static final String pinCode_correct = "123456";
    private static final String pinCode_wrong = "123";
    private static final String pinCode_empty = "";

    private FortressGatePresenter presenter;

    @Mock
    private FortressGateNotifier listener;
    @Mock
    private UserCrud crud;
    @Mock
    private UserModel userModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new FortressGatePresenter(crud);
        presenter.setListener(listener);
        userModel = new UserModel();
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
        when(crud.readFromDb(pinCode_correct))
                .thenReturn(userModel);

        userModel.setPin(pinCode_correct);

        presenter.checkPassword(pinCode_correct);
        verify(listener).onCorrectPassword();
    }

    @Test
    public void checkPassword_notExist() {
        when(crud.readFromDb(pinCode_wrong))
                .thenReturn(userModel);

        userModel.setPin(null);

        presenter.checkPassword(pinCode_wrong);

        verify(listener).userMessage(Constants.Error_WRONG_PIN);
    }

}