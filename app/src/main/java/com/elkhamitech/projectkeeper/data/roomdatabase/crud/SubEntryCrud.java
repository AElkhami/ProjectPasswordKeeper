package com.elkhamitech.projectkeeper.data.roomdatabase.crud;

import com.elkhamitech.projectkeeper.data.roomdatabase.LocalDbRepository;
import com.elkhamitech.projectkeeper.data.roomdatabase.PasswordsDatabase;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;

import java.util.List;

import javax.inject.Inject;

public class SubEntryCrud implements LocalDbRepository<SubEntryModel, Long> {

    private final PasswordsDatabase db;

    @Inject
    SubEntryCrud(PasswordsDatabase db){
        this.db = db;
    }

    @Override
    public long create(SubEntryModel subEntity) {
        return db.subEntryDao().createSubEntry(subEntity);
    }

    @Override
    public List<SubEntryModel> getListDb(Long id) {
        return db.subEntryDao().getSubEntries(id);
    }

    @Override
    public SubEntryModel readFromDb(Long id) {
        return db.subEntryDao().getSubEntry(id);
    }

    @Override
    public void update(SubEntryModel subEntity) {
        db.subEntryDao().updateSubEntry(subEntity);
    }

    @Override
    public void delete(SubEntryModel subEntity) {
        db.subEntryDao().deleteSubEntry(subEntity);
    }
}
