package com.example.driver.DataBaseRoom.Tables.Driver;

import com.example.driver.Driver;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface DriverDao {

    @Insert()
    void insertDriver(DriverBD driver);

    @Query("SELECT * FROM driver ")
    LiveData<DriverBD> getDriver();

    List<DriverBD> getDrivers();




    @Update
    void updateDriver(DriverBD driver);


    @Query("DELETE FROM driver ")
    void DriverDelete();


}
