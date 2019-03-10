package com.prototype.farmCiti.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.fastaccess.datetimepicker.DatePickerFragmentDialog;
import com.fastaccess.datetimepicker.callback.DatePickerCallback;
import com.fastaccess.datetimepicker.callback.TimePickerCallback;
import com.prototype.farmCiti.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Entryformfrag extends Fragment implements DatePickerCallback, TimePickerCallback {
    //TODO: Your views here.
    private View rootView;
    private Spinner spinner;
    private Button pickTime;
    private TextView dateResult;

    public Entryformfrag() {
    }

    public static Entryformfrag newInstance(){
        return new Entryformfrag();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_entryform, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.food_types_spinner);

        pickTime = (Button) rootView.findViewById(R.id.pick_time);
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentDialog.newInstance(true).show(getChildFragmentManager(), "DatePickerFragmentDialog");
            }
        });

        dateResult = (TextView) rootView.findViewById(R.id.datepick_result);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.food_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return rootView;
    }

    public String getDateOnly(long time) {
        return new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(time);
    }

    public static String getDateAndTime(long time) {
        SimpleDateFormat sample = new SimpleDateFormat("dd MMM yyyy, hh:mma", Locale.getDefault());
        return sample.format(new Date(time));
    }

    public static String getTimeOnly(long time) {
        SimpleDateFormat sample = new SimpleDateFormat("hh:mma", Locale.getDefault());
        return sample.format(time);
    }

    @Override public void onDateSet(long date) {
        dateResult.setVisibility(View.VISIBLE);
        dateResult.setText(getDateOnly(date));
    }

    @Override
    public void onTimeSet(long time, long date) {
        dateResult.setVisibility(View.VISIBLE);
        dateResult.setText(String.format("Full Date: %s", getDateAndTime(date)));
    }
}
