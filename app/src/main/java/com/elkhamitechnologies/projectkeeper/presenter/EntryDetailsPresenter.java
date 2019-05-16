package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.Constants;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.LocalDbRepository;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.crud.SubEntryCrud;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.SubEntryModel;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.EntryDetailsNotifier;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.SetViewNotifier;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public class EntryDetailsPresenter implements SetViewNotifier<EntryDetailsNotifier> {

    private LocalDbRepository<SubEntryModel, Long> subEntryCrud;

    private EntryDetailsNotifier notifier;

    @Inject
    BasePresenter basePresenter;

    @Inject
    EntryDetailsPresenter(SubEntryCrud subEntryCrud){
        this.subEntryCrud = subEntryCrud;
    }

    @Override
    public void setListener(EntryDetailsNotifier listener) {
        this.notifier = listener;
    }

    public void getSelectedSubEntry(long id){
        SubEntryModel subEntry = subEntryCrud.readFromDb(id);
        notifier.onSelectedSubEntryReceived(subEntry);
    }

    public void editSelectedSubEntry(SubEntryModel subEntry){
        subEntry.setCreatedAt(basePresenter.getCurrentDate());

        subEntryCrud.update(subEntry);
        notifier.userMessage(Constants.SUCCESS_EDIT);
    }

    public void deleteSelectedSubEntry(SubEntryModel subEntry){
        subEntryCrud.delete(subEntry);
        notifier.userMessage(Constants.SUCCESS_DELETE);
    }

    public void createSubEntry(SubEntryModel subEntry) {
        subEntry.setCreatedAt(basePresenter.getCurrentDate());
        subEntryCrud.create(subEntry);
    }
}
