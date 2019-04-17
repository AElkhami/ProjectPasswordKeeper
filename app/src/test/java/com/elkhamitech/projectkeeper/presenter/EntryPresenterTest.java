package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.EntryCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.SubEntryCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.EntryNotifier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by A.Elkhami on 15,April,2019
 */
public class EntryPresenterTest {

    private EntryPresenter presenter;

    @Mock
    EntryNotifier notifier;
    @Mock
    EntryCrud entryCrud;
    @Mock
    SubEntryCrud subEntryCrud;
    @Mock
    ArrayList<SubEntryModel> subEntryList;
    @Mock
    EntryModel entryModel;
    @Mock
    SubEntryModel subEntryModel;
    @Mock
    BasePresenter basePresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new EntryPresenter(entryCrud, subEntryCrud);
        presenter.setListener(notifier);
    }

    private EntryModel entryModelStub() {

        entryModel.setRowId(1);
        entryModel.setName("Name 1");
        entryModel.setUserName("UserName 1");
        entryModel.setPassword("Password");

        return entryModel;
    }

    private SubEntryModel subEntryModelStub() {

        subEntryModel.setParentId(1);
        subEntryModel.setName("Name 1");
        subEntryModel.setUserName("UserName 1");
        subEntryModel.setPassword("Password");

        return subEntryModel;
    }

    @Test
    public void getSelectedEntry() {

        long userId = 1L;

        when(entryCrud.readFromDb(userId))
                .thenReturn(entryModel);

        presenter.getSelectedEntry(userId);

        verify(notifier).onSelectedEntryReceived(entryModelStub());
    }

    @Test
    public void editSelectedEntry() {

        entryModelStub().setName("Name 2");
        entryModelStub().setCreatedAt(basePresenter.getCurrentDate());
        when(basePresenter.getCurrentDate()).thenReturn("a");

        entryCrud.update(entryModelStub());

        presenter.editSelectedEntry(entryModelStub());

        verify(notifier).userMessage(Constants.SUCCESS_EDIT);
    }

    @Test
    public void deleteSelectedEntry() {

        entryCrud.delete(entryModelStub());

        presenter.deleteSelectedEntry(entryModelStub());

        verify(notifier).userMessage(Constants.SUCCESS_DELETE);
    }

    @Test
    public void getSubEntries() {

        long parentId = entryModelStub().getRowId();
        subEntryList.add(subEntryModelStub());

        when(subEntryCrud.getListDb(parentId)).thenReturn(subEntryList);

        presenter.getSubEntries(parentId);

        verify(notifier).onSubEntryListReceived(subEntryList);
    }
}