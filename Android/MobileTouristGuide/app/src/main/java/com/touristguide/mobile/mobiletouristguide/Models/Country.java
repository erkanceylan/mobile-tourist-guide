package com.touristguide.mobile.mobiletouristguide.Models;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Map;

public class Country {
    private String countryId;
    private String name;
    private String capitalCity;
    private int population;
    private String flag;
    private ArrayList<City> cities;

    public Country(String countryId, String name, String capitalCity, int population, String flag, ArrayList<City> cities) {
        this.countryId = countryId;
        this.name = name;
        this.capitalCity = capitalCity;
        this.population = population;
        this.flag = flag;
        this.cities = cities;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}
