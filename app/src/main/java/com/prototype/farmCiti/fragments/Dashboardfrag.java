package com.prototype.farmCiti.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.prototype.farmCiti.R;
import com.prototype.farmCiti.datamodel.MealObject;

import java.util.ArrayList;

public class Dashboardfrag extends Fragment {
    //TODO: Your views here.
    private View rootView;

    // Views and Ids of Dashboard entries
    private TextView beefEntry;
    private TextView chickenEntry;
    private TextView porkEntry;
    private TextView meatEntry;
    private SharedPreferences sharedPreferences;

    // defaults
    private static int BEEF_DEFAULT = 7;
    private static int CHICKEN_DEFAULT = 7;
    private static int PORK_DEFAULT = 7;
    private static int OTHER_MEAT_DEFAULT = 7;

    // User's progress (# of cows the user has)
    private int BEEF_USER = 0;
    private int CHICKEN_USER = 0;
    private int PORK_USER = 0;
    private int OTHER_MEAT_USER = 0;

    // daily meat usage average
    private static float MEAT_CHICKEN = 100.5f;
    private static float MEAT_PORK = 62.4f;
    private static float MEAT_BEEF = 82.8f;
    private static float MEAT_OTHER = 2.655f;


    public ArrayList<MealObject> meals = new ArrayList<>();


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
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String rawData = sharedPreferences.getString("JsonTag","");
        String[] dataList = rawData.split("###");
        for(String data: dataList) {
            meals.add(gson.fromJson(data,MealObject.class));
        };
        updateCount();
        updateBeef(BEEF_DEFAULT-BEEF_USER);
        updateChicken(CHICKEN_DEFAULT-CHICKEN_USER);
        updatePork(PORK_DEFAULT-PORK_USER);
        updateMeat(OTHER_MEAT_DEFAULT-OTHER_MEAT_USER);
        return rootView;
    }

    public void updateBeef(int count){
        String emojiCow = getResources().getString(R.string.emoji_cow);
        beefEntry.setText("");
        for (int i=0; i<count; i++){
            beefEntry.append(emojiCow);
        }
    }

    public void updateChicken(int count){
        String emojiChicken = getResources().getString(R.string.emoji_chicken);
        chickenEntry.setText("");
        for (int i=0; i<count; i++){
            chickenEntry.append(emojiChicken);
        }
    }

    public void updatePork(int count){
        String emojiPig = getResources().getString(R.string.emoji_pig);
        porkEntry.setText("");
        for (int i=0; i<count; i++){
            porkEntry.append(emojiPig);
        }
    }

    public void updateMeat(int count){
        String emojiMeat = getResources().getString(R.string.emoji_other_meat);
        meatEntry.setText("");
        for (int i=0; i<count; i++){
            meatEntry.append(emojiMeat);
        }
    }

    private void updateCount() {
        if (meals == null) { return; }
        float currentBeef = 0f;
        float currentChicken = 0f;
        float currentPork = 0f;
        float currentOther = 0f;

        for (MealObject meal : meals) {
            if (meal == null) { return; }
            switch (meal.getType()) {
                case "Beef":
                    currentBeef += meal.getAmount();
                    Log.d("BEEF SWITCH", "updateCount: " + String.valueOf(currentBeef));
                    break;
                case "Chicken":
                    currentChicken += meal.getAmount();
                    break;
                case "Pork":
                    currentPork += meal.getAmount();
                    break;
                case "Other":
                    currentOther += meal.getAmount();
                    break;
            }
            if (currentBeef >= MEAT_BEEF) {
                while (currentBeef >= MEAT_BEEF) {
                    currentBeef -= MEAT_BEEF;
                    BEEF_USER++;
                }
            }
            if (currentChicken >= MEAT_CHICKEN) {
                while (currentChicken >= MEAT_CHICKEN) {
                    currentChicken -= MEAT_CHICKEN;
                    CHICKEN_USER++;
                }
            }
            if (currentPork >= MEAT_PORK) {
                while(currentPork >= MEAT_PORK) {
                    currentPork -= MEAT_PORK;
                    PORK_USER++;
                }
            }
            if (currentOther >= MEAT_OTHER) {
                while (currentOther >= MEAT_OTHER) {
                    currentOther -= MEAT_OTHER;
                    OTHER_MEAT_USER++;
                }
            }
            Log.d("LOOP B", "updateCount: " + String.valueOf(currentBeef));
            Log.d("LOOP B", "updateCount: " + String.valueOf(currentBeef));
        }


    }
}
