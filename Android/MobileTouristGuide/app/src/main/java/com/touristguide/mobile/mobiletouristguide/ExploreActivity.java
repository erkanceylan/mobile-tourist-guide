package com.touristguide.mobile.mobiletouristguide;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.touristguide.mobile.mobiletouristguide.Adapters.ExplorerActivityCityListAdapter;
import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Models.City;
import com.touristguide.mobile.mobiletouristguide.Utils.JsonToObject;
import com.touristguide.mobile.mobiletouristguide.Utils.ListUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//Keşfet Ekranı
public class ExploreActivity extends AppCompatActivity {
    private CardView cardViewCities;
    private CardView cardViewCountries;
    private CardView cardViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
    }

    private void init(){
        cardViewCities=findViewById(R.id.ExploreActivityCardViewCities);
        cardViewCountries=findViewById(R.id.ExploreActivityCardViewCountries);
        cardViewProfile=findViewById(R.id.ExploreActivityCardViewProfile);

        cardViewCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ExploreActivity.this,CitiesActivity.class);
                startActivity(intent);
            }
        });
        cardViewCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ExploreActivity.this,CountriesActivity.class);
                startActivity(intent);
            }
        });
        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ExploreActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}
