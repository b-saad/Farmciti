package com.prototype.farmCiti.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.prototype.farmCiti.R;
import com.prototype.farmCiti.datamodel.MealObject;

import java.util.ArrayList;
import java.util.List;

public class Analyticsfrag extends Fragment {

    // Views
    private View rootView;
    private BarChart mMeatChart;
    private HorizontalBarChart mWaterChart;
    private BarChart mFeedChart;
    private HorizontalBarChart mCo2Chart;
    private SharedPreferences sharedPreferences;

    // Avg meat consumption per capita per week in grams
    private static float MEAT_CHICKEN = 703.5f;
    private static float MEAT_PORK = 436.8f;
    private static float MEAT_BEEF = 579.6f;
    private static float MEAT_OTHER = 18.585f;

    // litres of water per gram of meat consumed
    private static float WATER_BEEF = 15.03f;
    private static float WATER_PORK = 4.8f;
    private static float WATER_CHICKEN = 4.325f;
    private static float WATER_OTHER = 8.763f;

    // kg of C02 per gram of meat consumed
    private static float CO2_BEEF = 0.0346f;
    private static float CO2_PORK = 0.00635f;
    private static float CO2_CHICKEN = 0.00457f;
    private static float CO2_OTHER = 0.0174f;

    // gram of feed per gram of meat consumed
    private static float FEED_BEEF = 25f;
    private static float FEED_PORK = 6.4f;
    private static float FEED_CHICKEN = 3.3f;
    private static float FEED_OTHER = 15f;

    // weekly meat consumption average user
    private static float USER_CHICKEN = 0f;
    private static float USER_PORK = 0f;
    private static float USER_BEEF = 0f;
    private static float USER_OTHER = 0f;

    String[] values = new String[] {
            "Poultry", "Pigmeat", "Beef", "Other"
    };

    public ArrayList<MealObject> meals = new ArrayList<>();


    public void addMeatData() {
        List<BarEntry> meatAverageEneries = new ArrayList<BarEntry>();
        List<BarEntry> meatUserEnteries = new ArrayList<BarEntry>();

        meatAverageEneries.add(new BarEntry(0f, MEAT_CHICKEN));
        meatAverageEneries.add(new BarEntry(1f, MEAT_PORK));
        meatAverageEneries.add(new BarEntry(2f, MEAT_BEEF));
        meatAverageEneries.add(new BarEntry(3f, MEAT_OTHER));

        meatUserEnteries.add(new BarEntry(0, USER_CHICKEN));
        meatUserEnteries.add(new BarEntry(1, USER_PORK));
        meatUserEnteries.add(new BarEntry(2, USER_BEEF));
        meatUserEnteries.add(new BarEntry(3, USER_OTHER));

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        BarDataSet set1 = new BarDataSet(meatAverageEneries, "Average");
        BarDataSet set2 = new BarDataSet(meatUserEnteries, "You");
        set1.setColor(Color.RED);

        BarData data = new BarData(set1, set2);
        data.setBarWidth(barWidth); // set the width of each bar
        mMeatChart.setData(data);
        mMeatChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
        Description blankDescription = new Description();
        blankDescription.setText("");
        mMeatChart.setDescription(blankDescription);
        XAxis xAxis = mMeatChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        mMeatChart.getAxisRight().setEnabled(false);
        xAxis.setValueFormatter(new XAxisValueFormatter(values));
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(4);

        mMeatChart.fitScreen();
        mMeatChart.invalidate(); // refresh
    }

