package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.EntryCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.viewnotifiyers.HomeNotifier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by A.Elkhami on 11,April,2019
 */
public class HomePresenterTest {

    private HomePresenter presenter;

    @Mock
    HomeNotifier homeNotifier;
    @Mock
    EntryCrud entryCrud;
    @Mock
    List<EntryModel> entryModels;
    @Mock
    EntryModel entryModel;
    @Mock
    CacheRepository cacheRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new HomePresenter(cacheRepository, entryCrud);
        presenter.setListener(homeNotifier);
    }

    private EntryModel entryModelStub(){

        entryModel.setUserId(1);
        entryModel.setName("Name 1");
        entryModel.setUserName("User Name 1");
        entryModel.setPassword("Password 1");

        return entryModel;
    }


    @Test
    public void getPasswordsListCorrectId() {

        HashMap<String, Long> userMap = new HashMap<>();

        userMap.put("Id",1L);

        entryModels.add(entryModelStub());

        when(cacheRepository.getRowDetails()).thenReturn(userMap);

        when(entryCrud.getListDb(userMap.get("Id"))).thenReturn(entryModels);

        presenter.getPasswordsList();

        verify(homeNotifier).displayPasswordsList(entryModels);
    }

    private HashMap<String,Long> cachedUserStub(){
        HashMap<String,Long> userData = new HashMap<>();
        userData.put("Id",1L);

        return userData;
    }

    @Test
    public void getPasswordsListNullId() {

        HashMap<String, Long> userMap = new HashMap<>();

//        userMap.put("Id",1L);

        entryModels.add(entryModelStub());

        when(cacheRepository.getRowDetails()).thenReturn(userMap);

        when(entryCrud.getListDb(userMap.get("Id"))).thenReturn(entryModels);

        presenter.getPasswordsList();

        verify(homeNotifier).userMessage(Constants.ERROR_DATA_RETRIEVE);
    }

    @Test
    public void getPasswordsListNullData() {
        entryModels = null;

        when(cacheRepository.getRowDetails()).thenReturn(cachedUserStub());

        long userId = cachedUserStub().get("Id");

        when(entryCrud.getListDb(userId)).thenReturn(entryModels);

        presenter.getPasswordsList();

        verify(homeNotifier).userMessage(Constants.ERROR_DATA_RETRIEVE);
    }

    @Test
    public void deletePasswordEntry() {

        presenter.deletePasswordEntry(entryModelStub());
        verify(homeNotifier).userMessage(Constants.SUCCESS_DELETE);
    }
}