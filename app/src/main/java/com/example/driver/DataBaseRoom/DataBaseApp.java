package com.example.driver.DataBaseRoom;

import android.content.Context;
import android.os.Environment;

import com.example.driver.DataBaseRoom.Tables.Driver.DriverDao;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDao;
import com.example.driver.DataBaseRoom.Tables.Police.PoliceDao;
import com.example.driver.DataBaseRoom.Tables.TypeVoi.TypeVoiDao;
import com.example.driver.DataBaseRoom.Tables.User.UserDoa;
import com.example.driver.Driver;
import com.example.driver.Manager;
import com.example.driver.Police;
import java.io.File;

import androidx.room.RoomDatabase;
import androidx.room.Database;
import androidx.room.Room;
import okhttp3.internal.Internal;

import static okhttp3.internal.Internal.instance;

@Database(entities = {Driver.class, Police.class, Manager.class},
        version = 1, exportSchema = false)

abstract class DataBaseApp extends RoomDatabase {

    private static final String DbDirectoryName = "POS";
    private static final String DATABASE_NAME = "TrafficGo.db";
    private static final Object LOCK = new Object();

    public abstract DriverDao driverDao();

    public abstract PoliceDao policeDao();

    public abstract ManagerDao managerDao();

    public abstract TypeVoiDao typeVoiDao();

    public abstract UserDoa userDoa();
    public static Internal getInstance(Context context) {
        if (instance == null) {
            createDirectoryIfNotExist();
            synchronized (LOCK) {
                if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext() , DataBaseApp.class , DATABASE_NAME)
                   .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }


        return instance;
    }


    private static void createDirectoryIfNotExist() {
        File myDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), DbDirectoryName);
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
    }


}