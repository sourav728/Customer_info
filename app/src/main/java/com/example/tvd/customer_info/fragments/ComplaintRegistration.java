package com.example.tvd.customer_info.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.adapter.RoleAdapter;
import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ComplaintRegistration extends Fragment {

    private Toolbar toolbar;
    TextView font_toolbar_title;
    Typeface typeface;
    ArrayList<GetSetValues> complaint_list, sub_category_list;
    RoleAdapter complaint_adapter, sub_category_adapter;
    Spinner first_spiner, second_spiner;
    GetSetValues getSetValues;
    String main_role = "";
    EditText customer_search;
    FunctionCall fcall;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Button search;
    View view;
    public ComplaintRegistration() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_complaint_registration, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String language = sharedPreferences.getString("LANGUAGE", "");


        typeface = Typeface.createFromAsset(getActivity().getAssets(), "calibri.ttf");
        toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        fcall = new FunctionCall();

       /* font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.back);
        font_toolbar_title.setTypeface(typeface);*/
        //font_toolbar_title.setText("Complaint Registration");

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


        //For Hiding keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

       /* toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });*/
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
                if (selectedId == -1)
                {
                    Toast.makeText(getActivity(), "Please select anyone option first!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    radioButton = (RadioButton) view.findViewById(selectedId);
                    Toast.makeText(getActivity(), radioButton.getText() + " Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(getActivity(), languageCode);
        Resources resources = context.getResources();
       // font_toolbar_title.setText(resources.getString(R.string.complaint_registration));
    }

}
