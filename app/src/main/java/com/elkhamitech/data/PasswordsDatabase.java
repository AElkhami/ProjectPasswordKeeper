package com.elkhamitech.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import com.elkhamitech.data.dao.EntryDao;
import com.elkhamitech.data.dao.SubEntryDao;
import com.elkhamitech.data.dao.UserDao;
import com.elkhamitech.data.model.EntryModel;
import com.elkhamitech.data.model.SubEntryModel;
import com.elkhamitech.data.model.UserModel;

@Database(entities = {UserModel.class, EntryModel.class, SubEntryModel.class}, version = 1)
public abstract class PasswordsDatabase extends RoomDatabase {

    private static PasswordsDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract EntryDao entryDao();
    public abstract SubEntryDao subEntryDao();

    private final static String DB_NAME = "passkeep";

    public static PasswordsDatabase initDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context, PasswordsDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static PasswordsDatabase getDatabase() {
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
