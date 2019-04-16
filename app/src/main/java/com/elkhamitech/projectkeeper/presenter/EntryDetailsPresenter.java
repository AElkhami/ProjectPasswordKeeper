package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.SubEntryCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;
import com.elkhamitech.projectkeeper.viewnotifiyers.EntryDetailsNotifier;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public class EntryDetailsPresenter implements SetPresenterListener<EntryDetailsNotifier> {

    private LocalDbRepository<SubEntryModel, Long> subEntryCrud;

    private EntryDetailsNotifier notifier;

    @Inject
    EntryDetailsPresenter(SubEntryCrud subEntryCrud){
        this.subEntryCrud = subEntryCrud;
    }

    public void getSelectedSubEntry(long id){
        SubEntryModel subEntry = subEntryCrud.readFromDb(id);
        notifier.onSelectedSubEntryReceived(subEntry);
    }

    public void editSelectedSubEntry(SubEntryModel subEntry){
        subEntryCrud.update(subEntry);
        notifier.userMessage(Constants.SUCCESS_EDIT);
    }

    public void deleteSelectedSubEntry(SubEntryModel subEntry){
        subEntryCrud.delete(subEntry);
        notifier.userMessage(Constants.SUCCESS_DELETE);
    }

    @Override
    public void setListener(EntryDetailsNotifier listener) {
        this.notifier = listener;
    }

    public void createSubEntry(SubEntryModel subEntry) {
        subEntryCrud.create(subEntry);
    }
}
