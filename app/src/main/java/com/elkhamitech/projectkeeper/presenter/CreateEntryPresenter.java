package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.data.roomdatabase.crud.EntryCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.viewnotifiyers.CreateEntryNotifier;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public class CreateEntryPresenter implements SetPresenterListener<CreateEntryNotifier> {

    private CreateEntryNotifier entryNotifier;
    private LocalDbRepository<EntryModel, Long> entryCrud;
    private CacheRepository cacheRepository;

    @Inject
    CreateEntryPresenter(CacheRepository cacheRepository, EntryCrud entryCrud) {
        this.cacheRepository = cacheRepository;
        this.entryCrud = entryCrud;
    }

    @Override
    public void setListener(CreateEntryNotifier listener) {
        this.entryNotifier = listener;
    }

    public void createNewEntry(EntryModel newEntry) {

        long userId = cacheRepository.getRowDetails().get("Id");

        newEntry.setUserId(userId);

        long rowId = entryCrud.create(newEntry);

        entryNotifier.onNewEntryCreated(rowId);
    }

}
