package com.example.tvd.customer_info.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.Billing_Collection_Tabbed_Activity;
import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.adapter.LTBilling_Adapter;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tvd.customer_info.values.ConstantValues.BILLING_FRAGMENT_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.BILLING_FRAGMENT_fAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.COMPLAINT_HISTORY_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.COMPLAINT_HISTORY_SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingFragment extends Fragment {
    SendingData sendingdata;
    String consno="";
    GetSetValues getsetvalues;
    RecyclerView billingrecyclerview;
    ArrayList<GetSetValues> arraylist;
    private LTBilling_Adapter ltbilling_adapter;
    TextView consid,cons_name,tariff,mobileno;
    String consname="",con_tariff="",phonono="",current_con_id="";
    SendingData sendingData;
    private final Handler mHandler;
    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case BILLING_FRAGMENT_SUCCESS:
                        Toast.makeText(getActivity(), "Success..", Toast.LENGTH_SHORT).show();
                        break;
                    case BILLING_FRAGMENT_fAILURE:
                        Toast.makeText(getActivity(), "Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    public BillingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        consno = sharedPreferences.getString("LT_BILLING_CON_ID", "");
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("SWITCH_CONSUMER_ID", MODE_PRIVATE);
        current_con_id = sharedPreferences1.getString("Curr_Cons_ID", "");

        Log.d("Debug","Got Consumerno"+consno);
        consname = sharedPreferences.getString("LT_BILLING_CON_NAME","");
        Log.d("Debug","Got con_name"+consname);
        con_tariff = sharedPreferences.getString("LT_BILLING_TARIFF","");
        Log.d("Debug","Got Cons_tariff"+con_tariff);
        phonono = sharedPreferences.getString("LT_BILLING_MOBILE_NO","");
        Log.d("Debug","Got COns_Mobileno"+phonono);

        sendingData = new SendingData();
        getsetvalues = new GetSetValues();
        consid = (TextView) view.findViewById(R.id.txt_acc_id);
        cons_name = (TextView) view.findViewById(R.id.txt_accname);
        tariff = (TextView) view.findViewById(R.id.txt_tariff);
        mobileno = (TextView) view.findViewById(R.id.txt_mob);
        arraylist = new ArrayList<>();
        setHasOptionsMenu(true);
        billingrecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        billingrecyclerview.setHasFixedSize(true);
        billingrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        ltbilling_adapter = new LTBilling_Adapter(getActivity(), arraylist);
        billingrecyclerview.setAdapter(ltbilling_adapter);


        consid.setText(consno);
        cons_name.setText(consname);
        tariff.setText(con_tariff);
        if (phonono.equals("0"))
        {
            mobileno.setText("NA");
        }
        else
        {
            mobileno.setText(phonono);
        }

        SendingData.BillingFragment billingFragment = sendingData.new BillingFragment(mHandler, getsetvalues ,arraylist, ltbilling_adapter);
        billingFragment.execute(current_con_id);
        return view;
    }

}
