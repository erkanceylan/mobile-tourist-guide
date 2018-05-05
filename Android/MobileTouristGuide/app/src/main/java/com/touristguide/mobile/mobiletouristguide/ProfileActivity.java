package com.touristguide.mobile.mobiletouristguide;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.touristguide.mobile.mobiletouristguide.Adapters.CountryActivityCityListAdapter;
import com.touristguide.mobile.mobiletouristguide.Adapters.ProfileActivityPlannedTravelsListAdapter;
import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Models.City;
import com.touristguide.mobile.mobiletouristguide.Models.PlannedTravels;
import com.touristguide.mobile.mobiletouristguide.Models.User;
import com.touristguide.mobile.mobiletouristguide.Utils.JsonToObject;
import com.touristguide.mobile.mobiletouristguide.Utils.ListUtils;
import com.touristguide.mobile.mobiletouristguide.Utils.SharedPreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    private ImageView photoImageView;
    private User user;
    private ListView plannedTravelsListView;
    private CollapsingToolbarLayout collapsingToolbar;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private Button exploreCitiesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        fillProfileActivityWithUserData();

        //TODO: Planned travels getirilecek.
        /* istek yapılıyor */
        if(isOnline()){
            try {
                ApiRequests.GET("users/" + user.getEmail(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("h",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        fillPlannedTravels(response.body().string());

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            InternetConnectionError();
        }
        /* istek bitişi */
    }

    public boolean isOnline(){

        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void InternetConnectionError(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.internet_connection_alert))
                .setTitle(getResources().getString(R.string.internet_connection_alert_title));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void init(){
        photoImageView=findViewById(R.id.ProfileActivityImageView);
        plannedTravelsListView=findViewById(R.id.ProfileActivityPlannedTravelsListView);
        collapsingToolbar=findViewById(R.id.ProfileActivityCollapsingToolbar);
        layout1=findViewById(R.id.ProfileActivityLayout1);
        layout2=findViewById(R.id.ProfileActivityLayout2);
        exploreCitiesButton=findViewById(R.id.ProfileActivityExploreCitiesButton);

        exploreCitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CitiesActivity.class);
                startActivity(intent);
            }
        });

        user= SharedPreferencesUtils.GetUser(getApplicationContext());
    }

    private void fillProfileActivityWithUserData(){

        collapsingToolbar.setTitle(user.getName());

        if(!user.getPhoto().equals("DEFAULT"))
        {
            File imgFile = new  File(user.getPhoto());

            if(imgFile.exists()){

                photoImageView.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    private void fillPlannedTravels(String response){
        final ArrayList<PlannedTravels> plannedTravels= JsonToObject.GetPlannedTravelsFromJson(response);

        getOnlyNowTravels(plannedTravels);

        if(plannedTravels.size()>0){

            final ProfileActivityPlannedTravelsListAdapter adapter=new ProfileActivityPlannedTravelsListAdapter(this,R.layout.profile_activity_planned_travels_list_layout,R.id.profile_activity_planned_travel_list_layout_lanetTextView, plannedTravels);
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.INVISIBLE);
                    plannedTravelsListView.setAdapter(adapter);
                    ListUtils.setDynamicHeight(plannedTravelsListView);
                    plannedTravelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            PlannedTravels x= plannedTravels.get(position);
                            if(x.getCityOrCountryId().split(":")[0].equals("city")){
                              Intent intentToCity=new Intent(ProfileActivity.this,CityActivity.class);
                              intentToCity.putExtra("cityId",x.getCityOrCountryId());
                              startActivity(intentToCity);
                            }
                            else{
                                Intent intentToCountry=new Intent(ProfileActivity.this,CountryActivity.class);
                                intentToCountry.putExtra("countryName",x.getLocationName());
                                startActivity(intentToCountry);
                            }
                        }
                    });
                }
            });
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layout1.setVisibility(View.INVISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void getOnlyNowTravels(ArrayList<PlannedTravels> travels){

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY,0);
        PlannedTravels x;
        for (int i=0; i<travels.size();i++){
            x=travels.get(i);
            if(x.getFinishingDate().compareTo(now)==1){
                travels.remove(x);
            }
        }
    }

}
