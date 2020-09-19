package com.example.driver.DataBaseRoom.Tables.TypeVoi;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "type_voi")

class TypeVoiDB  implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int driverId;
    @NonNull
    private String TypeVoi;
    private int SerialNum;


    @NonNull
    public String getTypeVoi() {
        return TypeVoi;
    }

    public void setTypeVoi(@NonNull String typeVoi) {
        TypeVoi = typeVoi;
    }

    public int getSerialNum() {
        return SerialNum;
    }

    public void setSerialNum(int SerialNum) {
        this.SerialNum = SerialNum;
    }
}
//https://play.google.com/apps/publish/internalappsharing