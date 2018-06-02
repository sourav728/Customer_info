package com.example.tvd.customer_info.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.tvd.customer_info.R;

public class ComplaintHistory extends Fragment {
    CardView cardView;
    AlertDialog alertDialog;
    public ComplaintHistory() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_complaint_history, container, false);
        cardView = (CardView) view.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deactivate = new AlertDialog.Builder(getActivity());
                deactivate.setCancelable(false);
                LinearLayout deactivate_layout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.activity_dummy, null);
                deactivate.setView(deactivate_layout);

                Button cancel_btn = (Button) deactivate_layout.findViewById(R.id.btn_cancel);

                alertDialog = deactivate.create();
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });


        return view;
    }

}
