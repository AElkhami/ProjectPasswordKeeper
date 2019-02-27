package com.elkhamitech.data.model;

/**
 * Created by Ahmed on 1/5/2017.
 */

public class EntryModel {

    private long e_row_id;
    private String e_name;
    private String e_user_name;
    private String e_password;
    private String e_website;
    private String e_note;
    private long UserId;
    private String created_at;


    public EntryModel(){

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

    public String getE_website() {
        return e_website;
    }

    public void setE_website(String e_website) {
        this.e_website = e_website;
    }

    public String getE_note() {
        return e_note;
    }

    public void setE_note(String e_note) {
        this.e_note = e_note;
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
