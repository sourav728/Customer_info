package com.example.tvd.customer_info.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tvd.customer_info.R;


public class Notification_fragment extends Fragment {


    public Notification_fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notification_fragment, container, false);
        getActivity().setTitle("Notification");
        return view;
    }

}
