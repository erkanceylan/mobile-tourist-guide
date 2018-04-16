package com.touristguide.mobile.mobiletouristguide;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashScreen extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private ImageView mImageView;
    private Animation myAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
        mImageView.startAnimation(myAnimation);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void init(){
        mImageView=findViewById(R.id.imageView);
        myAnimation= AnimationUtils.loadAnimation(this,R.anim.alpha);
    }
}
