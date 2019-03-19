package com.elkhamitech.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.elkhamitech.data.model.SubEntryModel;

import java.util.List;

@Dao
public interface SubEntryDao {

    @Insert
    long createSubEntry(SubEntryModel subEntryModel);

    @Query("SELECT * FROM sub_entry WHERE sub_entry_id = :rowId")
    SubEntryModel getSubEntry(long rowId);

    @Query("SELECT * FROM sub_entry WHERE parent_entry_id = :parentId")
    List<SubEntryModel> getSubEntries(long parentId);

    @Update
    void updateSubEntry(SubEntryModel subEntryModel);

    @Delete
    void deleteSubEntry(SubEntryModel subEntryModel);
}
