package com.example.driver.DataBaseRoom.Tables.Manager;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "Manager")
class ManagerBD  implements Serializable  {

    @PrimaryKey(autoGenerate = true)
    private int managerId;
    @NonNull
    private String name;
    private int password;
    private int phone;
    private String user;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

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
