package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.SplashNotifier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SplashPresenterTest {

    private SplashPresenter presenter;

    @Mock
    private CacheRepository cacheRepository;
    @Mock
    private SplashNotifier listener;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new SplashPresenter(cacheRepository);
        presenter.setListener(listener);
    }

    @Test
    public void checkLoginStatus_logged() {
        when(cacheRepository.isLoggedIn()).thenReturn(true);
        presenter.checkLoginStatus();
        verify(listener).loggedIn();
    }

    @Test
    public void checkLoginStatus_notLogged() {
        when(cacheRepository.isLoggedIn()).thenReturn(false);
        presenter.checkLoginStatus();
        verify(listener).notLoggedIn();
    }
}