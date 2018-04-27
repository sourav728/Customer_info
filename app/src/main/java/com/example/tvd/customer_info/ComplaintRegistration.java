package com.example.tvd.customer_info;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tvd.customer_info.adapter.RoleAdapter;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

public class ComplaintRegistration extends AppCompatActivity {
    private Toolbar toolbar;
    TextView font_toolbar_title;
    Typeface typeface;
    ArrayList<GetSetValues> complaint_list, sub_category_list;
    RoleAdapter complaint_adapter, sub_category_adapter;
    Spinner first_spiner, second_spiner;
    GetSetValues getSetValues;
    String main_role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registration);
        initialize();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initialize() {
        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.back);
        font_toolbar_title.setTypeface(typeface);
        font_toolbar_title.setText("Complaint Registration");

        first_spiner = (Spinner) findViewById(R.id.spinner1);
        second_spiner = (Spinner) findViewById(R.id.spiner2);

        complaint_list = new ArrayList<>();
        complaint_adapter = new RoleAdapter(complaint_list, this);
        first_spiner.setAdapter(complaint_adapter);

        sub_category_list = new ArrayList<>();
        sub_category_adapter = new RoleAdapter(sub_category_list, this);
        second_spiner.setAdapter(sub_category_adapter);

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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
