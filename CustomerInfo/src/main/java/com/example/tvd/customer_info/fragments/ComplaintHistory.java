package com.example.tvd.customer_info.fragments;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.SwitchConsumerActivity;
import com.example.tvd.customer_info.adapter.ComplaintHistoryAdapter;
import com.example.tvd.customer_info.adapter.ConsumerListAdapter;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_DEACTIVATED_SUCCESSFULLY;
import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_DEACTIVATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.COMPLAINT_HISTORY_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.COMPLAINT_HISTORY_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_SUCCESS;

public class ComplaintHistory extends Fragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    ArrayList<GetSetValues>arrayList;
    GetSetValues getSetValues;
    private ComplaintHistoryAdapter complaintHistoryAdapter;
    Context context;
    CardView cardView;
    AlertDialog alertDialog;
    SendingData sendingData;
    EditText account_id;
    Button search;
    private final Handler mHandler;
    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case COMPLAINT_HISTORY_SUCCESS:
                        Toast.makeText(getActivity(), "Success..", Toast.LENGTH_SHORT).show();
                        break;
                    case COMPLAINT_HISTORY_FAILURE:
                        Toast.makeText(getActivity(), "Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    public ComplaintHistory() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_complaint_history, container, false);

        sendingData = new SendingData();
        getSetValues = new GetSetValues();
        account_id = (EditText) view.findViewById(R.id.edit_search2);
        search = (Button) view.findViewById(R.id.btn_search2);
        recyclerView = (RecyclerView) view.findViewById(R.id.consumer_recycler_view);
        arrayList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.consumer_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        complaintHistoryAdapter = new ComplaintHistoryAdapter(arrayList, getActivity(), getSetValues);
        complaintHistoryAdapter = new ComplaintHistoryAdapter(arrayList, getActivity(), getSetValues);
        recyclerView.setAdapter(complaintHistoryAdapter);
        /*cardView = (CardView) view.findViewById(R.id.card_view);
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
        });*/

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!account_id.getText().toString().equals(""))
                {
                    SendingData.ComplaintSearch complaintSearch = sendingData.new ComplaintSearch(mHandler, getSetValues, arrayList, complaintHistoryAdapter);
                    complaintSearch.execute(account_id.getText().toString());
                }
                else Toast.makeText(getActivity(), "Please Enter Account Id!!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
