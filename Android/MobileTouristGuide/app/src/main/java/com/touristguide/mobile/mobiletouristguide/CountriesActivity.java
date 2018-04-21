package com.touristguide.mobile.mobiletouristguide;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.touristguide.mobile.mobiletouristguide.Adapters.CityActivityPlaceListAdapter;
import com.touristguide.mobile.mobiletouristguide.Adapters.CountriesActivityCountryListAdapter;
import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Models.Country;
import com.touristguide.mobile.mobiletouristguide.Models.Place;
import com.touristguide.mobile.mobiletouristguide.Utils.JsonToObject;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CountriesActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ListView countriesListView;
    private ProgressBar progressBar;
    private ArrayList<Country> countries;
    private ArrayList<Country> searchingCountries=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        getCountries();

    }
    private void init(){
        countriesListView=findViewById(R.id.listViewCountriesActivityCountries);
        progressBar=findViewById(R.id.progressBarCountriesActivity);
        searchEditText=findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchingText=searchEditText.getText().toString();
                if(searchingText!="" && searchingText!=null && !searchingText.isEmpty()){
                    //Array içinde arama yap, searching array a at onu göster.
                    Search(searchingText);
                }
                else{
                    searchingCountries=copyCountryList(countries);
                }
                CountriesActivityCountryListAdapter adapter=new CountriesActivityCountryListAdapter(getApplicationContext(),R.layout.countries_activity_country_layout,R.id.countries_activity_country_layout_lanetTextView, searchingCountries);

                countriesListView.setAdapter(adapter);
                countriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Country country= searchingCountries.get(position);
                        Log.e("countries: ",country.getName());
                        Intent intent = new Intent(CountriesActivity.this, CountryActivity.class);
                        intent.putExtra("countryName",country.getName());
                        startActivity(intent);
                    }
                });
            }
        });
    }
    public boolean isOnline(){
        ConnectivityManager cm =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean connection=netInfo != null && netInfo.isConnectedOrConnecting();
        return connection;
    }
    private void getCountries(){
        /* istek yapılıyor */
        if(isOnline()){
            try {
                ApiRequests.GET("countries/", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("h",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        fillTheCountriesData(response.body().string());
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
    }

    private void Search(String searchingString){
        if(searchingCountries.size()>0)
            searchingCountries.removeAll(searchingCountries);
        for (Country x:countries){
            if(x.getName().toLowerCase().indexOf(searchingString.toLowerCase())>-1){
                searchingCountries.add(x);
            }
        }
    }

    private void fillTheCountriesData(String responseJson){

        countries=null;
        countries = JsonToObject.GetCountriesFromJson(responseJson);

        Log.i("## Country list size:",String.valueOf(countries.size()));
        if(countries.size()!=0){
            final CountriesActivityCountryListAdapter adapter=new CountriesActivityCountryListAdapter(this,R.layout.countries_activity_country_layout,R.id.countries_activity_country_layout_lanetTextView, countries);
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    progressBar.setVisibility(View.INVISIBLE);

                    countriesListView.setAdapter(adapter);
                    countriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Country country= countries.get(position);
                            Log.e("countries: ",country.getName());
                            Intent intent = new Intent(CountriesActivity.this, CountryActivity.class);
                            intent.putExtra("countryName",country.getName());
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

    private ArrayList<Country> copyCountryList(ArrayList<Country> countryList){
        ArrayList<Country> newCountryList=new ArrayList<>();
        for (Country x:countryList){
            newCountryList.add(x);
        }
        return newCountryList;
    }

}
