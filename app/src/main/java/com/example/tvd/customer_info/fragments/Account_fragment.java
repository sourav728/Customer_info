package com.example.tvd.customer_info.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tvd.customer_info.EditProfileActivity;
import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.ViewBillActivity;

public class Account_fragment extends Fragment {
    TextView edit_profile;
    TextView font_username,font_user_mobile,font_outstanding,font_viewbill,font_paynow,font_last_payment,font_amount,font_dated;
    Typeface typeface;
    public Account_fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_fragment, container, false);
        initialize(view);
        typeface=Typeface.createFromAsset(getActivity().getAssets(),"timesnewroman.ttf");

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });
        font_viewbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewBillActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public void initialize(View view)
    {
        font_username = (TextView) view.findViewById(R.id.txt_font_username);
        font_user_mobile = (TextView) view.findViewById(R.id.txt_font_mobile);
        edit_profile = (TextView) view.findViewById(R.id.txt_edit_profile);
        font_outstanding = (TextView) view.findViewById(R.id.txt_font_outstanding);
        font_viewbill = (TextView) view.findViewById(R.id.txt_view_bill);
        font_paynow = (TextView) view.findViewById(R.id.txt_paynow);
        font_last_payment = (TextView) view.findViewById(R.id.txt_font_lastpayment);
        font_amount = (TextView) view.findViewById(R.id.txt_font_amount);
        font_dated = (TextView) view.findViewById(R.id.txt_font_dated);
        //setting typespace values to textviews
        font_username.setTypeface(typeface);
        font_user_mobile.setTypeface(typeface);
        edit_profile.setTypeface(typeface);
        font_outstanding.setTypeface(typeface);
        font_viewbill.setTypeface(typeface);
        font_paynow.setTypeface(typeface);
        font_last_payment.setTypeface(typeface);
        font_dated.setTypeface(typeface);
    }
}
