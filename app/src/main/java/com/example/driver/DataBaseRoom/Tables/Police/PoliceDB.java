package com.example.driver.DataBaseRoom.Tables.Police;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "Police")

class PoliceDB implements Serializable  {

    @PrimaryKey(autoGenerate = true)
    private int policeId;

    @NonNull
    private String userName;
    private int password;
    private String Name;
    private int phoneNo;
    private String dgree;
    private int Job_id;
    private String address;
    private double lat;
    private double laog;
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDgree() {
        return dgree;
    }

    public void setDgree(String dgree) {
        this.dgree = dgree;
    }

    public int getJob_id() {
        return Job_id;
    }

    public void setJob_id(int job_id) {
        Job_id = job_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLaog() {
        return laog;
    }

    public void setLaog(double laog) {
        this.laog = laog;
    }
}
