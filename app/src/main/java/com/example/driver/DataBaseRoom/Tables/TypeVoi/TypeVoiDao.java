package com.example.driver.DataBaseRoom.Tables.TypeVoi;
import com.example.driver.Driver;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao

public interface TypeVoiDao {

    @Insert()
    void insertTypeVoi(TypeVoiDB driver);

    @Query("SELECT * FROM driver ")
    LiveData<TypeVoiDB> getTypeVoi();

    List<TypeVoiDB> getTypeVois();


    @Update
    void updateTypeVoi(TypeVoiDB typeVoi);


}