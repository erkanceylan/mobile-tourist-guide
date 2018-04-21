package com.touristguide.mobile.mobiletouristguide.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.touristguide.mobile.mobiletouristguide.Models.Place;
import com.touristguide.mobile.mobiletouristguide.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CityActivityPlaceListAdapter extends ArrayAdapter<Place>
{
    private ArrayList<Place> places;
    Context context;
    int resource;
    int tvResourceId;
    public CityActivityPlaceListAdapter(Context context, int resource, int textViewResourceId, ArrayList<Place> objects) {
        super(context, resource, textViewResourceId, objects);

        Log.e("objects size: ",String.valueOf(objects.size()));
        this.tvResourceId=textViewResourceId;
        this.places=objects;
        this.context=context;
        this.resource=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null) {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.city_activity_place_list_layout,null,true);
        }

        Place thisPlace=getItem(position);
        Log.e("Adapter: ",thisPlace.getName()+" - "+thisPlace.getThumbnail() );
        TextView placeName=convertView.findViewById(R.id.city_activity_city_list_layout_txtCityName);
        TextView placeDescription=convertView.findViewById(R.id.city_activity_city_list_layout_txtCityDescription);
        ImageView imageView=convertView.findViewById(R.id.city_activity_city_list_layout_imageView);

        placeName.setText(thisPlace.getName());

        String description=thisPlace.getDescription();
        if(description!= null && !description.isEmpty() && !description.equals("null"))
            placeDescription.setText(thisPlace.getDescription());
        else
            placeDescription.setText("");

        String thumbnailUrl=thisPlace.getThumbnail();
        if(thumbnailUrl!=null && !thumbnailUrl.isEmpty() && !thumbnailUrl.equals("null")){
          //  try {

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //your codes here

                    Picasso.with(getContext())
                            .load(thumbnailUrl)
                            .resize(600,600)
                            .placeholder(R.drawable.default_place)
                            .error(R.drawable.default_place)
                            .into(imageView);
                }

        }


        return super.getView(position,convertView,parent);
    }
}
