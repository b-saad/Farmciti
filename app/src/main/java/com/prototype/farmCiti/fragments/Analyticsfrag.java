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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
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
    private BarChart mWaterChart;
    private TextView mMeatChartCaptionTextView;
    private TextView mWaterChartCaptionTextView;

    private static float WATER_BEEF = 15.03f; //per gram
    private static float WATER_PORK = 4.8f;
    private static float WATER_CHICKEN = 4.325f;
    private static float WATER_LAMB = 8.763f;

    private static float CO2_BEEF = 0.0346f;
    private static float CO2_PORK = 0.00635f;
    private static float CO2_CHICKEN = 0.00457f;
    private static float CO2_LAMB = 0.0174f;

    List<BarEntry> meatAverageEntries = new ArrayList<BarEntry>();
    List<BarEntry> meatUserEnteries = new ArrayList<BarEntry>();

    String[] values = new String[] {
            "Poultry", "Pigmeat", "Beef", "Mutton/Goat"
    };

    public void addData() {

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
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(4);
        mMeatChart.fitScreen();
        mMeatChart.invalidate(); // refresh
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
        mWaterChart = (BarChart) view.findViewById(R.id.bc_water_usage_chart);
        mMeatChartCaptionTextView = (TextView) view.findViewById(R.id.tv_meat_consumption_caption);
        mWaterChartCaptionTextView = (TextView) view.findViewById(R.id.tv_water_usage_caption);
        addData();
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
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
