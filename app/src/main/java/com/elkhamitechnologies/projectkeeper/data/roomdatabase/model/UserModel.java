package com.elkhamitechnologies.projectkeeper.data.roomdatabase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by Ahmed on 1/5/2017.
 */

@Entity(tableName = "users")
public class UserModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private long row_id;
    @ColumnInfo(name = "user_name")
    private String user_name;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "pin")
    private String pin;

    @Ignore
    public UserModel(){
    }

    public UserModel(String pin) {
        this.pin = pin;
    }

    @Ignore
    public UserModel(long row_id, String user_name, String password,
                     String email, String pin) {
        this.row_id = row_id;
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.pin = pin;
    }

    public long getRow_id() {
        return row_id;
    }

    public void setRow_id(long row_id) {
        this.row_id = row_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
