package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.data.roomdatabase.crud.EntryCrud;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitechnologies.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.CreateEntryNotifier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public class CreateEntryPresenterTest {

    private CreateEntryPresenter presenter;

    @Mock
    CreateEntryNotifier entryNotifier;
    @Mock
    EntryCrud entryCrud;
    @Mock
    CacheRepository cacheRepository;
    @Mock
    EntryModel newEntry;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new CreateEntryPresenter(cacheRepository, entryCrud);
        presenter.setListener(entryNotifier);
    }

    private EntryModel entryModelStub() {

        newEntry.setUserId(1);
        newEntry.setName("name");
        newEntry.setUserName("username");
        newEntry.setPassword("pass");
        newEntry.setWebsite("website");
        newEntry.setNote("note");

        return newEntry;
    }

    @Test
    public void createNewEntry() {

        HashMap<String, Long> userMap = new HashMap<>();

        userMap.put("Id",1L);

        when(cacheRepository.getUserId()).thenReturn(userMap);
        when(entryCrud.create(entryModelStub())).thenReturn(1L);
        presenter.createNewEntry(entryModelStub());

        verify(entryNotifier).onNewEntryCreated(1);
    }
}