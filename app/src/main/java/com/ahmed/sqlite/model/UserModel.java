package com.ahmed.sqlite.model;

/**
 * Created by Ahmed on 1/5/2017.
 */

public class UserModel {

    private long row_id;
    private String user_name;
    private String password;
    private String eMail;
    private String pin;

    public UserModel(){

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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