    public void addWaterData() {
        List<BarEntry> waterUserEnteries = new ArrayList<BarEntry>();

        waterUserEnteries.add(new BarEntry(0, (USER_CHICKEN * WATER_CHICKEN)));
        waterUserEnteries.add(new BarEntry(1, (USER_PORK * WATER_PORK)));
        waterUserEnteries.add(new BarEntry(2, (USER_BEEF * WATER_BEEF)));
        waterUserEnteries.add(new BarEntry(3, (USER_OTHER * WATER_OTHER)));

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        BarDataSet dataSet = new BarDataSet(waterUserEnteries, "Water used in food production");
        dataSet.setColor(Color.BLUE);

        BarData data = new BarData(dataSet);
        data.setBarWidth(barWidth); // set the width of each bar
        mWaterChart.setData(data);
        Description blankDescription = new Description();
        blankDescription.setText("");
        mWaterChart.setDescription(blankDescription);
        XAxis xAxis = mWaterChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new XAxisValueFormatter(values));
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(3.5f);
        mWaterChart.fitScreen();
        mWaterChart.invalidate(); // refresh
    }

    public void addFeedData() {
        List<BarEntry> feedUserEnteries = new ArrayList<BarEntry>();

        feedUserEnteries.add(new BarEntry(0, (USER_CHICKEN * FEED_CHICKEN)));
        feedUserEnteries.add(new BarEntry(1, (USER_PORK * FEED_PORK)));
        feedUserEnteries.add(new BarEntry(2, (USER_BEEF * FEED_BEEF)));
        feedUserEnteries.add(new BarEntry(3, (USER_OTHER * FEED_OTHER)));

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        BarDataSet dataSet = new BarDataSet(feedUserEnteries, "Feed used in food production");
        dataSet.setColor(Color.MAGENTA);

        BarData data = new BarData(dataSet);
        data.setBarWidth(barWidth); // set the width of each bar
        mFeedChart.setData(data);
        mFeedChart.getAxisRight().setEnabled(false);
        Description blankDescription = new Description();
        blankDescription.setText("");
        mFeedChart.setDescription(blankDescription);
        XAxis xAxis = mFeedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new XAxisValueFormatter(values));
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(3.5f);
        mFeedChart.setFitBars(true);
        mFeedChart.fitScreen();
        mFeedChart.invalidate(); // refresh
    }

    public void addCarbonData() {
        List<BarEntry> carbonUserEnteries = new ArrayList<BarEntry>();

        carbonUserEnteries.add(new BarEntry(0, (USER_CHICKEN * CO2_CHICKEN)));
        carbonUserEnteries.add(new BarEntry(1, (USER_PORK * CO2_PORK)));
        carbonUserEnteries.add(new BarEntry(2, (USER_BEEF * CO2_BEEF)));
        carbonUserEnteries.add(new BarEntry(3, (USER_OTHER * CO2_OTHER)));

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        BarDataSet dataSet = new BarDataSet(carbonUserEnteries, "Carbon emissions from your food");
        dataSet.setColor(Color.rgb(244, 134, 66));

        BarData data = new BarData(dataSet);
        data.setBarWidth(barWidth); // set the width of each bar
        mCo2Chart.setData(data);
        Description blankDescription = new Description();
        blankDescription.setText("");
        mCo2Chart.setDescription(blankDescription);
        XAxis xAxis = mCo2Chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new XAxisValueFormatter(values));
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(3.5f);
        mCo2Chart.setFitBars(true);
        mCo2Chart.fitScreen();
        mCo2Chart.invalidate(); // refresh
    }

    public Analyticsfrag() {

    }

    public static Analyticsfrag newInstance(){
        return new Analyticsfrag();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analytics, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMeatChart = (BarChart) view.findViewById(R.id.bc_meat_consumption_chart);
        mWaterChart = (HorizontalBarChart) view.findViewById(R.id.bc_water_usage_chart);
        mFeedChart = (BarChart) view.findViewById(R.id.bc_feed_usage_chart);
        mCo2Chart = (HorizontalBarChart) view.findViewById(R.id.bc_c02_emission_char);
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String rawData = sharedPreferences.getString("JsonTag","");
        String[] dataList = rawData.split("###");
        for(String data: dataList) { meals.add(gson.fromJson(data,MealObject.class));}

        updateCount();
        addMeatData();
        addWaterData();
        addFeedData();
        addCarbonData();
    }

    private void updateCount() {
        if (meals == null) { return; }
        for (MealObject meal : meals) {
            if (meal == null) { return; }
            switch (meal.getType()) {
                case "Beef":
                    USER_BEEF += meal.getAmount();
                    break;
                case "Chicken":
                    USER_CHICKEN += meal.getAmount();
                    break;
                case "Pork":
                    USER_PORK += meal.getAmount();
                    break;
                case "Other":
                    USER_OTHER += meal.getAmount();
                    break;
            }
        }
    }

    public class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public XAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            if ( value <= 0 ) {
                return mValues[0];
            } else if ( value == 1) {
                return mValues[1];
            } else if ( value == 2) {
                return mValues[2];
            } else if ( value == 3) {
                return mValues[3];
            }
            return "";
        }

    }
}
