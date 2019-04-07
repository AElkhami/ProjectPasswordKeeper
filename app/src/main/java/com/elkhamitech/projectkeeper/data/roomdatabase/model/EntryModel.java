package com.elkhamitech.projectkeeper.data.roomdatabase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Ahmed on 1/5/2017.
 */

@Entity(tableName = "entry")
public class EntryModel {

    @PrimaryKey
    @ColumnInfo(name = "entry_id")
    private long rowId;
    @ColumnInfo(name = "entry_name")
    private String name;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "website")
    private String website;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "user_id")
    private long userId;
    @ColumnInfo(name = "created_at")
    private String createdAt;

    public EntryModel(){
    }

    public EntryModel(long row_id, String name, String user_name, String password,
                      String website, String note, long userId, String created_at) {
        this.rowId = row_id;
        this.name = name;
        this.userName = user_name;
        this.password = password;
        this.website = website;
        this.note = note;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
