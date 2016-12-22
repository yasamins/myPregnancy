package com.example.pregnancyTracker.HeartRateSensor;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pregnancyTracker.v1.R;

public class MWScannerFragment extends DialogFragment {


    public MWScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mwscanner, container, false);
    }

}
