package com.example.driver.DataBaseRoom.Tables.TypeVoi;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "TypeVoi")

class TypeVoiDB  implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int driverId;
    @NonNull
    private String name;
    private String date;
    private String amount;
    private String type;
    private int CarNumber;
    private double lat;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(int carNumber) {
        CarNumber = carNumber;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    private double lang;
}
