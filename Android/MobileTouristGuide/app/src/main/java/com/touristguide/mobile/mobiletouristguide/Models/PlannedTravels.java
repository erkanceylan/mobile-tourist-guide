package com.touristguide.mobile.mobiletouristguide.Models;

import java.util.Calendar;
import java.util.Date;

public class PlannedTravels {
    private String userEmail;
    private Calendar startingDate;
    private Calendar finishingDate;
    private String tripName;
    private String locationName;
    private String cityOrCountryId;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Calendar getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Calendar startingDate) {
        this.startingDate = startingDate;
    }

    public Calendar getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(Calendar finishingDate) {
        this.finishingDate = finishingDate;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCityOrCountryId() {
        return cityOrCountryId;
    }

    public void setCityOrCountryId(String cityOrCountryId) {
        this.cityOrCountryId = cityOrCountryId;
    }

    public PlannedTravels(String userEmail, Calendar startingDate, Calendar finishingDate, String tripName, String locationName, String cityOrCountryId) {
        this.userEmail = userEmail;
        this.startingDate = startingDate;
        this.finishingDate = finishingDate;
        this.tripName = tripName;
        this.locationName=locationName;
        this.cityOrCountryId = cityOrCountryId;

    }
}
