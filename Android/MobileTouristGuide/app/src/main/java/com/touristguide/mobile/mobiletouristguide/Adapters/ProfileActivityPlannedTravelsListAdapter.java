package com.touristguide.mobile.mobiletouristguide.Adapters;

import android.app.Activity;
import android.content.Context;
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
import com.touristguide.mobile.mobiletouristguide.Models.PlannedTravels;
import com.touristguide.mobile.mobiletouristguide.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfileActivityPlannedTravelsListAdapter extends ArrayAdapter<PlannedTravels>
{
    private ArrayList<PlannedTravels> plannedTravels;
    Context context;
    int resource;
    int tvResourceId;
    public ProfileActivityPlannedTravelsListAdapter(Context context, int resource, int textViewResourceId, ArrayList<PlannedTravels> objects) {
        super(context, resource, textViewResourceId, objects);

        Log.e("objects size: ",String.valueOf(objects.size()));
        this.tvResourceId=textViewResourceId;
        this.plannedTravels=objects;
        this.context=context;
        this.resource=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null) {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.profile_activity_planned_travels_list_layout,null,true);
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy");

        PlannedTravels thisPlannedTravel=getItem(position);

        TextView cityName=convertView.findViewById(R.id.profile_activity_planned_travel_list_layout_txtTripCityName);
        TextView startingDate=convertView.findViewById(R.id.profile_activity_planned_travel_list_layout_txtTripStartingDate);
        TextView finishingDate=convertView.findViewById(R.id.profile_activity_planned_travel_list_layout_txtTripFinishingDate);

        cityName.setText(thisPlannedTravel.getLocationName());

        startingDate.setText(dateFormatter.format(thisPlannedTravel.getStartingDate().getTime()));
        finishingDate.setText(dateFormatter.format(thisPlannedTravel.getFinishingDate().getTime()));
        return super.getView(position,convertView,parent);

    }
}
