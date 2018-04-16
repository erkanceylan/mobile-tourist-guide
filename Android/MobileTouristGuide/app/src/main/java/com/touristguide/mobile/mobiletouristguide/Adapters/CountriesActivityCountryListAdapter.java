package com.touristguide.mobile.mobiletouristguide.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVGImageView;
import com.squareup.picasso.Picasso;
import com.touristguide.mobile.mobiletouristguide.Models.Country;
import com.touristguide.mobile.mobiletouristguide.R;

import java.net.URL;
import java.util.ArrayList;

public class CountriesActivityCountryListAdapter extends ArrayAdapter<Country>
{
    private ArrayList<Country> countries;
    Context context;
    int resource;
    int tvResourceId;
    public CountriesActivityCountryListAdapter(Context context, int resource, int textViewResourceId, ArrayList<Country> objects) {
            super(context, resource, textViewResourceId, objects);

            Log.e("objects size: ",String.valueOf(objects.size()));
            this.tvResourceId=textViewResourceId;
            this.countries=objects;
            this.context=context;
            this.resource=resource;
            }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null) {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.countries_activity_country_layout,null,true);
        }

        Country thisCountry=getItem(position);
        Log.e("Adapter: ",thisCountry.getName()+" - "+thisCountry.getFlag() );
        TextView countryName=convertView.findViewById(R.id.countries_activity_country_layout_txtCountryName);
        ImageView imageView=convertView.findViewById(R.id.countries_activity_country_layout_imageView);

        countryName.setText(thisCountry.getName());


        URL url;
        Bitmap bmp=null;
        String thumbnailUrl=thisCountry.getFlag();
        if(thumbnailUrl!=null && !thumbnailUrl.isEmpty() && !thumbnailUrl.equals("null")){
            //  try {

            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here
/*
                Picasso.with(getContext())
                .load(thumbnailUrl)
                .resize(600,600)
                .placeholder(R.drawable.default_place)
                .error(R.drawable.default_place)
                .into(imageView);
                // url = new URL(thumbnailUrl);
                // bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                // imageView.setImageBitmap(bmp);
  */
            }
    /*
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
        }


        return super.getView(position,convertView,parent);
    }
}
