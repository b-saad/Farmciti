package com.prototype.farmCiti.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prototype.farmCiti.R;

public class Analyticsfrag extends Fragment {

    //TODO: Your views here.
    private View rootView;

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
}
