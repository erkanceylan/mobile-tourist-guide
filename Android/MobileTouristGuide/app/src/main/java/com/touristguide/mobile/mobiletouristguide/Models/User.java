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
}
