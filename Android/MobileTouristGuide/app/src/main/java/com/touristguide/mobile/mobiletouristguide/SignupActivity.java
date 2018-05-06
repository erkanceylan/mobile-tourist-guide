package com.touristguide.mobile.mobiletouristguide;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Utils.SharedPreferencesUtils;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

public class SignupActivity extends AppCompatActivity {


    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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
            Log.e("filePath: ",filePath);
            userFilePath=filePath;
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("userPhotoPath", filePath).apply();
            cursor.close();

            File imgFile = new  File(filePath);

            if(imgFile.exists()){
                Log.e("exist","true");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                //imageView.setImageBitmap(myBitmap);
                imageView.setImageURI(Uri.fromFile(imgFile));
            }
            else{
                Log.e("exist","false");
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

                if (EasyPermissions.hasPermissions(SignupActivity.this, galleryPermissions)) {
                    startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                } else {
                    EasyPermissions.requestPermissions(SignupActivity.this, "Access for storage",
                            101, galleryPermissions);
                }
            }
        });

        contunieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=userEmail;
                final String password=userPassword;
                final String photoUrl=userFilePath;
                final String name=editText.getText().toString();
                if(isOnline()){
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
                else{
                    InternetConnectionError();
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

    public boolean isOnline(){

        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void InternetConnectionError(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.internet_connection_alert))
                .setTitle(getResources().getString(R.string.internet_connection_alert_title));
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
