package com.elkhamitech.projectkeeper.data.roomdatabase.crud;

import com.elkhamitech.projectkeeper.data.roomdatabase.PasswordsDatabase;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;

import java.util.List;

import javax.inject.Inject;

public class EntryCrud implements LocalDbRepository<EntryModel, Long>{

    private EntryModel entryModel = new EntryModel();
    private final PasswordsDatabase db;

    @Inject
    public EntryCrud(PasswordsDatabase db){
        this.db = db;
    }

    @Override
    public List<EntryModel> getListDb(Long userId) {
        return db.entryDao().getEntries(userId);
    }

    @Override
    public EntryModel readFromDb(Long password) {
        return null;
    }

    @Override
    public long create(EntryModel entryModel) {
        return db.entryDao().createEntry(entryModel);
    }

    @Override
    public void update(EntryModel entity) {
        db.entryDao().updateEntry(entity);
    }

    @Override
    public void delete(EntryModel entity) {
        db.entryDao().deleteEntry(entity);
    }
}
