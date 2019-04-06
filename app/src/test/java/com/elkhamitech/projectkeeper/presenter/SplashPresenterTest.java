package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;
import com.elkhamitech.projectkeeper.viewnotifiyers.SplashPresenterListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void checkLoginStatus_logged() {
        when(repository.isLoggedIn()).thenReturn(true);
        presenter.checkLoginStatus();
        verify(listener).loggedIn();
    }

    @Test
    public void checkLoginStatus_notLogged() {
        when(repository.isLoggedIn()).thenReturn(false);
        presenter.checkLoginStatus();
        verify(listener).notLoggedIn();
    }
}