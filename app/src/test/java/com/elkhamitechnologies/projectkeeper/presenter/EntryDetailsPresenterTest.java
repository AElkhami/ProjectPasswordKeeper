package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.Constants;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.crud.SubEntryCrud;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.SubEntryModel;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.EntryDetailsNotifier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by A.Elkhami on 15,April,2019
 */
public class EntryDetailsPresenterTest {

    private EntryDetailsPresenter presenter;

    @Mock
    EntryDetailsNotifier notifier;
    @Mock
    SubEntryCrud subEntryCrud;
    private SubEntryModel subEntryModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new EntryDetailsPresenter(subEntryCrud);
        presenter.setListener(notifier);


        subEntryModel = new SubEntryModel();
    }

    private SubEntryModel subEntryModelStub(){

        subEntryModel.setParentId(1);
        subEntryModel.setName("Name 1");
        subEntryModel.setUserName("UserName 1");
        subEntryModel.setPassword("Password");

        return subEntryModel;
    }


    @Test
    public void getSelectedEntry() {

        long userId = 1L;

        when(subEntryCrud.readFromDb(userId))
                .thenReturn(subEntryModelStub());

        presenter.getSelectedSubEntry(userId);

        verify(notifier).onSelectedSubEntryReceived(subEntryModelStub());
    }

    @Test
    public void editSelectedSubEntry() {

        subEntryModelStub().setName("Name 2");
        subEntryCrud.update(subEntryModelStub());

        presenter.editSelectedSubEntry(subEntryModelStub());

        verify(notifier).userMessage(Constants.SUCCESS_EDIT);
    }

    @Test
    public void deleteSelectedSubEntry() {

        subEntryCrud.delete(subEntryModelStub());

        presenter.deleteSelectedSubEntry(subEntryModelStub());

        verify(notifier).userMessage(Constants.SUCCESS_DELETE);
    }

    @Test
    public void createSubEntry() {

        subEntryCrud.create(subEntryModelStub());
        presenter.createSubEntry(subEntryModelStub());
        verify(subEntryCrud).create(subEntryModelStub());
    }
}