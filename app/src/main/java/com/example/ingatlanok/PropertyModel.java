package com.example.ingatlanok;

import java.time.LocalDate;

public class PropertyModel {
    private String id;
    private String name;
    private float price;
    private String city;
    private String street;
    private float size;
    private float rooms;
    private String heating;
    private String description;
    private String user;
    private int coverImageResource;

    public PropertyModel() {
    }

    public PropertyModel(String name, float price, String city, String street,
                         float size, float rooms, String heating, String description, String user, int coverImageResource) {
        this.name = name;
        this.price = price;
        this.city = city;
        this.street = street;
        this.size = size;
        this.rooms = rooms;
        this.heating = heating;
        this.description = description;
        this.user = user;
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

    public float getRooms() {
        return rooms;
    }

    public void setRooms(float rooms) {
        this.rooms = rooms;
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

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
