package com.elkhamitech.projectkeeper.data.roomdatabase.crud;

import com.elkhamitech.projectkeeper.data.roomdatabase.PasswordsDatabase;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;

import java.util.List;

public class SubEntryCrud {

    public static long createSubEntry(PasswordsDatabase db, SubEntryModel subEntryModel){
        return db.subEntryDao().createSubEntry(subEntryModel);
    }

    public static SubEntryModel getSubEntry(PasswordsDatabase db, long rowId){
        return db.subEntryDao().getSubEntry(rowId);
    }

    public static List<SubEntryModel> getSubEntries(PasswordsDatabase db, long parentId){
        return db.subEntryDao().getSubEntries(parentId);
    }

    public static void updateSubEntry(PasswordsDatabase db, SubEntryModel subEntryModel){
        db.subEntryDao().updateSubEntry(subEntryModel);
    }

    public static void deleteSubEntry(PasswordsDatabase db, SubEntryModel subEntryModel){
        db.subEntryDao().deleteSubEntry(subEntryModel);
    }
}
