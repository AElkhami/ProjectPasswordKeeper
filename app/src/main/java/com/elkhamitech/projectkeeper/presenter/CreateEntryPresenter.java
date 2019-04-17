package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.EntryCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.CreateEntryNotifier;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.SetViewNotifier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public class CreateEntryPresenter implements SetViewNotifier<CreateEntryNotifier> {

    private CreateEntryNotifier entryNotifier;
    private LocalDbRepository<EntryModel, Long> entryCrud;
    private CacheRepository cacheRepository;

    @Inject
    BasePresenter basePresenter;

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

        long userId = cacheRepository.getUserId().get("Id");

        newEntry.setUserId(userId);
        newEntry.setCreatedAt(basePresenter.getCurrentDate());

        long rowId = entryCrud.create(newEntry);

        entryNotifier.onNewEntryCreated(rowId);
    }

}
