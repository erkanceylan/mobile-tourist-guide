package com.touristguide.mobile.mobiletouristguide.Adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.chrisbanes.photoview.PhotoView;
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
        PhotoView imageView = view.findViewById(R.id.ImageSliderLayoutImageView);

        VideoView videoView = view.findViewById(R.id.ImageSliderLayoutVideoView);

        String mediaUrl=images[position];
        Log.e(": ",mediaUrl);
        if(mediaUrl!=null && !mediaUrl.isEmpty() && !mediaUrl.equals("null")){
            String extension=mediaUrl.substring(mediaUrl.length()-4);
            Log.e(": ",extension);
            if(extension.equals(".mp4")){
                videoView.setVisibility(View.VISIBLE);
                Uri uri= Uri.parse(mediaUrl);
                videoView.setVideoURI(uri);
                videoView.start();
            }
            else{
                imageView.setVisibility(View.VISIBLE);
                int SDK_INT = Build.VERSION.SDK_INT;
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

        }

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