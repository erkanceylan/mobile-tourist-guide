package com.touristguide.mobile.mobiletouristguide;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Utils.SharedPreferencesUtils;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText;
    private Button addPhotoButton;
    private Button contunieButton;
    public static final int GET_FROM_GALLERY = 3;
    private String userEmail, userPassword, userFilePath;
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
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            userFilePath=filePath;
            cursor.close();


            Bitmap bitmap = null;//BitmapFactory.decodeFile(filePath);

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
                // TODO : Signup Profile : POST method, URL: /api/users/ fotoğraf hariç
                final String email=userEmail;
                final String password=userPassword;
                final String photoUrl=userFilePath;
                final String name=editText.getText().toString();
                //TODO: Fotograf uygulamaya kaydedilecek ve ordaki linki kaydedilecek
                try {
                    ApiRequests.POST("users/", new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("error:", "burda patladi");
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            SharedPreferencesUtils.SaveUser(getApplicationContext(),name,email,password,photoUrl);
                            Intent intent=new Intent(SignupActivity.this, ExploreActivity.class);
                            startActivity(intent);
                        }
                    }, name, email, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
