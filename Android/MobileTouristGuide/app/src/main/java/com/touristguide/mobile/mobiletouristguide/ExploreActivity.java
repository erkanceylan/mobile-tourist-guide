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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.touristguide.mobile.mobiletouristguide.Adapters.ExplorerActivityCityListAdapter;
import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Models.City;
import com.touristguide.mobile.mobiletouristguide.Utils.JsonToObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//Keşfet Ekranı
public class ExploreActivity extends AppCompatActivity {
    private ArrayList<City> cityList;
    private ListView cityListView;

    private EditText searchText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        init();
    }

    private void init(){
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        searchText= findViewById(R.id.searchText);
        progressBar=findViewById(R.id.progressBarExploreActivity);

        cityListView=findViewById(R.id.cityListViewExploreActivity);

        searchText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                progressBar.setVisibility(View.VISIBLE);
                Log.i("h",searchText.getText().toString());
                /* istek yapılıyor */
                if(isOnline()){
                    try {
                        ApiRequests.GET("cities/?name=" + searchText.getText().toString(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("h",e.getMessage());
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            fillTheCities(response.body().string());
                                            //responseView.setText(response.body().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
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

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //searchText.getText().clear();
            }
        });

    }

    public static boolean hasPermissions(Context context, String... permissions)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
        {
            for (String permission : permissions)
            {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isOnline(){
        ConnectivityManager cm =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean connection=netInfo != null && netInfo.isConnectedOrConnecting();
        return connection;
    }

    private void fillTheCities(String response){

        cityList=null;

        String responseJson=response;
        cityList = JsonToObject.GetCitiesFromJson(responseJson);
        if(cityList.size()!=0){
            final ExplorerActivityCityListAdapter adapter=new ExplorerActivityCityListAdapter(this,R.layout.explore_activity_city_list_layout,R.id.explore_activity_city_list_layout_lanetTextView, cityList);
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    cityListView.setAdapter(adapter);
                    cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            City city= cityList.get(position);

                            //Toast.makeText(getApplicationContext(), city.getName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ExploreActivity.this, CityActivity.class);
                            intent.putExtra("cityId",city.getCityId());
                            startActivity(intent);

                        }
                    });
                }
            });
        }
        else{
            //TODO: Buraya "hiçbir sonuç bulunamadı" şeysi yapılacak
        }


    }

}
