package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.viewnotifiyers.BasePresenterListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by A.Elkhami on 03,April,2019
 */
public class BasePresenterImplTest {

    private BasePresenterImpl basePresenter;

    @Mock
    private CacheRepository cacheRepository;
    @Mock
    private BasePresenterListener listener;
    @Mock
    private BasePresenterContract basePresenterContract;

    private static final boolean isNumericKeyboard = true;
    private static final boolean isNotNumericKeyboard = false;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        basePresenter = new BasePresenterImpl(cacheRepository);
    }

    @Test
    public void setListener() {
        basePresenter.setListener(listener);
        assertThat(listener, instanceOf(BasePresenterListener.class));
    }

    @Test
    public void saveKeyboardType_numeric() {
        //no need to test this method
        when(basePresenterContract.getKeyboardStatus()).thenReturn(isNumericKeyboard);

        basePresenter.saveKeyboardType(isNumericKeyboard);

        assertTrue(basePresenterContract.getKeyboardStatus());
    }

    @Test
    public void saveKeyboardType_nonNumeric() {
        //no need to test this method
        when(basePresenterContract.getKeyboardStatus()).thenReturn(isNotNumericKeyboard);

        basePresenter.saveKeyboardType(isNotNumericKeyboard);

        assertFalse(basePresenterContract.getKeyboardStatus());
    }

    @Test
    public void getKeyboardStatus_numeric() {
        HashMap<String, Boolean> hashMap = new HashMap<>();
        hashMap.put(Constants.KEYBOARD_TYPE, true);

        when(cacheRepository.getKeyboardDetails())
                .thenReturn(hashMap);

        when(basePresenterContract.getKeyboardStatus())
                .thenReturn(hashMap.get(Constants.KEYBOARD_TYPE));

        assertTrue(basePresenter.getKeyboardStatus());
    }

    @Test
    public void getKeyboardStatus_nonNumeric() {
        HashMap<String, Boolean> hashMap = new HashMap<>();
        hashMap.put(Constants.KEYBOARD_TYPE, false);

        when(cacheRepository.getKeyboardDetails())
                .thenReturn(hashMap);

        when(basePresenterContract.getKeyboardStatus())
                .thenReturn(hashMap.get(Constants.KEYBOARD_TYPE));

        assertFalse(basePresenter.getKeyboardStatus());
    }

}