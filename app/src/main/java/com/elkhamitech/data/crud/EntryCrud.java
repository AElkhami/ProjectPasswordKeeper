package com.elkhamitech.data.crud;

import com.elkhamitech.data.PasswordsDatabase;
import com.elkhamitech.data.model.EntryModel;

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
