package com.example.ingatlanok;

import java.time.LocalDate;

public class PropertyModel {
    private String id;
    private String name;
    private float price;
    private String city;
    private String street;
    private String type;
    private float rooms;
    private String floors;
    private String heating;
    private String user;
    private LocalDate date;
    private int coverImageResource;

    public PropertyModel() {
    }

    public PropertyModel(String name, float price, String city, String street, String type,
                         float rooms, String floors, String heating, String user, LocalDate date, int coverImageResource) {
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
        this.coverImageResource = coverImageResource;
    }

    public PropertyModel(String name, float price, String city, int coverImageResource) { //Todo csak próba miatt
        this.name = name;
        this.price = price;
        this.city = city;
        this.coverImageResource = coverImageResource;
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

    public float getRooms() {
        return rooms;
    }

    public void setRooms(float rooms) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCoverImageResource() {
        return coverImageResource;
    }

    public void setCoverImageResource(int coverImageResource) {
        this.coverImageResource = coverImageResource;
    }

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
