package com.elkhamitech.projectkeeper.data.roomdatabase.crud;

import com.elkhamitech.projectkeeper.data.roomdatabase.PasswordsDatabase;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;

import java.util.List;

public class EntryCrud {

    public static long createEntry(PasswordsDatabase db, EntryModel entryModel){
        return db.entryDao().createEntry(entryModel);
    }

    public static EntryModel getEntry(PasswordsDatabase db, long rowId){
        return db.entryDao().getEntry(rowId);
    }

    public static List<EntryModel> getEntries(PasswordsDatabase db, long userId){
        return db.entryDao().getEntries(userId);
    }

    public static void updateEntry(PasswordsDatabase db, EntryModel entryModel){
        db.entryDao().updateEntry(entryModel);
    }

    public static void deleteEntry(PasswordsDatabase db,EntryModel entryModel){
        db.entryDao().deleteEntry(entryModel);
    }
}
