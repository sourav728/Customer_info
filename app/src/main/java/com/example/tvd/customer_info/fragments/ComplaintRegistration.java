package com.example.tvd.customer_info.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.ViewBillActivity;
import com.example.tvd.customer_info.adapter.RoleAdapter;
import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_ID_SEARCH_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_ID_SEARCH_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.COMPLAINT_REGISTER_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.COMPLAINT_REGISTER_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.VIEW_BILL_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.VIEW_BILL_SUCCESS;


public class ComplaintRegistration extends Fragment {

    private Toolbar toolbar;
    TextView font_toolbar_title;
    Typeface typeface;
    ArrayList<GetSetValues> complaint_list, sub_category_list;
    RoleAdapter complaint_adapter, sub_category_adapter;
    Spinner first_spiner, second_spiner;
    GetSetValues getSetValues;
    String main_role = "", complaint_issue = "", sub_complaint_issue = "";
    EditText customer_search;
    FunctionCall fcall;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Button search;
    View view;
    ProgressDialog progressDialog;
    SendingData sendingData;
    TextView con_name, con_rrno, con_acc_id, con_feeder_name, con_tc_code, con_tariff_code, con_address, con_subdivision, con_mobile_no,
            con_pole_no;
    LinearLayout show_hide;
    EditText landmark, remark;
    Button submit;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ACCOUNT_ID_SEARCH_SUCCESS:
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Success..", Toast.LENGTH_SHORT).show();
                        show_hide.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.VISIBLE);
                        setTextViewValues();
                        break;
                    case ACCOUNT_ID_SEARCH_FAILURE:
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                    case COMPLAINT_REGISTER_SUCCESS:
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        break;
                    case COMPLAINT_REGISTER_FAILURE:
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    public ComplaintRegistration() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complaint_registration, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String language = sharedPreferences.getString("LANGUAGE", "");
        initialize();
        //Setting status spinner
        for (int i = 0; i < getResources().getStringArray(R.array.complaint_list).length; i++) {
            getSetValues = new GetSetValues();
            getSetValues.setSpiner_item(getResources().getStringArray(R.array.complaint_list)[i]);
            complaint_list.add(getSetValues);
            complaint_adapter.notifyDataSetChanged();
        }
        first_spiner.setSelection(0);

        first_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView role = (TextView) view.findViewById(R.id.spinner_txt);
                String selected_role = role.getText().toString();
                if (!selected_role.equals("--SELECT--")) {
                    main_role = selected_role;
                    GetSetValues complaint = complaint_list.get(position);
                    complaint_issue = complaint.getSpiner_item();
                }
                if (selected_role.equals("BILLING ISSUES")) {
                    sub_category_list.clear();
                    //Setting status spinner
                    for (int i = 0; i < getResources().getStringArray(R.array.first).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.first)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("ADDITIONAL TC/ENHANCEMENT")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.second).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.second)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("NEW CONNECTION/ADDITIONAL LOAD")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.third).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.third)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("VOLTAGE COMPLAINTS")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.fourth).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.fourth)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("FAILURE OF POWER SUPPLY")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.fifth).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.fifth)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("GENERAL")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.sixth).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.sixth)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("TRANSFER OF OWNERSHIP AND CONVERSION")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.seventh).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.seventh)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("SAFETY ISSUES")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.eight).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.eight)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("REFUND/ISSUE OF CERTIFICATES")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.ninth).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.ninth)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("ALLEGATIONS ON STAFF")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.tenth).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.tenth)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("PHASE CONVERSION")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.eleven).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.eleven)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("METER COMPLAINTS")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.twelve).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.twelve)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("THEFT")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.thirteen).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.thirteen)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }
                } else if (selected_role.equals("TRANSFORMER FAILURE COMPLAINT")) {
                    sub_category_list.clear();
                    for (int i = 0; i < getResources().getStringArray(R.array.fourteen).length; i++) {
                        getSetValues = new GetSetValues();
                        getSetValues.setSpiner_item(getResources().getStringArray(R.array.fourteen)[i]);
                        sub_category_list.add(getSetValues);
                        sub_category_adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        second_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView role = (TextView) view.findViewById(R.id.spinner_txt);
                GetSetValues complaint_subtype = sub_category_list.get(position);
                sub_complaint_issue = complaint_subtype.getSpiner_item();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //For Hiding keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //below code is for loading different font
        if (!language.equals("")) {
            if (language.equals("KN")) {
                updateViews("KN");
            } else if (language.equals("en")) {
                updateViews("en");
            }
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "Please select anyone option first!!", Toast.LENGTH_SHORT).show();
                } else {
                    radioButton = (RadioButton) view.findViewById(selectedId);
                    if (StringUtils.startsWithIgnoreCase(radioButton.getText().toString(), "Account Id")) {
                        progressDialog = new ProgressDialog(getActivity(), R.style.MyProgressDialogstyle);
                        progressDialog.setTitle("Connecting To Server");
                        progressDialog.setMessage("Please Wait..");
                        progressDialog.show();
                        Toast.makeText(getActivity(), "Call First Service", Toast.LENGTH_SHORT).show();
                        SendingData.CustomerSearch_CONSID customerSearch_consid = sendingData.new CustomerSearch_CONSID(mHandler, getSetValues);
                        customerSearch_consid.execute(customer_search.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), "Call Second Service", Toast.LENGTH_SHORT).show();
                        progressDialog = new ProgressDialog(getActivity(), R.style.MyProgressDialogstyle);
                        progressDialog.setTitle("Connecting To Server");
                        progressDialog.setMessage("Please Wait..");
                        progressDialog.show();
                        SendingData.CustomerSearch_RRNO customerSearch_rrno = sendingData.new CustomerSearch_RRNO(mHandler, getSetValues);
                        customerSearch_rrno.execute(customer_search.getText().toString());
                    }
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity(), R.style.MyProgressDialogstyle);
                progressDialog.setTitle("Registering Your Complaint..");
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
                SendingData.Complaint_Register complaint_register = sendingData.new Complaint_Register(mHandler, getSetValues);
                complaint_register.execute(con_rrno.getText().toString(), con_acc_id.getText().toString(), con_subdivision.getText().toString(), con_mobile_no.getText().toString(),
                        complaint_issue, sub_complaint_issue, landmark.getText().toString(), remark.getText().toString());
            }
        });

        return view;
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(getActivity(), languageCode);
        Resources resources = context.getResources();
    }

    public void initialize() {
        sendingData = new SendingData();
        getSetValues = new GetSetValues();
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "calibri.ttf");
        toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        fcall = new FunctionCall();
        show_hide = (LinearLayout) view.findViewById(R.id.lin_show_hide);
        first_spiner = (Spinner) view.findViewById(R.id.spinner1);
        second_spiner = (Spinner) view.findViewById(R.id.spiner2);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        search = (Button) view.findViewById(R.id.btn_search);

        complaint_list = new ArrayList<>();
        complaint_adapter = new RoleAdapter(complaint_list, getActivity());
        first_spiner.setAdapter(complaint_adapter);

        sub_category_list = new ArrayList<>();
        sub_category_adapter = new RoleAdapter(sub_category_list, getActivity());
        second_spiner.setAdapter(sub_category_adapter);

        customer_search = (EditText) view.findViewById(R.id.edit_search);
        landmark = (EditText) view.findViewById(R.id.edit_land_mark);
        remark = (EditText) view.findViewById(R.id.edit_remark);
        con_name = (TextView) view.findViewById(R.id.txt_name);
        con_rrno = (TextView) view.findViewById(R.id.txt_rrno);
        con_acc_id = (TextView) view.findViewById(R.id.txt_acc_id);
        con_feeder_name = (TextView) view.findViewById(R.id.txt_feeder_name);
        con_tc_code = (TextView) view.findViewById(R.id.txt_tc_code);
        con_tariff_code = (TextView) view.findViewById(R.id.txt_tariff_code);
        con_address = (TextView) view.findViewById(R.id.txt_address);
        con_subdivision = (TextView) view.findViewById(R.id.txt_subdivision);
        con_mobile_no = (TextView) view.findViewById(R.id.txt_mobileno);
        con_pole_no = (TextView) view.findViewById(R.id.txt_pole_no);

        submit = (Button) view.findViewById(R.id.btn_submit);
    }

    public void setTextViewValues() {
        con_name.setText(getSetValues.getComplaint_customer_name());
        con_rrno.setText(getSetValues.getComplaint_rrno());
        con_acc_id.setText(getSetValues.getComplaint_cons_no());
        con_feeder_name.setText(getSetValues.getComplaint_feeder_name());
        con_tc_code.setText(getSetValues.getComplaint_tc_code());
        con_tariff_code.setText(getSetValues.getComplaint_tariff());
        con_address.setText(getSetValues.getComplaint_add());
        con_subdivision.setText(getSetValues.getComplaint_subdivision_code());
        con_mobile_no.setText(getSetValues.getComplaint_mobile_no());
        con_pole_no.setText(getSetValues.getComplaint_poleno());
    }

}
