package com.touristguide.mobile.mobiletouristguide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.touristguide.mobile.mobiletouristguide.Models.User;
import com.touristguide.mobile.mobiletouristguide.Utils.SharedPreferencesUtils;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView;
    private ImageView photoImageView;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        fillProfileActivityWithUserData();
    }

    private void init(){
        nameTextView=findViewById(R.id.ProfileActivityNameTextView);
        photoImageView=findViewById(R.id.ProfileActivityImageView);

        user= SharedPreferencesUtils.GetUser(getApplicationContext());
    }

    private void fillProfileActivityWithUserData(){
        nameTextView.setText(user.getName());
        //TODO: Foto ayarlanacak.
        Bitmap bitmap = BitmapFactory.decodeFile(user.getPhoto());
        photoImageView.setImageBitmap(bitmap);
    }
}
