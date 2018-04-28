package com.touristguide.mobile.mobiletouristguide.Models;

import java.util.ArrayList;

public class User {
    private String photo;
    private String name;
    private String email;
    private String password;
    private double latitude;
    private double longitude;
    private ArrayList<String> plannedTravels;

    public User(String photo, String name, String email, String password, double latitude, double longitude, ArrayList<String> plannedTravels) {
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.plannedTravels = plannedTravels;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getPlannedTravels() {
        return plannedTravels;
    }

    public void setPlannedTravels(ArrayList<String> plannedTravels) {
        this.plannedTravels = plannedTravels;
    }
}
