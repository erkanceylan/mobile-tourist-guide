package com.touristguide.mobile.mobiletouristguide;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.touristguide.mobile.mobiletouristguide.Adapters.CountryActivityCityListAdapter;
import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Models.City;
import com.touristguide.mobile.mobiletouristguide.Models.Country;
import com.touristguide.mobile.mobiletouristguide.Utils.JsonToObject;
import com.touristguide.mobile.mobiletouristguide.Utils.ListUtils;
import com.touristguide.mobile.mobiletouristguide.Utils.SvgDecoder;
import com.touristguide.mobile.mobiletouristguide.Utils.SvgDrawableTranscoder;
import com.touristguide.mobile.mobiletouristguide.Utils.SvgSoftwareLayerSetter;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CountryActivity extends AppCompatActivity {

    private TextView txtCountryName;
    private TextView txtCapitalCity;
    private TextView txtPopulation;
    private ListView listViewCities;

    private Country thisCountryObject;
    private ArrayList<City> cityList;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView toolbarImageView;
    private FloatingActionButton fab;

    private String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        countryName = getIntent().getStringExtra("countryName");
        init();
        getCountryObjectFromCountryName();
    }
    private void init(){
        txtCountryName=findViewById(R.id.txtCountryActivityCountryName);
        txtCapitalCity=findViewById(R.id.txtCountryActivityCapitalCity);
        txtPopulation=findViewById(R.id.txtCountryActivityPopulation);
        listViewCities=findViewById(R.id.listViewCountryActivityCities);

        collapsingToolbar=findViewById(R.id.CountryActivityCollapsingToolbar);
        toolbarImageView=findViewById(R.id.CountryActivityImageView);

        fab = findViewById(R.id.CountryActivityFloatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CountryActivity.this, CreateTripActivity.class);
                intent.putExtra("locationName",thisCountryObject.getName());
                intent.putExtra("locationId",thisCountryObject.getCountryId());
                startActivity(intent);

                //Snackbar.make(view, "Burada tarih seçme ekranı açılacak", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

    }

    private void getCountryObjectFromCountryName(){
        /* istek yapılıyor */
        if(isOnline()){
            try {
                ApiRequests.GET("countries/" + countryName, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("h",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        fillTheCountryData(response.body().string());

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

    private void fillTheCountryData(final String response){
        //thisCountryObject nesnesini oluştur ve ata.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    thisCountryObject =JsonToObject.GetCountryFromJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                txtCountryName.setText(thisCountryObject.getName());
                txtCapitalCity.setText(thisCountryObject.getCapitalCity());
                txtCapitalCity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String cityId=getCityIdOfCapitalCity();
                        Intent intent = new Intent(CountryActivity.this, CityActivity.class);
                        intent.putExtra("cityId",cityId);
                        startActivity(intent);
                    }
                });
                txtPopulation.setText(getPopulationTextWithCommas(String.valueOf(thisCountryObject.getPopulation())));
                collapsingToolbar.setTitle(thisCountryObject.getName());
                Log.e("City Size: ", String.valueOf(thisCountryObject.getCities().size()));

                String mediaUrl=thisCountryObject.getFlag();
                Log.e("media flag: ",mediaUrl);
                if(mediaUrl!=null && !mediaUrl.isEmpty() && !mediaUrl.equals("null")){

                    int SDK_INT = android.os.Build.VERSION.SDK_INT;
                    if (SDK_INT > 8)
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);


                        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
                        requestBuilder = Glide.with(getApplicationContext())
                                .using(Glide.buildStreamModelLoader(Uri.class,getApplicationContext()), InputStream.class)
                                .from(Uri.class)
                                .as(SVG.class)
                                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                                .sourceEncoder(new StreamEncoder())
                                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder())).decoder(new SvgDecoder())
                            .placeholder(R.drawable.image_loading)
                            .error(R.drawable.no_image)
                            .animate(android.R.anim.fade_in)
                            .listener(new SvgSoftwareLayerSetter<Uri>());

                        Uri uri = Uri.parse(mediaUrl);
                        requestBuilder
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .load(uri)
                                .into(toolbarImageView);

                    }
                }
                fillTheCities();
            }
        });
    }
    private String getCityIdOfCapitalCity(){
        for (City city:thisCountryObject.getCities()){
            if(city.getName().equals(thisCountryObject.getCapitalCity())){
                return city.getCityId();
            }
        }
        return null;
    }
    private String getPopulationTextWithCommas(String text){
        String populationText="";
        char[] charArray = text.toCharArray();
        final int size=charArray.length;
        Log.e("size: ",String.valueOf(size));
        if(size%3==0){
            for (int i=0;i<size;i++){
                if(i%3==0 && i!=0){
                    populationText+=",";
                }
                populationText+=charArray[i];
            }
        }
        else if(size%3==1){
            for (int i=0;i<size;i++){
                if(i%3==1 && i!=0){
                    populationText+=",";
                }
                populationText+=charArray[i];
            }
        }
        else if(size%3==2){
            for (int i=0;i<size;i++){
                if(i%3==2 && i!=0){
                    populationText+=",";
                }
                populationText+=charArray[i];
            }
        }
        return populationText;
    }

    private void fillTheCities(){
        cityList=null;
        cityList=thisCountryObject.getCities();
        Log.e("City List: ",String.valueOf(cityList.size()));
        if(cityList.size()>0){

            final CountryActivityCityListAdapter adapter=new CountryActivityCityListAdapter(this,R.layout.country_activity_city_list_layout,R.id.country_activity_city_list_layout_lanetTextView , cityList);
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    listViewCities.setAdapter(adapter);
                    ListUtils.setDynamicHeight(listViewCities);
                    listViewCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            City city= cityList.get(position);

                            Intent intent = new Intent(CountryActivity.this, CityActivity.class);
                            intent.putExtra("cityId",city.getCityId());
                            startActivity(intent);
                        }
                    });
                }
            });
        }
        else{

            Toast.makeText(getApplicationContext(),"No city found!",Toast.LENGTH_LONG).show();
        }


    }
}
