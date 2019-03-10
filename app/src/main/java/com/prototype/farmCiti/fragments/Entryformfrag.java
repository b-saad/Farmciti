package com.prototype.farmCiti.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaccess.datetimepicker.DatePickerFragmentDialog;
import com.fastaccess.datetimepicker.DateTimeBuilder;
import com.fastaccess.datetimepicker.callback.DatePickerCallback;
import com.fastaccess.datetimepicker.callback.TimePickerCallback;
import com.google.gson.Gson;
import com.prototype.farmCiti.R;
import com.prototype.farmCiti.datamodel.MealObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Entryformfrag extends Fragment implements DatePickerCallback, TimePickerCallback {
    //TODO: Your views here.
    private View rootView;
    private Spinner spinner;
    private Button pickTime;
    private Button saveData;
    private TextView dateResult;
    private EditText consumptionAmount;
    private long rawDate;
    private SharedPreferences sharedPreferences;

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
        saveData = (Button) rootView.findViewById(R.id.save_record);
        consumptionAmount = (EditText) rootView.findViewById(R.id.input_unit);
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        pickTime = (Button) rootView.findViewById(R.id.pick_time);
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentDialog.newInstance(DateTimeBuilder.get().withTheme(R.style.PickersTheme)).show(getChildFragmentManager(), "DatePickerFragmentDialog");
            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consumptionType = spinner.getSelectedItem().toString().split(" ")[0];
                if(consumptionAmount == null) {
                    Toast.makeText(getContext(),"Failed: data incomplete!", Toast.LENGTH_SHORT).show();
                    return;
                }
                double amountConsumed = Double.parseDouble(consumptionAmount.getText().toString());
                Date date = new Date();
                date.setTime(rawDate);
                if ((!consumptionType.isEmpty() && amountConsumed != 0 && rawDate != 0)) {
                    MealObject mealObject = new MealObject(consumptionType,amountConsumed,date);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(mealObject);
                    String existingData = sharedPreferences.getString("JsonTag","");
                    if(!existingData.isEmpty()) {
                        jsonString = existingData + "###" + jsonString;
                    }
                    sharedPreferences.edit().putString("JsonTag",jsonString).apply();

                    consumptionAmount.getText().clear();
                    rawDate = 0;
                    dateResult.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Data recorded!",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),"Failed: data incomplete!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dateResult = (TextView) rootView.findViewById(R.id.datepick_result);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.food_types_array, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return rootView;
    }

    public String getDateOnly(long time) {
        return new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault()).format(time);
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
        rawDate = date;
        dateResult.setText(getDateOnly(date));
    }

    @Override
    public void onTimeSet(long time, long date) {
        dateResult.setVisibility(View.VISIBLE);
        dateResult.setText(String.format("Full Date: %s", getDateAndTime(date)));
    }
}
