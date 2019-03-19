package com.elkhamitech.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.elkhamitech.data.model.EntryModel;

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
