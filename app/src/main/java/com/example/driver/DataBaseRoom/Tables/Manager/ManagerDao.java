package com.example.driver.DataBaseRoom.Tables.Manager;
import com.example.driver.Driver;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao

public interface ManagerDao {

    @Insert()
    void insertManager(ManagerDB manager);

    @Query("SELECT * FROM manager ")
    LiveData<ManagerDao> getManager();
    List<ManagerDao> getManagers();


    @Update
    void updateManager(ManagerDao manager);


    @Query("DELETE FROM manager ")
    void ManagerDelete();
}
