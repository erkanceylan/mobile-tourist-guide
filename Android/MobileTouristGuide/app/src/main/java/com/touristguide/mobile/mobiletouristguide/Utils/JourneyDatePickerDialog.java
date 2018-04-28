package com.touristguide.mobile.mobiletouristguide.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.touristguide.mobile.mobiletouristguide.R;

public class JourneyDatePickerDialog {

    private int startYear, startMonth, startDay, endYear, endMonth, endDay;
    private Activity activity;
    private Context context;

    public JourneyDatePickerDialog(Activity act, Context cont){
        this.context=cont;
        this.activity=act;
    }
    /**
     * Displays the start and end date picker dialog
     */
    public void showDatePicker() {
        // Inflate your custom layout containing 2 DatePickers
        LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_date_picker_dialog_layout, null);

        // Define your date pickers
        final DatePicker dpStartDate = (DatePicker) customView.findViewById(R.id.dpStartDate);
        final DatePicker dpEndDate = (DatePicker) customView.findViewById(R.id.dpEndDate);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customView); // Set the view of the dialog to your custom layout
        builder.setTitle("Select start and end date");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startYear = dpStartDate.getYear();
                startMonth = dpStartDate.getMonth();
                startDay = dpStartDate.getDayOfMonth();
                endYear = dpEndDate.getYear();
                endMonth = dpEndDate.getMonth();
                endDay = dpEndDate.getDayOfMonth();
                dialog.dismiss();
            }});

        // Create and show the dialog
        builder.create().show();
    }
}
