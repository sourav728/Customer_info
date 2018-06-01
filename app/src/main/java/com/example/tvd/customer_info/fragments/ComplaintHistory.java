package com.example.tvd.customer_info.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tvd.customer_info.R;

public class ComplaintHistory extends Fragment {

    public ComplaintHistory() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_complaint_history, container, false);
        return view;
    }

}
