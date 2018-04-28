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
        import android.view.WindowManager;
        import android.widget.AdapterView;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.Toast;


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
public class CitiesActivity extends AppCompatActivity  {
    private ArrayList<City> cityList;
    private ArrayList<City> topCitiesList;
    private ArrayList<City> edistorsPickList;

    private ListView searchingCityListView;
    private ListView topCityListView;
    private ListView editorsPickCityListView;

    private EditText searchText;
    private ProgressBar progressBar;

    private RelativeLayout layout1;
    private RelativeLayout layout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        getSuggestionCities();
    }

    private void init(){
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        searchText= findViewById(R.id.searchText);
        progressBar=findViewById(R.id.progressBarCitiesActivity);

        layout1 = findViewById(R.id.CitiesActivityLayout1);
        layout2 = findViewById(R.id.CitiesActivityLayout2);

        topCityListView = findViewById(R.id.CitiesActivityTopCitiesListView);
        editorsPickCityListView = findViewById(R.id.CitiesActivityEditorsPickCitiesListView);
        searchingCityListView =findViewById(R.id.CitiesActivityCityListView);

        searchText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                SetVisibity(progressBar,true);

                String searchingText=searchText.getText().toString();
                if(searchingText!="" && searchingText!=null && !searchingText.isEmpty()){
                    final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                    int pixels = (int) (0 * scale + 0.5f);
                    layout1.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                    layout2.getLayoutParams().height=pixels;

                    /* istek yapılıyor */
                    if(isOnline()){
                        try {
                            ApiRequests.GET("cities/?name=" + searchingText, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.i("h",e.getMessage());
                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {

                                    try {
                                        fillTheCities(response.body().string(),searchingCityListView);
                                        //responseView.setText(response.body().string());
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
                        InternetConnectionError();
                    }
                }
                else{
                    //Öneri ekranını göster.

                    getSuggestionCities();
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
    private void InternetConnectionError(){
        Log.d("*********","THERE IS NO INTERNET CONNECTION");
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(getResources().getString(R.string.internet_connection_alert))
                .setTitle(getResources().getString(R.string.internet_connection_alert_title));
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void getSuggestionCities(){
        SetVisibity(progressBar,true);

        if(isOnline()){
            try {
                ApiRequests.GET("cities/", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("h",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        try {
                            final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                            int pixels = (int) (0 * scale + 0.5f);
                            layout1.getLayoutParams().height = pixels;
                            layout2.getLayoutParams().height=RelativeLayout.LayoutParams.MATCH_PARENT;
                            fillTheTopCities(response.body().string(),topCityListView);

                            //responseView.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                ApiRequests.GET("cities/editorspick", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("h",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        try {
                            //progressBar.setVisibility(View.INVISIBLE);
                            fillTheEditorsPickCities(response.body().string(),editorsPickCityListView);
                            //responseView.setText(response.body().string());
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
            InternetConnectionError();
        }

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
    private void SetVisibity(View view, boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
            view.getLayoutParams().height= RelativeLayout.LayoutParams.WRAP_CONTENT;
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
            int pixels;
            pixels = (int) (0 * scale + 0.5f);
            view.getLayoutParams().height=pixels;
        }
    }
    public boolean isOnline(){
        ConnectivityManager cm =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean connection=netInfo != null && netInfo.isConnectedOrConnecting();
        return connection;
    }

    private void fillTheCities(String response, final ListView cityListView){

        cityList=null;

        String responseJson=response;
        cityList = JsonToObject.GetCitiesFromJson(responseJson);
        if(cityList.size()!=0){
            final ExplorerActivityCityListAdapter adapter=new ExplorerActivityCityListAdapter(this,R.layout.cities_activity_top_cities_layout,R.id.explore_activity_top_cities_layout_lanetTextView, cityList);
            runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    SetVisibity(progressBar,false);
                    cityListView.setAdapter(adapter);
                    ListUtils.setDynamicHeight(cityListView);
                    cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            City city= cityList.get(position);
                            Intent intent = new Intent(CitiesActivity.this, CityActivity.class);
                            intent.putExtra("cityId",city.getCityId());
                            startActivity(intent);
                        }
                    });
                }
            });
        }
        else{
            SetVisibity(progressBar,false);
            Toast.makeText(getApplicationContext(),"No cities found !",Toast.LENGTH_LONG).show();
        }
    }


    private void fillTheTopCities(String response, final ListView cityListView){

        topCitiesList=null;

        String responseJson=response;
        topCitiesList = JsonToObject.GetCitiesFromJson(responseJson);
        if(topCitiesList.size()!=0){
            final ExplorerActivityCityListAdapter adapter=new ExplorerActivityCityListAdapter(this,R.layout.cities_activity_top_cities_layout,R.id.explore_activity_top_cities_layout_lanetTextView, topCitiesList);
            runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    SetVisibity(progressBar,false);
                    cityListView.setAdapter(adapter);
                    ListUtils.setDynamicHeight(cityListView);
                    cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            City city= topCitiesList.get(position);
                            Intent intent = new Intent(CitiesActivity.this, CityActivity.class);
                            intent.putExtra("cityId",city.getCityId());
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }

    private void fillTheEditorsPickCities(String response, final ListView cityListView){

        edistorsPickList=null;

        String responseJson=response;
        edistorsPickList = JsonToObject.GetCitiesFromJson(responseJson);
        if(edistorsPickList.size()!=0){
            final ExplorerActivityCityListAdapter adapter=new ExplorerActivityCityListAdapter(this,R.layout.cities_activity_editors_pick_layout,R.id.explore_activity_editors_pick_layout_lanetTextView, edistorsPickList);
            runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    cityListView.setAdapter(adapter);
                    ListUtils.setDynamicHeight(cityListView);
                    cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            City city= edistorsPickList.get(position);
                            Intent intent = new Intent(CitiesActivity.this, CityActivity.class);
                            intent.putExtra("cityId",city.getCityId());
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }


}
