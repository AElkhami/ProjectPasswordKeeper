package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.Constants;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.crud.EntryCrud;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.LocalDbRepository;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.crud.SubEntryCrud;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.SubEntryModel;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.EntryNotifier;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.SetViewNotifier;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 15,April,2019
 */
public class EntryPresenter implements SetViewNotifier<EntryNotifier> {

    private EntryNotifier notifier;

    private LocalDbRepository<SubEntryModel, Long> subEntryCrud;
    private LocalDbRepository<EntryModel, Long> entryCrud;

    @Inject
    BasePresenter basePresenter;

    @Inject
    EntryPresenter(EntryCrud entryCrud
            , SubEntryCrud subEntryCrud){

        this.entryCrud = entryCrud;
        this.subEntryCrud = subEntryCrud;
    }

    public void getSelectedEntry(long id){
        EntryModel entryModel = entryCrud.readFromDb(id);
        notifier.onSelectedEntryReceived(entryModel);
    }

    public void editSelectedEntry(EntryModel entryModel){
        entryModel.setCreatedAt(basePresenter.getCurrentDate());

        entryCrud.update(entryModel);
        notifier.userMessage(Constants.SUCCESS_EDIT);
    }

    public void deleteSelectedEntry(EntryModel entryModel){
        entryCrud.delete(entryModel);
        notifier.userMessage(Constants.SUCCESS_DELETE);

    }

    public void getSubEntries(long parentId){
        List<SubEntryModel> subEntryModelArrayList = subEntryCrud.getListDb(parentId);
        notifier.onSubEntryListReceived(subEntryModelArrayList);
    }

    @Override
    public void setListener(EntryNotifier listener) {
        notifier = listener;
    }
}
