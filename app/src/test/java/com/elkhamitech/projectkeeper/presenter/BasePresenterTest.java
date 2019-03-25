package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class BasePresenterTest {

    private boolean isNumericKeyboard_true = true;
    private boolean isNumericKeyboard_false = false;

    private BasePresenter presenter;

    @Mock
    Repository repository;

    @Mock
    BasePresenterListener listener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new BasePresenter(repository);
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
        assertThat(listener,instanceOf(BasePresenterListener.class));
    }

    @Test
    public void getKeyboardStatus_true() {
        saveKeyboardType_true();
        assertTrue(presenter.getKeyboardStatus());
    }

    @Test
    public void getKeyboardStatus_false() {
        saveKeyboardType_false();
        assertFalse(presenter.getKeyboardStatus());
    }
}