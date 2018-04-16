package com.touristguide.mobile.mobiletouristguide.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.touristguide.mobile.mobiletouristguide.Models.City;
import com.touristguide.mobile.mobiletouristguide.R;

import java.util.ArrayList;

public class ExplorerActivityCityListAdapter extends ArrayAdapter<City>
{
    private ArrayList<City> cities;
    Context context;
    int resource;
    int tvResourceId;
    public ExplorerActivityCityListAdapter(Context context, int resource, int textViewResourceId, ArrayList<City> objects) {
        super(context, resource, textViewResourceId, objects);

        this.tvResourceId=textViewResourceId;
        this.cities=objects;
        this.context=context;
        this.resource=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.explore_activity_city_list_layout,null,true);
        }
        City thisCity=getItem(position);

        TextView cityName=convertView.findViewById(R.id.explore_activity_city_list_layout_txtCityName);
        TextView countryName=convertView.findViewById(R.id.explore_activity_city_list_layout_txtCountryName);


        cityName.setText(thisCity.getName());
        countryName.setText(thisCity.getCountry());

        return super.getView(position,convertView,parent);
    }
}

