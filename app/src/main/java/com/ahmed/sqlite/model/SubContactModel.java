package com.ahmed.sqlite.model;

/**
 * Created by Ahmed on 1/24/2017.
 */

public class SubContactModel {

    private long s_row_id;
    private String s_name;
    private String s_user_name;
    private String s_password;
    private String s_website;
    private String s_note;
    private long parentId;
    private String created_at;

    public SubContactModel() {
    }

    public long getS_row_id() {
        return s_row_id;
    }

    public void setS_row_id(long s_row_id) {
        this.s_row_id = s_row_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_user_name() {
        return s_user_name;
    }

    public void setS_user_name(String s_user_name) {
        this.s_user_name = s_user_name;
    }

    public String getS_password() {
        return s_password;
    }

    public void setS_password(String s_password) {
        this.s_password = s_password;
    }

    public String getS_website() {
        return s_website;
    }

    public void setS_website(String s_website) {
        this.s_website = s_website;
    }

    public String getS_note() {
        return s_note;
    }

    public void setS_note(String s_note) {
        this.s_note = s_note;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
