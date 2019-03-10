package com.prototype.farmCiti.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prototype.farmCiti.R;

public class Dashboardfrag extends Fragment {
    //TODO: Your views here.
    private View rootView;

    // Views and Ids of Dashboard entries
    private TextView beefEntry;
    private TextView chickenEntry;
    private TextView porkEntry;
    private TextView meatEntry;

    // Counters


    public Dashboardfrag() {
    }

    public static Dashboardfrag newInstance(){
        return new Dashboardfrag();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //grainEntry = rootView.findViewById(R.id.farm_grain);
        beefEntry = rootView.findViewById(R.id.farm_beef);
        chickenEntry = rootView.findViewById(R.id.farm_chicken);
        porkEntry = rootView.findViewById(R.id.farm_pork);
        meatEntry = rootView.findViewById(R.id.farm_other_meat);

        // Default counts
        updateBeef(4);
        updateChicken(2);
        updatePork(3);
        updateMeat(1);
        return rootView;
    }

    public void updateBeef(int count){
        String emojiCow = getResources().getString(R.string.emoji_cow);
        beefEntry.setText(emojiCow);
        for (int i=0; i<count-1; i++){
            beefEntry.append(emojiCow);
        }
    }

    public void updateChicken(int count){
        String emojiChicken = getResources().getString(R.string.emoji_chicken);
        chickenEntry.setText(emojiChicken);
        for (int i=0; i<count-1; i++){
            chickenEntry.append(emojiChicken);
        }
    }

    public void updatePork(int count){
        String emojiPig = getResources().getString(R.string.emoji_pig);
        porkEntry.setText(emojiPig);
        for (int i=0; i<count-1; i++){
            porkEntry.append(emojiPig);
        }
    }

    public void updateMeat(int count){
        String emojiMeat = getResources().getString(R.string.emoji_other_meat);
        meatEntry.setText(emojiMeat);
        for (int i=0; i<count-1; i++){
            meatEntry.append(emojiMeat);
        }
    }
}
