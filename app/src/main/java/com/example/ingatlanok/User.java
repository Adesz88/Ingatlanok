package com.example.ingatlanok;

public class User {
    private String email;
    private String name;
    private String phone;
    private Boolean notification;

    public User() {
    }

    public User(String email, String name, String phone, boolean notification) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.notification = notification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }
}
