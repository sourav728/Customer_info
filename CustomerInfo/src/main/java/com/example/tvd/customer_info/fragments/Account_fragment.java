package com.example.tvd.customer_info.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.AccountRegistrationActivity;
import com.example.tvd.customer_info.EditProfileActivity;
import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.ViewBillActivity;
import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tvd.customer_info.values.ConstantValues.INSERTION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.INSERTION_SUCCESSFULL;
import static com.example.tvd.customer_info.values.ConstantValues.LAST_PAYMENT_DATE_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.LAST_PAYMENT_DATE_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.SEARCH_FOUND;
import static com.example.tvd.customer_info.values.ConstantValues.SEARCH_NOT_FOUND;

public class Account_fragment extends Fragment {
    TextView edit_profile, last_date, last_amt;
    TextView font_username, font_user_mobile, font_outstanding, font_viewbill, font_paynow, font_last_payment, font_amount, font_dated;
    Typeface typeface;
    String useremail = "";
    SendingData sendingData;
    GetSetValues getSetValues;
    FunctionCall fcall;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LAST_PAYMENT_DATE_SUCCESS:
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        setView();
                        break;
                    case LAST_PAYMENT_DATE_FAILURE:
                        Toast.makeText(getActivity(), "Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }


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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        useremail = sharedPreferences.getString("EMAIL", "");
        String language = sharedPreferences.getString("LANGUAGE", "");

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("SWITCH_CONSUMER_ID",MODE_PRIVATE);
        String curr_consumer_id = sharedPreferences1.getString("Curr_Cons_ID", "");

        initialize(view);
        font_username.setText(useremail);
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
        //below code is for loading different font 
        if (!language.equals("")) {
            if (language.equals("KN")) {
                updateViews("KN");
            } else if (language.equals("en")) {
                updateViews("en");
            }
        }
        if (!curr_consumer_id.equals(""))
        {
            SendingData.Send_customer_last_payment_details send_customer_last_payment_details = sendingData.new Send_customer_last_payment_details(mHandler, getSetValues);
            send_customer_last_payment_details.execute(curr_consumer_id);
        }
        return view;
    }

    public void setView() {
        last_date.setText(fcall.Parse_date(getSetValues.getLast_receipt_date()));
        last_amt.setText(String.format("%s %s", getActivity().getResources().getString(R.string.rupee),getSetValues.getLast_payment_amt()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void initialize(View view) {
        fcall = new FunctionCall();
        sendingData = new SendingData();
        getSetValues = new GetSetValues();
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "calibri.ttf");
        font_username = (TextView) view.findViewById(R.id.txt_font_username);
        font_user_mobile = (TextView) view.findViewById(R.id.txt_font_mobile);
        edit_profile = (TextView) view.findViewById(R.id.txt_edit_profile);
        font_outstanding = (TextView) view.findViewById(R.id.txt_font_outstanding);
        font_viewbill = (TextView) view.findViewById(R.id.txt_view_bill);
        font_paynow = (TextView) view.findViewById(R.id.txt_paynow);
        font_last_payment = (TextView) view.findViewById(R.id.txt_font_lastpayment);
        font_amount = (TextView) view.findViewById(R.id.txt_font_amount);
        font_dated = (TextView) view.findViewById(R.id.txt_font_dated);
        last_amt = (TextView) view.findViewById(R.id.txt_last_amount);
        last_date = (TextView) view.findViewById(R.id.txt_last_date);
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

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(getActivity(), languageCode);
        Resources resources = context.getResources();
        edit_profile.setText(resources.getString(R.string.view_edit_profile));
        font_viewbill.setText(resources.getString(R.string.view_bill));
        font_paynow.setText(resources.getString(R.string.pay_now));
        font_outstanding.setText(resources.getString(R.string.total_outstanding));
        font_last_payment.setText(resources.getString(R.string.last_payment_details));
    }
}

