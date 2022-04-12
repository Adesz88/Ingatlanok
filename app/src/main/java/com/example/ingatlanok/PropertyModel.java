package com.example.ingatlanok;

import java.time.LocalDate;

public class PropertyModel {
    private String name;
    private double price;
    private String city;
    private String street;
    private String type;
    private double rooms;
    private String floors;
    private String heating;
    private String user;
    private LocalDate date;

    public PropertyModel(String name, double price, String city, String street, String type,
                         double rooms, String floors, String heating, String user, LocalDate date) {
        this.name = name;
        this.price = price;
        this.city = city;
        this.street = street;
        this.type = type;
        this.rooms = rooms;
        this.floors = floors;
        this.heating = heating;
        this.user = user;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRooms() {
        return rooms;
    }

    public void setRooms(double rooms) {
        this.rooms = rooms;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
