package com.example.driver;

public class VioClass {
/*"NAME": "yousra nabil",
        "PHONE": 725745789,
        "PASSWORD": 1234,
        "USER_NAME": "yousra",
        "CREATED_DATE": "2020-02-23 11:14:00",
        "CAR_NUM": 123456,
        "CAR_TYPE": 25806,
        "JOB": "business",
        "ADDRESS": "sanaa",
        "GENDER": "female",
        "DRIVER_ID": 3,
        "POLICE_ID": 0,
        "AMOUNT": 5000,
        "TYPE": "accident",
        "DATE": "2020-02-23 23:06:11",
        "LAT": 0,
        "LANG": 0*/

String name,date,amount,type,carNumber;
double latLocation,longLocation;
int id;


    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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



    public double getLatLocation() {
        return latLocation;
    }

    public void setLatLocation(double latLocation) {
        this.latLocation = latLocation;
    }

    public double getLongLocation() {
        return longLocation;
    }

    public void setLongLocation(double longLocation) {
        this.longLocation = longLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
