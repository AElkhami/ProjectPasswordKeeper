package com.elkhamitech.projectkeeper.data.roomdatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.elkhamitech.projectkeeper.data.roomdatabase.dao.EntryDao;
import com.elkhamitech.projectkeeper.data.roomdatabase.dao.SubEntryDao;
import com.elkhamitech.projectkeeper.data.roomdatabase.dao.UserDao;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;

@Database(entities = {UserModel.class, EntryModel.class, SubEntryModel.class}, version = 1)
public abstract class PasswordsDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract EntryDao entryDao();

    public abstract SubEntryDao subEntryDao();

    private final static String DB_NAME = "passkeep";

    public static PasswordsDatabase initDatabase(Context context) {

        return Room
                .databaseBuilder(context, PasswordsDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }


}
