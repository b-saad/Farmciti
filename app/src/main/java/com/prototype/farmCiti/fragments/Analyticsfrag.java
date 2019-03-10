package com.prototype.farmCiti.fragments;

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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.prototype.farmCiti.R;

import java.util.ArrayList;
import java.util.List;

public class Analyticsfrag extends Fragment {

    // Views
    private View rootView;
    private BarChart mMeatChart;
    private HorizontalBarChart mWaterChart;
    private TextView mMeatChartCaptionTextView;
    private TextView mWaterChartCaptionTextView;

    // water per gram of meat consumed
    private static float WATER_BEEF = 15.03f; //per gram
    private static float WATER_PORK = 4.8f;
    private static float WATER_CHICKEN = 4.325f;
    private static float WATER_LAMB = 8.763f;

    // C02 per gram of meat consumed
    private static float CO2_BEEF = 0.0346f;
    private static float CO2_PORK = 0.00635f;
    private static float CO2_CHICKEN = 0.00457f;
    private static float CO2_LAMB = 0.0174f;

    String[] values = new String[] {
            "Poultry", "Pigmeat", "Beef", "Mutton/Goat"
    };

    public void addMeatData() {
        List<BarEntry> meatAverageEntries = new ArrayList<BarEntry>();
        List<BarEntry> meatUserEnteries = new ArrayList<BarEntry>();

        meatAverageEntries.add(new BarEntry(0f, 100.5f));
        meatAverageEntries.add(new BarEntry(1f, 62.4f));
        meatAverageEntries.add(new BarEntry(2f, 82.8f));
        meatAverageEntries.add(new BarEntry(3f, 2.655f));

        meatUserEnteries.add(new BarEntry(0, 5));
        meatUserEnteries.add(new BarEntry(1, 6));
        meatUserEnteries.add(new BarEntry(2, 7));
        meatUserEnteries.add(new BarEntry(3, 8));

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        BarDataSet set1 = new BarDataSet(meatAverageEntries, "Average");
        BarDataSet set2 = new BarDataSet(meatUserEnteries, "You");
        set1.setColor(Color.RED);

        BarData data = new BarData(set1, set2);
        data.setBarWidth(barWidth); // set the width of each bar
        mMeatChart.setData(data);
        mMeatChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
        XAxis xAxis = mMeatChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        mMeatChart.getAxisRight().setEnabled(false);
        xAxis.setValueFormatter(new MeatXAxisValueFormatter(values));
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(4);
        mMeatChart.fitScreen();
        mMeatChart.invalidate(); // refresh
    }

    public void addWaterData() {
        List<BarEntry> waterUserEnteries = new ArrayList<BarEntry>();

        waterUserEnteries.add(new BarEntry(0, 4));
        waterUserEnteries.add(new BarEntry(1, 5));
        waterUserEnteries.add(new BarEntry(2, 6));
        waterUserEnteries.add(new BarEntry(3, 7));

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        BarDataSet dataSet = new BarDataSet(waterUserEnteries, "Your Water Usage");
        dataSet.setColor(Color.BLUE);

        BarData data = new BarData(dataSet);
        data.setBarWidth(barWidth); // set the width of each bar
        mWaterChart.setData(data);
        XAxis xAxis = mWaterChart.getXAxis();
//        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new WaterXAxisValueFormatter(values));
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(3.5f);
        mWaterChart.setFitBars(true);
        mWaterChart.fitScreen();
        mWaterChart.invalidate(); // refresh
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
        mMeatChartCaptionTextView = (TextView) view.findViewById(R.id.tv_meat_consumption_caption);
        mWaterChartCaptionTextView = (TextView) view.findViewById(R.id.tv_water_usage_caption);
        addMeatData();
        addWaterData();
    }

    public class MeatXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MeatXAxisValueFormatter(String[] values) {
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


    public class WaterXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public WaterXAxisValueFormatter(String[] values) {
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
