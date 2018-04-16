package com.touristguide.mobile.mobiletouristguide.Models;

import java.util.ArrayList;

public class Place {
    private String placeId;
    private double rating;
    private double latitude;
    private double longitude;
    private String name;
    private String cityCountryName;
    private String description;
    private String longDescription;
    private String thumbnail;
    private String marker;
    private ArrayList<String> parentIds;
    private ArrayList<String> categories;
    private ArrayList<String> media;

    public Place(String placeId, double rating, double latitude, double longitude, String name, String cityCountryName, String description, String longDescription, String thumbnail, String marker, ArrayList<String> parentIds, ArrayList<String> categories, ArrayList<String> media) {
        this.placeId = placeId;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.cityCountryName = cityCountryName;
        this.description = description;
        this.longDescription = longDescription;
        this.thumbnail = thumbnail;
        this.marker = marker;
        this.parentIds = parentIds;
        this.categories = categories;
        this.media = media;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCountryName() {
        return cityCountryName;
    }

    public void setCityCountryName(String cityCountryName) {
        this.cityCountryName = cityCountryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public ArrayList<String> getParentIds() {
        return parentIds;
    }

    public void setParentIds(ArrayList<String> parentIds) {
        this.parentIds = parentIds;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<String> media) {
        this.media = media;
    }
}
