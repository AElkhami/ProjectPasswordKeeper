package com.elkhamitech.projectkeeper.presenter;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.EntryCrud;
import com.elkhamitech.projectkeeper.data.roomdatabase.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.HomeNotifier;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.SetViewNotifier;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by A.Elkhami on 06,April,2019
 */
public class HomePresenter implements SetViewNotifier<HomeNotifier> {

    private HomeNotifier homeNotifier;
    private LocalDbRepository<EntryModel, Long> entryCrud;
    private CacheRepository cacheRepository;

    @Inject
    HomePresenter(CacheRepository cacheRepository, EntryCrud entryCrud) {
        this.entryCrud = entryCrud;
        this.cacheRepository = cacheRepository;
    }

    public void getPasswordsList() {
        EntryModel entryModel = new EntryModel();

        if (cacheRepository.getUserId().get("Id") != null) {

            entryModel.setRowId(cacheRepository.getUserId().get("Id"));

            if (entryCrud.getListDb(entryModel.getRowId()) != null) {
                List<EntryModel> entriesList = entryCrud.getListDb(entryModel.getRowId());
                homeNotifier.displayPasswordsList(entriesList);
            } else {
                homeNotifier.userMessage(Constants.ERROR_DATA_RETRIEVE);
            }
        } else {
            homeNotifier.userMessage(Constants.ERROR_DATA_RETRIEVE);
        }


    }

    public void deletePasswordEntry(EntryModel entity) {
        if(entity != null){
            entryCrud.delete(entity);
            homeNotifier.userMessage(Constants.SUCCESS_DELETE);
        }
    }

    @Override
    public void setListener(HomeNotifier listener) {
        this.homeNotifier = listener;
    }
}
