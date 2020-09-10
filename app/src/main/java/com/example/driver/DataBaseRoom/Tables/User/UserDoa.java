package com.example.driver.DataBaseRoom.Tables.User;
import com.example.driver.Driver;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao

public interface UserDoa {

    @Insert()
    void insertUser(UserDoa user);

    @Query("SELECT * FROM user ")
    LiveData<UserDoa> getUser();
    List<UserDoa> getUsers();


    @Update
    void updateUser(UserDoa user);


    @Query("DELETE FROM user ")
    void UserDelete();

}
