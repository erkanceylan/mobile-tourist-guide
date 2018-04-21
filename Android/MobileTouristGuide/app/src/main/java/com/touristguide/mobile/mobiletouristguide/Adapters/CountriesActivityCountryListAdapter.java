package com.touristguide.mobile.mobiletouristguide.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.touristguide.mobile.mobiletouristguide.Models.Country;
import com.touristguide.mobile.mobiletouristguide.R;
import com.touristguide.mobile.mobiletouristguide.Utils.SvgDecoder;
import com.touristguide.mobile.mobiletouristguide.Utils.SvgDrawableTranscoder;
import com.touristguide.mobile.mobiletouristguide.Utils.SvgSoftwareLayerSetter;

import java.io.InputStream;
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
        String mediaUrl=thisCountry.getFlag();
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
        requestBuilder = Glide.with(getContext())
                .using(Glide.buildStreamModelLoader(Uri.class,getContext()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder())).decoder(new SvgDecoder())
                .placeholder(R.drawable.default_place)
                .error(R.drawable.default_place)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        Uri uri = Uri.parse(mediaUrl);
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(imageView);

        return super.getView(position,convertView,parent);
    }
}
