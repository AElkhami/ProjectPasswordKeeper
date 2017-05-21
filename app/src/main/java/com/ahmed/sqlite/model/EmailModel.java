package com.ahmed.sqlite.model;

/**
 * Created by Ahmed on 1/5/2017.
 */

public class EmailModel {

    private long e_row_id;
    private String e_name;
    private String e_user_name;
    private String e_password;
    private long UserId;
    private String created_at;


    public EmailModel(){

    }

    //------Setters & Getters -----------


    public long getE_row_id() {
        return e_row_id;
    }

    public void setE_row_id(long e_row_id) {
        this.e_row_id = e_row_id;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getE_user_name() {
        return e_user_name;
    }

    public void setE_user_name(String e_user_name) {
        this.e_user_name = e_user_name;
    }

    public String getE_password() {
        return e_password;
    }

    public void setE_password(String e_password) {
        this.e_password = e_password;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        this.UserId = userId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
