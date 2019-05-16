package com.elkhamitechnologies.projectkeeper.data.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.UserModel;

@Dao
public interface UserDao {

    @Insert
    long createUser (UserModel user);

    @Query("SELECT * FROM users WHERE pin = :pinCode")
    UserModel getRegisteredUser(String pinCode);
}
