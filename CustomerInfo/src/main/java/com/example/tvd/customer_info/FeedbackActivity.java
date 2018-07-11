package com.example.tvd.customer_info;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tvd.customer_info.adapter.RoleAdapter;
import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_DEACTIVATED_SUCCESSFULLY;
import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_DEACTIVATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.FEEDBACK_STATUS_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.FEEDBACK_STATUS_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_SUCCESS;

public class FeedbackActivity extends AppCompatActivity {
    ArrayList<GetSetValues> feedback_list;
    RoleAdapter feedbackAdapter;
    Spinner feedback_spiner;
    GetSetValues getSetValues;
    String main_role = "", curr_consumer_id = "";
    EditText feedback_description;
    Button submit;
    TextView font_toolbar_text;
    Typeface typeface;
    SendingData sendingData;
    private android.support.v7.widget.Toolbar toolbar;

    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FEEDBACK_STATUS_SUCCESS:
                        Toast.makeText(FeedbackActivity.this, "Feedback submitted..", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case FEEDBACK_STATUS_FAILURE:
                        Toast.makeText(FeedbackActivity.this, "Feedback submission failure!!", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        //For Hiding Keyboard below code is used
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String language = sharedPreferences.getString("LANGUAGE", "");
        SharedPreferences sharedPreferences1 = getSharedPreferences("SWITCH_CONSUMER_ID", MODE_PRIVATE);
        curr_consumer_id = sharedPreferences1.getString("Curr_Cons_ID", "");

        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        sendingData = new SendingData();
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        font_toolbar_text = (TextView) toolbar.findViewById(R.id.toolbar_title);
        font_toolbar_text.setTypeface(typeface);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        feedback_list = new ArrayList<>();
        feedbackAdapter = new RoleAdapter(feedback_list, this);
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
                   // Toast.makeText(FeedbackActivity.this, main_role + " Selected", Toast.LENGTH_SHORT).show();
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!curr_consumer_id.equals("")) {
                    if (!main_role.equals("")) {
                        if (!feedback_description.getText().toString().equals("")) {
                            SendingData.Send_feedback send_feedback = sendingData.new Send_feedback(mHandler);
                            send_feedback.execute(curr_consumer_id, main_role, feedback_description.getText().toString());
                        } else
                            Toast.makeText(FeedbackActivity.this, "Please select feedback description!!", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(FeedbackActivity.this, "Please select feedback type!!", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(FeedbackActivity.this, "Please select current consumer id!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(FeedbackActivity.this, languageCode);
        Resources resources = context.getResources();
        font_toolbar_text.setText(resources.getString(R.string.feedback));
    }
}


