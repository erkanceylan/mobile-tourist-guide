package com.touristguide.mobile.mobiletouristguide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView nameTextView;
    private ImageView photoImageView;
    private User user;
    private ListView plannedTravelsListView;


    private ArrayList<PlannedTravels> plannedTravels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        fillProfileActivityWithUserData();

        //TODO: Planned travels getirilecek.
        plannedTravels=new ArrayList<PlannedTravels>();
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
            Log.d("*********","THERE IS NO INTERNET CONNECTION");
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(getResources().getString(R.string.internet_connection_alert))
                    .setTitle(getResources().getString(R.string.internet_connection_alert_title));
            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        /* istek bitişi */



    }

    public boolean isOnline(){
        ConnectivityManager cm =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean connection=netInfo != null && netInfo.isConnectedOrConnecting();
        return connection;
    }

    private void init(){
        nameTextView=findViewById(R.id.ProfileActivityNameTextView);
        photoImageView=findViewById(R.id.ProfileActivityImageView);
        plannedTravelsListView=findViewById(R.id.ProfileActivityPlannedTravelsListView);

        user= SharedPreferencesUtils.GetUser(getApplicationContext());
    }

    private void fillProfileActivityWithUserData(){
        nameTextView.setText(user.getName());
        //TODO: Foto ayarlanacak.

        if(!user.getPhoto().equals("DEFAULT"))
        {
            File imgFile = new  File(user.getPhoto());

            if(imgFile.exists()){

                photoImageView.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    private void fillPlannedTravels(String response){
        //TODO: fill planned travels
        final ArrayList<PlannedTravels> plannedTravels= JsonToObject.GetPlannedTravelsFromJson(response);

        if(plannedTravels.size()>0){

            final ProfileActivityPlannedTravelsListAdapter adapter=new ProfileActivityPlannedTravelsListAdapter(this,R.layout.profile_activity_planned_travels_list_layout,R.id.profile_activity_planned_travel_list_layout_lanetTextView, plannedTravels);
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
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
                    Toast.makeText(getApplicationContext(),"No trip plan found!",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
