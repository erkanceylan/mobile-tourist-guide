package com.touristguide.mobile.mobiletouristguide.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.touristguide.mobile.mobiletouristguide.Models.User;

public class SharedPreferencesUtils {
    public static void SaveUser(Context context, String name, String email, String password, String imageUrl){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putString("imageUrl",imageUrl);
        editor.apply();
    }

    public static void SaveUser(Context context, String name, String email, String password){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("password",password);
        editor.apply();
    }

    public static User GetUser(Context context){
        String name,email,password,imageUrl;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        name=preferences.getString("name","DEFAULT");
        email=preferences.getString("email","DEFAULT");
        password=preferences.getString("password","DEFAULT");
        imageUrl=preferences.getString("userPhotoPath","DEFAULT");

        return new User(imageUrl,name,email,password,0,0,null);
    }
}
