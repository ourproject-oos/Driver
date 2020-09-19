package com.example.driver.DataBaseRoom.Tables.Manager;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "Manager")
public
class ManagerDB  implements Serializable  {

    @PrimaryKey(autoGenerate = true)
    private int managerId;
    @NonNull
    private String userName;
    private String DriverFirstName;
    private String PoliceFirstName;
    private String DriverLastName;
    private String PoliceLastName;
    private int password;
    private int rePassword;
    private int phone;
    private int carNumber;
    public String getDgree() {
        return dgree;
    }

    public void setDgree(String dgree) {
        this.dgree = dgree;
    }

    private String dgree;

    @NonNull
    public String getUserName() {
        return userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    private int userID;

    private String carType;

    @NonNull
    public String getUserName(String trim) {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getDriverFirstName() {
        return DriverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        DriverFirstName = driverFirstName;
    }

    public String getPoliceFirstName() {
        return PoliceFirstName;
    }

    public void setPoliceFirstName(String policeFirstName) {
        PoliceFirstName = policeFirstName;
    }

    public String getDriverLastName() {
        return DriverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        DriverLastName = driverLastName;
    }

    public String getPoliceLastName() {
        return PoliceLastName;
    }

    public void setPoliceLastName(String policeLastName) {
        PoliceLastName = policeLastName;
    }

    public int getRePassword() {
        return rePassword;
    }

    public void setRePassword(int rePassword) {
        this.rePassword = rePassword;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    private String userJob;
    private String adress;
    private String user;


    @NonNull

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
