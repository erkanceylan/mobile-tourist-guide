package com.touristguide.mobile.mobiletouristguide.Models;

import java.util.ArrayList;

//Without Places
public class City {
    private String cityId;
    private String name;
    private String country;
    private ArrayList<String> places;

    public City(String cityId,String name){
        this.cityId = cityId;
        this.name = name;
        this.country = null;
        this.places = null;
    }

    public City(String cityId, String name, String country, ArrayList<String> places) {
        this.cityId = cityId;
        this.name = name;
        this.country = country;
        this.places = places;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country= country;
    }

    public ArrayList<String> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<String> places) {
        this.places = places;
    }
}
