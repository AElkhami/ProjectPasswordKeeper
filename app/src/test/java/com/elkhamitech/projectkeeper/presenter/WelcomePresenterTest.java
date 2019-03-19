package com.elkhamitech.projectkeeper.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WelcomePresenterTest {

    private WelcomePresenter welcomePresenter;

    @Mock
    WelcomePresenterImpl impl;

    @Before
    public void setUp() throws Exception {

        welcomePresenter = new WelcomePresenter(impl);

    }

    @Test
    public void validateInputs() {
        welcomePresenter.validateInputs("123456");
    }

    @Test
    public void createPassword() {
    }

    @Test
    public void importDB() {
    }
}