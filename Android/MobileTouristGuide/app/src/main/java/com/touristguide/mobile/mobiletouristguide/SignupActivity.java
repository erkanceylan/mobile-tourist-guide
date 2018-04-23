package com.touristguide.mobile.mobiletouristguide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText;
    private Button addPhotoButton;
    private Button contunieButton;
    public static final int GET_FROM_GALLERY = 3;
    private String userEmail, userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //get email and password from intent
        userEmail=getIntent().getStringExtra("email");
        userPassword=getIntent().getStringExtra("password");

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void init(){
        imageView=findViewById(R.id.SignupActivityImageView);
        editText=findViewById(R.id.SignupActivityEditText);
        addPhotoButton=findViewById(R.id.SignupActivityAddPhotoButton);
        contunieButton=findViewById(R.id.SignupActivityContunieButton);

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        contunieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : Update Profile : POST method, URL: /api/users/ fotoğraf hariç
                //TODO: User bilgileri SharedPreferences a kaydedilecek.
                Log.e("USER: ",userEmail+" "+userPassword);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String nameText=editText.getText().toString();
                if(nameText!="" && nameText!=null && !nameText.isEmpty()){
                    contunieButton.setEnabled(true);
                }
                else{
                    contunieButton.setEnabled(false);
                }
            }
        });
    }
}
