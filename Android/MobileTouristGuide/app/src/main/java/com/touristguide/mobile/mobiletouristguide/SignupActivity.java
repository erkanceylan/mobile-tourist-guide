package com.touristguide.mobile.mobiletouristguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignupActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText;
    private Button addPhotoButton;
    private Button contunieButton;
    public static final int GET_FROM_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //get email and password from intent
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
                //Update Profile : POST method, URL: /api/users/
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
                if(editText.getText()!=null && !editText.getText().equals("")){
                    contunieButton.setEnabled(true);
                }
                else{
                    contunieButton.setEnabled(false);
                }
            }
        });
    }
}
