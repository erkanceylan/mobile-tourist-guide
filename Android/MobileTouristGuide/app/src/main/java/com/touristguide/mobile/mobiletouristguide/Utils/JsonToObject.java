package com.touristguide.mobile.mobiletouristguide.Utils;

import android.util.Log;

import com.touristguide.mobile.mobiletouristguide.Models.City;
import com.touristguide.mobile.mobiletouristguide.Models.Country;
import com.touristguide.mobile.mobiletouristguide.Models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class JsonToObject {
    public static City GetCityFromJson(String json) throws JSONException {
        String cityId,name,country;
        ArrayList<String> places=new ArrayList<>();
        JSONObject cityObject = new JSONObject(json);

        cityId=cityObject.getString("cityId");
        name=cityObject.getString("name");
        country=cityObject.getString("country");

        JSONArray placeArray=cityObject.getJSONArray("places");

        //Log.e("Olusan City: ",cityId+" "+name+" "+country);
        for (int i=0; i<placeArray.length();i++)
        {
            places.add(placeArray.getString(i));
        }
        return new City(cityId, name, country, places);
    }
    public static ArrayList<City> GetCitiesFromJson(String json) {
        ArrayList<City> cities=new ArrayList<City>();

        String cityId,name,country;
        ArrayList<String> places;
        try
        {
            JSONArray cityList = new JSONArray(json);
            for (int i=0; i<cityList.length();i++)
            {
                JSONObject cityObject=cityList.getJSONObject(i);
                cityId=cityObject.getString("cityId");
                name=cityObject.getString("name");
                country=cityObject.getString("country");
                //places=cityObject.getJSONArray("caption");
                places=null;

                Log.e("City","i: "+name+" - "+country);
                cities.add(new City(cityId, name, country, places));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        return cities;
    }

    public static ArrayList<Place> GetPlacesFromJson(String json) {
        ArrayList<Place> places=new ArrayList<Place>();
        Log.i("Placess",json);
        String placeId,name,cityCountryName,description,longDescription,thumbnail,marker;
        double rating=0,latitude=0,longitude=0;

        ArrayList<String> parentIds, categories, media;
        try
        {
            JSONArray placeList = new JSONArray(json);
            Log.e("Places: ",""+json);
            Log.e("TOPLAM Place SAYISI: ",""+placeList.length());
            for (int i=0; i<placeList.length();i++)
            {
                JSONObject placeObject=placeList.getJSONObject(i);
                placeId=placeObject.getString("placeId");
                name=placeObject.getString("name");
                cityCountryName=placeObject.getString("cityCountryName");
                description=placeObject.getString("description");
                longDescription=placeObject.getString("longDescription");
                thumbnail=placeObject.getString("thumbnail");
                marker=placeObject.getString("marker");



                parentIds=null;
                categories=null;
                media=null;
                places.add(new Place(placeId,rating,latitude,longitude,name,cityCountryName,description,longDescription,thumbnail,marker,parentIds,categories,media));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        return places;
    }

    public static ArrayList<String> GetPlacesJsonListFromJson(String json){
        ArrayList<String> places=new ArrayList<String>();

        try
        {
            JSONArray placeList = new JSONArray(json);
            Log.e("Places: ",""+json);
            Log.e("TOPLAM Place SAYISI: ",""+placeList.length());
            for (int i=0; i<placeList.length();i++)
            {
                JSONObject placeObject=placeList.getJSONObject(i);
                places.add(placeObject.toString());
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        return places;
    }

    public static Place GetPlaceFromJson(String json){
        Place place=null;

        String placeId,name,cityCountryName,description,longDescription,thumbnail,marker;
        double rating,latitude,longitude;
        ArrayList<String> categories=new ArrayList<>(), parentIds=new ArrayList<>(), media=new ArrayList<>();

        JSONObject placeObject= null;
        try {
            placeObject = new JSONObject(json);
            placeId=placeObject.getString("placeId");
            name=placeObject.getString("name");
            cityCountryName=placeObject.getString("cityCountryName");
            description=placeObject.getString("description");
            longDescription=placeObject.getString("longDescription");
            thumbnail=placeObject.getString("thumbnail");
            marker=placeObject.getString("marker");
            rating=placeObject.getDouble("rating");
            latitude=placeObject.getDouble("latitude");
            longitude=placeObject.getDouble("longitude");

            JSONArray categoryList = placeObject.getJSONArray("categories");
            for (int i=0; i<categoryList.length();i++)
            {
                //JSONObject categoryObject=categoryList.getJSONObject(i);
                categories.add(categoryList.get(i).toString());
            }
            JSONArray parentIdList = placeObject.getJSONArray("parentIds");
            for (int i=0; i<parentIdList.length();i++)
            {
                //JSONObject parentIdObject=parentIdList.getJSONObject(i);
                parentIds.add(parentIdList.get(i).toString());
            }
            JSONArray mediaList = placeObject.getJSONArray("media");
            for (int i=0; i<mediaList.length();i++)
            {
                String mediaItem=mediaList.get(i).toString();
               // mediaItem.

                media.add(mediaItem);
            }

            place=new Place(placeId,rating,latitude,longitude,name,cityCountryName,description,longDescription,thumbnail,marker,parentIds,categories,media);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return place;

    }

    public static Country GetCountryFromJson(String json) throws JSONException {
        String countryId,name,capitalCity, flag;
        int population;

        ArrayList<City> cities=new ArrayList<>();

        JSONObject countryObject = new JSONObject(json);

        countryId=countryObject.getString("countryId");
        name=countryObject.getString("name");
        capitalCity=countryObject.getString("capitalCity");
        flag=countryObject.getString("flag");
        population=countryObject.getInt("population");

        JSONArray cityArray=countryObject.getJSONArray("cities");

        for (int i=0; i<cityArray.length();i++)
        {
            cities.add(new City(cityArray.getJSONObject(i).getString("cityId"),cityArray.getJSONObject(i).getString("name")));
        }
        return new Country(countryId, name, capitalCity, population,flag,cities);
    }

    public static String[] GetMediaFromJson(String json) throws JSONException {
        JSONArray mediaArrayObject=new JSONArray(json);
        String[] media=new String[mediaArrayObject.length()];

        for (int i=0; i<mediaArrayObject.length();i++){
            media[i]=mediaArrayObject.getString(i);
        }
        return media;
    }

    public static ArrayList<Country> GetCountriesFromJson(String json){
        ArrayList<Country> countries=new ArrayList<Country>();
        String name, flag;
        try
        {
            JSONArray countryList = new JSONArray(json);

            for (int i=0; i<countryList.length();i++)
            {
                JSONObject countryObject=countryList.getJSONObject(i);
                name=countryObject.getString("name");
                flag=countryObject.getString("flag");
                countries.add(new Country(null,name,null,0, flag, null));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        return countries;
    }
}
