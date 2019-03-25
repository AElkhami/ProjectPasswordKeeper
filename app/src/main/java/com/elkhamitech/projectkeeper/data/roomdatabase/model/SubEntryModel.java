package com.elkhamitech.projectkeeper.data.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ahmed on 1/24/2017.
 */

@Entity(tableName = "sub_entry")
public class SubEntryModel {

    @PrimaryKey
    @ColumnInfo(name = "sub_entry_id")
    private long rowId;
    @ColumnInfo(name = "sub_entry_name")
    private String name;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "website")
    private String website;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "parent_entry_id")
    private long parentId;
    @ColumnInfo(name = "created_at")
    private String createdAt;

//    @Ignore
    public SubEntryModel() {
    }

    public SubEntryModel(long s_row_id, String name, String userName,
                         String s_password, String s_website, String s_note,
                         long parentId, String created_at) {
        this.rowId = s_row_id;
        this.name = name;
        this.userName = userName;
        this.password = s_password;
        this.website = s_website;
        this.note = s_note;
        this.parentId = parentId;
        this.createdAt = created_at;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
