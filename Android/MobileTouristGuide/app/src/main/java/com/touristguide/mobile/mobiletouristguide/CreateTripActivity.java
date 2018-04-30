package com.touristguide.mobile.mobiletouristguide;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    private EditText tripNameEditText;
    private TextView placeNameTextView;
    private TextView startingDateTextView;
    private TextView finishingDateTextView;
    private Button createButton;

    private String locationName;
    private String locationId;
    private String tripName;
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
        locationName=getIntent().getStringExtra("locationName");
        locationId=getIntent().getStringExtra("locationId");

        tripNameEditText = findViewById(R.id.CreateTripActivityTripNameEditText);
        placeNameTextView = findViewById(R.id.CreateTripActivityPlaceNameTextView);
        startingDateTextView = findViewById(R.id.CreateTripActivityStartingDateTextView);
        finishingDateTextView = findViewById(R.id.CreateTripActivityFinishingDateTextView);
        createButton=findViewById(R.id.CreateTripActivityButton);

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
        datePickerDialogStart.getDatePicker().setMaxDate(tripStartingDate.getTimeInMillis());

        tripFinishingDate=Calendar.getInstance();
        tripFinishingDate.add(Calendar.DAY_OF_MONTH, 7);
        datePickerDialogFinish=new DatePickerDialog(CreateTripActivity.this, CreateTripActivity.this, tripFinishingDate.get(Calendar.YEAR),tripFinishingDate.get(Calendar.MONTH),tripFinishingDate.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SpannableString content = new SpannableString(year+" - "+(month+1)+" - "+dayOfMonth);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        if(isStartingDate){
            startingDateTextView.setText(content);
        }
        else{
            finishingDateTextView.setText(content);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
