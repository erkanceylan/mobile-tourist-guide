package com.touristguide.mobile.mobiletouristguide;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touristguide.mobile.mobiletouristguide.Models.Place;
import com.touristguide.mobile.mobiletouristguide.Utils.JsonToObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PlaceActivity extends AppCompatActivity {

    private TextView txtPlaceName;
    private TextView txtCityCountry;
    private TextView txtLongDescription;
    private TextView txtCoordinates;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView toolbarImageView;
    private LinearLayout categoryLinearLayout;
    private Button showInMapButton;

    private Place thisPlaceObject;
    private String placeJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        placeJson = getIntent().getStringExtra("placeJson");
        init();
        getPlaceObjectFromPlaceJson();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getPlaceObjectFromPlaceJson() {
        thisPlaceObject = JsonToObject.GetPlaceFromJson(placeJson);

        collapsingToolbar.setTitle(thisPlaceObject.getName());
        txtPlaceName.setText(thisPlaceObject.getName());
        txtCityCountry.setText(thisPlaceObject.getCityCountryName());
        String longDescription=thisPlaceObject.getLongDescription();
        if(longDescription!=null && !longDescription.isEmpty() && !longDescription.equals("null"))
            txtLongDescription.setText(longDescription);
        else{
            String description=thisPlaceObject.getDescription();
            if(description!=null && !description.isEmpty() && !description.equals("null"))
                txtLongDescription.setText(description);
            else
                txtLongDescription.setText("");
        }

        txtCoordinates.setText(String.valueOf(thisPlaceObject.getLatitude())+" , "+String.valueOf(thisPlaceObject.getLongitude()));

        showInMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, MapsActivity.class);
                intent.putExtra("latitude",thisPlaceObject.getLatitude());
                intent.putExtra("longitude",thisPlaceObject.getLongitude());
                intent.putExtra("name",thisPlaceObject.getName());
                startActivity(intent);
            }
        });

        for (String category: thisPlaceObject.getCategories()){
            Log.e("category: ",category);
            TextView tv=new TextView(PlaceActivity.this);
            tv.setText(category);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(20);

            categoryLinearLayout.addView(tv);
        }

        if(thisPlaceObject.getMedia().size()>0){

            String mediaJson="[";
            for (int i =0; i<thisPlaceObject.getMedia().size();i++){
                String media = thisPlaceObject.getMedia().get(i);
                if((i+1)==thisPlaceObject.getMedia().size())
                    mediaJson+="\""+media+"\"";
                else
                    mediaJson+="\""+media+"\""+",";
            }
            mediaJson+="]";
            Log.e("sss",mediaJson);
            final String finalMediaJson = mediaJson;
            toolbarImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PlaceActivity.this, ImageSlider.class);
                    intent.putExtra("mediaJson", finalMediaJson);
                    startActivity(intent);
                }
            });
            String mediaUrl=thisPlaceObject.getMedia().get(0);
            if(mediaUrl!=null && !mediaUrl.isEmpty() && !mediaUrl.equals("null")){

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    Picasso.with(getApplicationContext())
                            .load(mediaUrl)
                            .placeholder(R.drawable.image_loading)
                            .error(R.drawable.no_image)
                            .into(toolbarImageView);
                }
            }
        }

    }

    private void init(){
        txtPlaceName=findViewById(R.id.txtPlaceActivityName);
        txtCityCountry=findViewById(R.id.txtPlaceActivityCityCountryName);
        txtLongDescription=findViewById(R.id.txtPlaceLongDescription);
        txtCoordinates=findViewById(R.id.txtPlaceActivityCoordinates);
        collapsingToolbar=findViewById(R.id.PlaceActivityCollapsingToolbar);
        toolbarImageView=findViewById(R.id.PlaceActivityImageView);
        categoryLinearLayout=findViewById(R.id.linearLayoutPlaceActivityCategories);
        showInMapButton=findViewById(R.id.PlaceActivityShowInMapButton);
    }
}
