package com.touristguide.mobile.mobiletouristguide.Adapters;

import android.content.Context;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;
import com.touristguide.mobile.mobiletouristguide.R;

public class ImageSliderActivityViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String [] images;// {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};

    public ImageSliderActivityViewPagerAdapter(Context context, String[] imageUrls) {
        this.context = context;
        Log.e("image Urls",String.valueOf(imageUrls.length));
        this.images=new String[imageUrls.length];
        for(int i=0; i<imageUrls.length;i++){
            this.images[i]=imageUrls[i];
        }
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_slider_image_view_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.ImageSliderLayoutImageView);
        //imageView.setImageResource(images[position]);

        String mediaUrl=images[position];
        if(mediaUrl!=null && !mediaUrl.isEmpty() && !mediaUrl.equals("null")){

            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);

                PhotoViewAttacher yourAttacher = new PhotoViewAttacher(imageView);

                Picasso.with(this.context)
                        .load(mediaUrl)
                        .placeholder(R.drawable.image_loading)
                        .error(R.drawable.no_image)
                        .into(imageView);
            }
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position == 0){
                    Toast.makeText(context, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
                } else if(position == 1){
                    Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}