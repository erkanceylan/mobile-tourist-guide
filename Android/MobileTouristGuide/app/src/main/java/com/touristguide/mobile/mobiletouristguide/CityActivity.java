package com.touristguide.mobile.mobiletouristguide;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.touristguide.mobile.mobiletouristguide.Adapters.CityActivityPlaceListAdapter;
import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Models.City;
import com.touristguide.mobile.mobiletouristguide.Models.Place;
import com.touristguide.mobile.mobiletouristguide.Utils.JourneyDatePickerDialog;
import com.touristguide.mobile.mobiletouristguide.Utils.JsonToObject;
import com.touristguide.mobile.mobiletouristguide.Utils.ListUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CityActivity extends AppCompatActivity {

    private LinearLayout mainLinearLayout;
    private TextView txtCityName;
    private TextView txtCountryName;
    private ListView placesListView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;

    private City cityObject;
    private ArrayList<Place> placesList;
    private String cityId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        cityId = getIntent().getStringExtra("cityId");

        init();
        getCityObjectFromCityId();
    }

    private void init(){
        mainLinearLayout=findViewById(R.id.linearLayoutCityActivity);
        txtCityName=findViewById(R.id.txtCityActivityCityName);
        txtCountryName=findViewById(R.id.txtCityActivityCountryName);
        placesListView=findViewById(R.id.listViewCityActivityPlaces);
        progressBar=findViewById(R.id.progressBarCityActivity);
        progressBar.setVisibility(View.VISIBLE);
        fab = findViewById(R.id.CityActivityFloatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JourneyDatePickerDialog dialog=new JourneyDatePickerDialog(CityActivity.this, getApplicationContext());
                dialog.showDatePicker();

                //Snackbar.make(view, "Burada tarih seçme ekranı açılacak", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
    }
    private void getCityObjectFromCityId(){
        /* istek yapılıyor */
        if(isOnline()){
            try {
                ApiRequests.GET("cities/city/" + cityId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("h",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        try {

                            fillTheCityData(response.body().string());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    private void fillTheCityData(final String response) throws JSONException {
        //cityObject nesnesini oluştur ve ata.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    cityObject=JsonToObject.GetCityFromJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                txtCityName.setText(cityObject.getName());
                txtCountryName.setText(cityObject.getCountry());
                txtCountryName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CityActivity.this, CountryActivity.class);
                        intent.putExtra("countryName",cityObject.getCountry());
                        startActivity(intent);
                    }
                });
                mainLinearLayout.setVisibility(View.VISIBLE);
            }
        });


        //Place 'leri getirelim.
        /* istek yapılıyor */
        if(isOnline()){
            try {
                ApiRequests.GET("places/city/" + cityId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                       Log.i("h",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        try {

                            fillThePlaces(response.body().string());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
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
    private void fillThePlaces(String response){
        final ArrayList<String> placesJsonList;
        placesList=null;
        String responseJson=response;
        placesList = JsonToObject.GetPlacesFromJson(responseJson);
        placesJsonList = JsonToObject.GetPlacesJsonListFromJson(responseJson);

        if(placesList.size()!=0){
            final CityActivityPlaceListAdapter adapter=new CityActivityPlaceListAdapter(this,R.layout.city_activity_place_list_layout,R.id.city_activity_place_list_layout_lanetTextView, placesList);
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    placesListView.setAdapter(adapter);
                    //ListUtils.setDynamicHeight(placesListView);
                    placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Intent intent = new Intent(CityActivity.this, PlaceActivity.class);
                            intent.putExtra("placeJson",placesJsonList.get(position));
                            startActivity(intent);
                        }
                    });
                }
            });
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"No place found!",Toast.LENGTH_LONG).show();
                }
            });
        }


    }

}
