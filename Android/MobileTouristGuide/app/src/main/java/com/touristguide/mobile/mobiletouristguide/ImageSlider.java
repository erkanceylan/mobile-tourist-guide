package com.touristguide.mobile.mobiletouristguide;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.touristguide.mobile.mobiletouristguide.Adapters.ImageSliderActivityViewPagerAdapter;
import com.touristguide.mobile.mobiletouristguide.Utils.JsonToObject;

import org.json.JSONException;

public class ImageSlider extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;
    private String mediaJson;
    private String[] media;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        init();
        mediaJson = getIntent().getStringExtra("mediaJson");
        Log.e("mediaJson: ",mediaJson);
        try {
            media= JsonToObject.GetMediaFromJson(mediaJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ImageSliderActivityViewPagerAdapter viewPagerAdapter=new ImageSliderActivityViewPagerAdapter(this,media);
        viewPager.setAdapter(viewPagerAdapter);
        dotsCount=viewPagerAdapter.getCount();
        dots=new ImageView[dotsCount];

        for (int i=0;i<dotsCount;i++){
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDotsPanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<dotsCount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void init(){
        viewPager=findViewById(R.id.ImageSliderViewPager);
        sliderDotsPanel=findViewById(R.id.ImageSliderDots);
    }
}
