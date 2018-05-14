package com.touristguide.mobile.mobiletouristguide;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.touristguide.mobile.mobiletouristguide.HttpRequest.ApiRequests;
import com.touristguide.mobile.mobiletouristguide.Models.PlannedTravels;
import com.touristguide.mobile.mobiletouristguide.Models.User;
import com.touristguide.mobile.mobiletouristguide.Utils.SharedPreferencesUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    private EditText tripNameEditText;
    private TextView placeNameTextView;
    private TextView startingDateTextView;
    private TextView finishingDateTextView;
    private Button createButton;

    private String locationName;
    private String locationId;
    private String tripName;
    private User user;
    private Calendar tripStartingDate;
    private Calendar tripFinishingDate;

    private DatePickerDialog datePickerDialogStart;
    private DatePickerDialog datePickerDialogFinish;

    private boolean isStartingDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        init();
        setStartingAndFinishingDates();
        setDateTextViewsDefaultValue();
        setValues();
    }

    private void init(){
        locationName = getIntent().getStringExtra("locationName");
        locationId = getIntent().getStringExtra("locationId");
        user = SharedPreferencesUtils.GetUser(getApplicationContext());

        tripNameEditText = findViewById(R.id.CreateTripActivityTripNameEditText);
        placeNameTextView = findViewById(R.id.CreateTripActivityPlaceNameTextView);
        startingDateTextView = findViewById(R.id.CreateTripActivityStartingDateTextView);
        finishingDateTextView = findViewById(R.id.CreateTripActivityFinishingDateTextView);
        createButton = findViewById(R.id.CreateTripActivityButton);
        createButton.setEnabled(true);
        startingDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartingDate=true;
                datePickerDialogStart.show();
            }
        });
        finishingDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartingDate=false;
                datePickerDialogFinish.show();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripName=tripNameEditText.getText().toString();
                PlannedTravels plannedTravels=new PlannedTravels(user.getEmail(),tripStartingDate,tripFinishingDate,tripName,locationName,locationId);
                createButton.setEnabled(false);
                if(isOnline()){
                    try {
                        ApiRequests.PUT("users/" + plannedTravels.getUserEmail(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("Succes:","basarisiz");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.e("Succes:","basarili");
                                Intent intent = new Intent(CreateTripActivity.this, ProfileActivity.class);
                                startActivity(intent);
                            }
                        },plannedTravels);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    InternetConnectionError();
                }

            }
        });
    }

    private void setValues(){
        tripNameEditText.setText("Trip To "+ locationName);
        placeNameTextView.setText(locationName);
    }
    private void setDateTextViewsDefaultValue(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy");

        String tripStartingString = dateFormatter.format(tripStartingDate.getTime());

        SpannableString content = new SpannableString(tripStartingString);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        startingDateTextView.setText(content);

        String tripFinishingString = dateFormatter.format(tripFinishingDate.getTime());

        SpannableString content2 = new SpannableString(tripFinishingString);
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        finishingDateTextView.setText(content2);
    }

    private void setStartingAndFinishingDates(){
        tripStartingDate = Calendar.getInstance();
        datePickerDialogStart=new DatePickerDialog(CreateTripActivity.this, CreateTripActivity.this, tripStartingDate.get(Calendar.YEAR),tripStartingDate.get(Calendar.MONTH) ,tripStartingDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialogStart.getDatePicker().setMinDate(tripStartingDate.getTimeInMillis());

        tripFinishingDate=Calendar.getInstance();
        tripFinishingDate.add(Calendar.DAY_OF_MONTH, 7);
        datePickerDialogFinish=new DatePickerDialog(CreateTripActivity.this, CreateTripActivity.this, tripFinishingDate.get(Calendar.YEAR),tripFinishingDate.get(Calendar.MONTH),tripFinishingDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialogFinish.getDatePicker().setMinDate(tripStartingDate.getTimeInMillis());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy");
        Calendar selectingDate=Calendar.getInstance();
        selectingDate.set(year,month,dayOfMonth);

        SpannableString content = new SpannableString(dateFormatter.format(selectingDate.getTime()));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        if(isStartingDate){
            tripStartingDate=(Calendar) selectingDate.clone();
            startingDateTextView.setText(content);
            datePickerDialogFinish.getDatePicker().setMinDate(tripStartingDate.getTimeInMillis());

            if(tripFinishingDate.getTimeInMillis()<tripStartingDate.getTimeInMillis()){
                tripFinishingDate=(Calendar) selectingDate.clone();
                finishingDateTextView.setText(content);
            }
        }
        else{
            tripFinishingDate=(Calendar) selectingDate.clone();
            finishingDateTextView.setText(content);
        }
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
