package com.example.driver.DataBaseRoom.Tables.Police;
import com.example.driver.Driver;
import com.example.driver.Police;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao

public interface PoliceDao {

    @Insert()
    void insertPolice(PoliceDB police);

    @Query("SELECT * FROM police   ")
    LiveData<PoliceDB> getPolice();
    List<PoliceDB> getPolices();



    @Update
    void updatePolice(PoliceDB police);


    @Query("DELETE FROM police ")
    void PoliceDelete();

}
