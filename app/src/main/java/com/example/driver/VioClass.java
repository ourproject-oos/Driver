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



String name,date,type,carNumber
    ,licence,cardDate,carType,address,amount ;
double latLocation,longLocation;
int id,driverId,policeId;


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

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getPoliceId() {
        return policeId;
    }

    public void setPoliceId(int policeId) {
        this.policeId = policeId;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getType(String type) {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getType() {
        return type;

    }
}
