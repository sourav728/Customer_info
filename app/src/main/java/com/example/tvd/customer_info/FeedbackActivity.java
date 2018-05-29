package com.example.tvd.customer_info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.adapter.RoleAdapter;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity {
    ArrayList<GetSetValues> feedback_list;
    RoleAdapter feedbackAdapter;
    Spinner feedback_spiner;
    GetSetValues getSetValues;
    String main_role = "";
    EditText feedback_description;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback_list = new ArrayList<>();
        feedbackAdapter = new RoleAdapter(feedback_list,this);
        feedback_spiner = (Spinner) findViewById(R.id.spinner);
        feedback_spiner.setAdapter(feedbackAdapter);
        feedback_description = (EditText) findViewById(R.id.edit_account_id);
        submit = (Button) findViewById(R.id.btn_submit);
        //Setting status spinner
        for (int i = 0; i < getResources().getStringArray(R.array.feedbacktype).length; i++) {
            getSetValues = new GetSetValues();
            getSetValues.setSpiner_item(getResources().getStringArray(R.array.feedbacktype)[i]);
            feedback_list.add(getSetValues);
            feedbackAdapter.notifyDataSetChanged();
        }
        feedback_spiner.setSelection(0);

        feedback_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView role = (TextView) view.findViewById(R.id.spinner_txt);
                role.setBackgroundDrawable(null);
                String selected_role = role.getText().toString();
                if (!selected_role.equals("--Select Feedback Type--")) {
                    main_role = selected_role;
                    Toast.makeText(FeedbackActivity.this, main_role + " Selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        feedback_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                submit.setEnabled(!TextUtils.isEmpty(s.toString().trim()));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submit.setEnabled(!TextUtils.isEmpty(s.toString().trim()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                submit.setEnabled(!TextUtils.isEmpty(s.toString().trim()));
            }
        });
    }
}


