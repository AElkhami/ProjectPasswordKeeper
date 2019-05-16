package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.Constants;
import com.elkhamitechnologies.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.BaseNotifier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by A.Elkhami on 03,April,2019
 */
public class BasePresenterTest {

    private BasePresenter basePresenter;

    @Mock
    private CacheRepository cacheRepository;
    @Mock
    private BaseNotifier listener;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        basePresenter = new BasePresenter(cacheRepository);
    }

    @Test
    public void setListener() {
        basePresenter.setListener(listener);
        assertThat(listener, instanceOf(BaseNotifier.class));
    }

    @Test
    public void getKeyboardStatus_numeric() {
        HashMap<String, Boolean> hashMap = new HashMap<>();
        hashMap.put(Constants.KEYBOARD_TYPE, true);

        when(cacheRepository.getKeyboardType())
                .thenReturn(hashMap);

        assertTrue(basePresenter.getKeyboardStatus());
    }

    @Test
    public void getKeyboardStatus_nonNumeric() {
        HashMap<String, Boolean> hashMap = new HashMap<>();
        hashMap.put(Constants.KEYBOARD_TYPE, false);

        when(cacheRepository.getKeyboardType())
                .thenReturn(hashMap);

        assertFalse(basePresenter.getKeyboardStatus());
    }

}