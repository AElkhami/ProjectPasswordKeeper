package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class SplashPresenterTest {

    private SplashPresenter presenter;

    @Mock
    private Repository repository;
    @Mock
    private SplashPresenterListener listener;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new SplashPresenter(repository);
        presenter.setListener(listener);
    }

    @Test
    public void setListener() {
        assertThat(listener, instanceOf(SplashPresenterListener.class));
    }

    @Test
    public void checkLoginStatus_logged() {
        repository.createLoginSession(0,"123456");
        presenter.checkLoginStatus();
        //todo fix this test
        verify(listener).loggedIn();
    }

    @Test
    public void checkLoginStatus_notLogged() {
        presenter.checkLoginStatus();
        verify(listener).notLoggedIn();
    }
}