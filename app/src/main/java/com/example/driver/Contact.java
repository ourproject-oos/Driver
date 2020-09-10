package com.example.driver;

public class Contact {

   int user_no, card_no,card_date,license_no,license_date,image,id;
   String name,type_of_car;

    public Contact(int user_no, int card_no, int card_date, int license_no, int license_date, int image, int id, String name, String type_of_car) {
        this.user_no = user_no;
        this.card_no = card_no;
        this.card_date = card_date;
        this.license_no = license_no;
        this.license_date = license_date;
        this.image = image;
        this.id = id;
        this.name = name;
        this.type_of_car = type_of_car;
    }

    public Contact() {

    }

    public int getUser_no() {
        return user_no;
    }

    public void setUser_no(int user_no) {
        this.user_no = user_no;
    }

    public int getCard_no() {
        return card_no;
    }

    public void setCard_no(int card_no) {
        this.card_no = card_no;
    }

    public int getCard_date() {
        return card_date;
    }

    public void setCard_date(int card_date) {
        this.card_date = card_date;
    }

    public int getLicense_no() {
        return license_no;
    }

    public void setLicense_no(int license_no) {
        this.license_no = license_no;
    }

    public int getLicense_date() {
        return license_date;
    }

    public void setLicense_date(int license_date) {
        this.license_date = license_date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_of_car() {
        return type_of_car;
    }

    public void setType_of_car(String type_of_car) {
        this.type_of_car = type_of_car;
    }
}
