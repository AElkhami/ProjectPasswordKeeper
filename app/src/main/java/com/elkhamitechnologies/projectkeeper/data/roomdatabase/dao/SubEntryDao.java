package com.elkhamitechnologies.projectkeeper.data.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.SubEntryModel;

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
