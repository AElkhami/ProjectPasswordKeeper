package com.elkhamitech.projectkeeper.data.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;

import java.util.List;

@Dao
public interface EntryDao {

    @Insert
    long createEntry(EntryModel entryModel);

    @Query("SELECT * FROM entry WHERE entry_id = :rowId")
    EntryModel getEntry(long rowId);

    @Query("SELECT * FROM entry WHERE user_id = :userId")
    List<EntryModel> getEntries(long userId);

    @Update
    void updateEntry(EntryModel entryModel);

    @Delete
    void deleteEntry(EntryModel entryModel);
}
