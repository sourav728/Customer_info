package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;

import static com.example.tvd.customer_info.values.ConstantValues.VIEW_BILL_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.VIEW_BILL_SUCCESS;

public class ViewBillActivity extends AppCompatActivity {
    private Toolbar toolbar;
    SendingData sendingdata;
    Typeface typeface,typeface2;
    TextView font_toolbar_title;
    TextView billing_period, reading_date, bill_no, due_date, sanc_load, connection_type, type_of_supply, tariff_plan,
            previous_reading, present_reading, constant, consumption, average, fixed_charge, energy_charge, fac,
            tod_changes, pf_panelty, ex_load_md_panelty, interest, others, tax, curr_bill_amt, arrears, credit_adjustment,
            gok_subsidy;
    TextView font_cons_name,font_fac, font_cons_address, font_bill_details, font_billing_period, font_reading_date, font_bill_no, font_due_date, font_personal_details,
            font_account_details, font_connection_details, font_sanc_load, font_connection_type, font_type_of_supply,
            font_consumption_details, font_previous_reading, font_present_reading, font_constant,
            font_consumption, font_average, font_fixed_charges, font_energy_charges, font_additional_charges, font_tod_changes,
            font_pf_panelty, font_ex_load_panelty, font_interest, font_others, font_tax, font_curr_bill_amt, font_arrears,
            font_credit_adjustment, font_gok_subsidy;
    TextView consumer_name, consumer_address, consumer_id, consumer_rrno;
    ProgressDialog progressDialog;
    GetSetValues getsetvalues;
    String TokenID = "0x9851FFA7317D3E4F191A969454138816104173F9";
    FunctionCall functionCall;
    Button pay;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case VIEW_BILL_SUCCESS:
                        progressDialog.dismiss();
                        Toast.makeText(ViewBillActivity.this, "Success..", Toast.LENGTH_SHORT).show();
                        setTextViewValues();
                        break;
                    case VIEW_BILL_FAILURE:
                        progressDialog.dismiss();
                        Toast.makeText(ViewBillActivity.this, "Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bill_layout);
        //function to initialize all textviews
        initialize();
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(ViewBillActivity.this, R.style.MyProgressDialogstyle);
        progressDialog.setTitle("Connecting To Server");
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        SharedPreferences sharedPreferences1 = getSharedPreferences("SWITCH_CONSUMER_ID", MODE_PRIVATE);
        String curr_consumer_id = sharedPreferences1.getString("Curr_Cons_ID", "");
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String language = sharedPreferences.getString("LANGUAGE", "");

        if (!curr_consumer_id.equals("")) {
            SendingData.ViewBill view_bill = sendingdata.new ViewBill(mHandler, getsetvalues);
            view_bill.execute(curr_consumer_id, TokenID);
        } else {
            finish();
            Toast.makeText(this, "Please Select Consumer id to View bill details!!", Toast.LENGTH_SHORT).show();
        }
        //below code is for loading different font
        if (!language.equals(""))
        {
            if (language.equals("KN"))
            {
                updateViews("KN");
            }
            else if (language.equals("en"))
            {
                updateViews("en");
            }
        }

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBillActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initialize() {
        functionCall = new FunctionCall();
        sendingdata = new SendingData();
        getsetvalues = new GetSetValues();
        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(), "CALIBRIB.TTF");
        pay = (Button) findViewById(R.id.btn_pay);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        font_toolbar_title.setTypeface(typeface);
        font_toolbar_title.setText("Bill Details");
        //All Textview Initialization for setting values
        billing_period = (TextView) findViewById(R.id.txt_billing_period);
        reading_date = (TextView) findViewById(R.id.txt_reading_date);
        bill_no = (TextView) findViewById(R.id.txt_bill_no);
        due_date = (TextView) findViewById(R.id.txt_due_date);
        sanc_load = (TextView) findViewById(R.id.txt_sancsion_load);
        connection_type = (TextView) findViewById(R.id.txt_connection_type);
        type_of_supply = (TextView) findViewById(R.id.txt_type_of_supply);
        previous_reading = (TextView) findViewById(R.id.txt_previous_reading);
        present_reading = (TextView) findViewById(R.id.txt_present_reading);
        constant = (TextView) findViewById(R.id.txt_constant);
        consumption = (TextView) findViewById(R.id.txt_consumption);
        average = (TextView) findViewById(R.id.txt_average);
        fixed_charge = (TextView) findViewById(R.id.txt_fixed_charges);
        energy_charge = (TextView) findViewById(R.id.txt_energy_charges);

        fac = (TextView) findViewById(R.id.txt_fac);
        tod_changes = (TextView) findViewById(R.id.txt_tod_changes);
        pf_panelty = (TextView) findViewById(R.id.txt_pf_penalty);
        ex_load_md_panelty = (TextView) findViewById(R.id.txt_ex_load_md_penalty);
        interest = (TextView) findViewById(R.id.txt_interest);
        others = (TextView) findViewById(R.id.txt_other);
        tax = (TextView) findViewById(R.id.txt_tax);
        curr_bill_amt = (TextView) findViewById(R.id.txt_curr_bill_amt);
        arrears = (TextView) findViewById(R.id.txt_arrears);
        credit_adjustment = (TextView) findViewById(R.id.txt_credit_and_adjustment);
        gok_subsidy = (TextView) findViewById(R.id.txt_gok_subsidy);

        //All Textview initialization values for setting fonts to textview level

        font_cons_name = (TextView) findViewById(R.id.font_cons_address);
        font_cons_address = (TextView) findViewById(R.id.font_cons_address);
        font_fac = (TextView) findViewById(R.id.font_fac);
        font_bill_details = (TextView) findViewById(R.id.font_billing_details);
        font_billing_period = (TextView) findViewById(R.id.font_billing_period);
        font_reading_date = (TextView) findViewById(R.id.font_reading_date);
        font_bill_no = (TextView) findViewById(R.id.font_bill_no);
        font_due_date = (TextView) findViewById(R.id.font_due_date);
        font_personal_details = (TextView) findViewById(R.id.font_personal_details);
        font_account_details = (TextView) findViewById(R.id.font_account_details);
        font_connection_details = (TextView) findViewById(R.id.font_connection_details);
        font_sanc_load = (TextView) findViewById(R.id.font_sanc_load);
        font_connection_type = (TextView) findViewById(R.id.font_connection_type);
        font_type_of_supply = (TextView) findViewById(R.id.font_type_of_supply);
        font_consumption_details = (TextView) findViewById(R.id.font_consumption_details);
        font_previous_reading = (TextView) findViewById(R.id.font_previous_reading);
        font_present_reading = (TextView) findViewById(R.id.font_present_reading);
        font_constant = (TextView) findViewById(R.id.font_constant);
        font_consumption = (TextView) findViewById(R.id.font_consumption);
        font_average = (TextView) findViewById(R.id.font_average);
        font_fixed_charges = (TextView) findViewById(R.id.font_fixed_charges);
        font_energy_charges = (TextView) findViewById(R.id.font_energy_charges);
        font_additional_charges = (TextView) findViewById(R.id.font_additional_charges);
        font_tod_changes = (TextView) findViewById(R.id.font_tod_changes);
        font_pf_panelty = (TextView) findViewById(R.id.font_pf_panelty);
        font_ex_load_panelty = (TextView) findViewById(R.id.font_ex_load_panelty);
        font_interest = (TextView) findViewById(R.id.font_interest);
        font_others = (TextView) findViewById(R.id.font_others);
        font_tax = (TextView) findViewById(R.id.font_tax);
        font_curr_bill_amt = (TextView) findViewById(R.id.font_curr_bill_amt);
        font_arrears = (TextView) findViewById(R.id.font_arrears);
        font_credit_adjustment = (TextView) findViewById(R.id.font_credit_adjustment);
        font_gok_subsidy = (TextView) findViewById(R.id.font_gok_subsidy);
        consumer_name = (TextView) findViewById(R.id.txt_name);
        consumer_address = (TextView) findViewById(R.id.txt_address);
        consumer_id = (TextView) findViewById(R.id.txt_consumer_id);
        consumer_rrno = (TextView) findViewById(R.id.txt_rrno);
        //setting typespace values
        font_cons_name.setTypeface(typeface);
        font_cons_address.setTypeface(typeface);
        font_fac.setTypeface(typeface);
        font_bill_details.setTypeface(typeface);
        font_billing_period.setTypeface(typeface);
        font_reading_date.setTypeface(typeface);
        font_bill_no.setTypeface(typeface);
        font_due_date.setTypeface(typeface);
        font_personal_details.setTypeface(typeface);
        font_account_details.setTypeface(typeface);
        font_connection_details.setTypeface(typeface);
        font_sanc_load.setTypeface(typeface);
        font_connection_type.setTypeface(typeface);
        font_type_of_supply.setTypeface(typeface);
        font_consumption_details.setTypeface(typeface);
        font_previous_reading.setTypeface(typeface);
        font_present_reading.setTypeface(typeface);
        font_constant.setTypeface(typeface);
        font_consumption.setTypeface(typeface);
        font_average.setTypeface(typeface);
        font_fixed_charges.setTypeface(typeface);
        font_energy_charges.setTypeface(typeface);
        font_additional_charges.setTypeface(typeface);
        font_tod_changes.setTypeface(typeface);
        font_pf_panelty.setTypeface(typeface);
        font_ex_load_panelty.setTypeface(typeface);
        font_interest.setTypeface(typeface);
        font_others.setTypeface(typeface);
        font_tax.setTypeface(typeface);
        font_curr_bill_amt.setTypeface(typeface2);
        font_arrears.setTypeface(typeface);
        font_credit_adjustment.setTypeface(typeface);
        font_gok_subsidy.setTypeface(typeface);

        reading_date.setTypeface(typeface);
        due_date.setTypeface(typeface);
        consumer_name.setTypeface(typeface);
        consumer_address.setTypeface(typeface);
        consumer_id.setTypeface(typeface);
        consumer_rrno.setTypeface(typeface);
        sanc_load.setTypeface(typeface);
        connection_type.setTypeface(typeface);
        type_of_supply.setTypeface(typeface);
        previous_reading.setTypeface(typeface);
        present_reading.setTypeface(typeface);
        constant.setTypeface(typeface);
        consumption.setTypeface(typeface);
        average.setTypeface(typeface);
        fixed_charge.setTypeface(typeface);
        energy_charge.setTypeface(typeface);
        fac.setTypeface(typeface);
        tod_changes.setTypeface(typeface);
        pf_panelty.setTypeface(typeface);
        ex_load_md_panelty.setTypeface(typeface);
        interest.setTypeface(typeface);
        others.setTypeface(typeface);
        tax.setTypeface(typeface);
        curr_bill_amt.setTypeface(typeface2);
        arrears.setTypeface(typeface);
        credit_adjustment.setTypeface(typeface);
        gok_subsidy.setTypeface(typeface);

        pay.setTypeface(typeface);
    }
    public void setTextViewValues() {
        reading_date.setText(functionCall.Parse_date(getsetvalues.getView_bill_date1()));
        due_date.setText(functionCall.Parse_date(getsetvalues.getView_bill_due_date()));
        consumer_name.setText(getsetvalues.getView_bill_cons_name());
        consumer_address.setText(getsetvalues.getView_bill_add());
        consumer_id.setText(getsetvalues.getView_bill_cons_id());
        consumer_rrno.setText(getsetvalues.getView_bill_rrno());
        sanc_load.setText(getsetvalues.getView_bill_kwhp());
        connection_type.setText(getsetvalues.getView_bill_tariff());
        previous_reading.setText(getsetvalues.getView_bill_prevread());
        present_reading.setText(getsetvalues.getView_bill_curr_read());
        consumption.setText(getsetvalues.getView_bill_con());
        average.setText(getsetvalues.getView_bill_avgcon());

        tod_changes.setText(String.format("%s %s", this.getResources().getString(R.string.rupee), getsetvalues.getView_bill_rbtamt()));
        fixed_charge.setText(String.format("%s %s", this.getResources().getString(R.string.rupee),getsetvalues.getView_bill_fc()));
        interest.setText(String.format("%s %s", this.getResources().getString(R.string.rupee), getsetvalues.getView_bill_di()));
        others.setText(String.format("%s %s", this.getResources().getString(R.string.rupee), getsetvalues.getView_bill_do()));
        tax.setText(String.format("%s %s", this.getResources().getString(R.string.rupee), getsetvalues.getView_bill_dt()));
        energy_charge.setText(String.format("%s %s", this.getResources().getString(R.string.rupee),getsetvalues.getView_bill_ec()));
        fac.setText(String.format("%s %s", this.getResources().getString(R.string.rupee),getsetvalues.getView_bill_fac()));
        pf_panelty.setText(String.format("%s %s", this.getResources().getString(R.string.rupee),getsetvalues.getView_bill_pfpanelty()));
        ex_load_md_panelty.setText(String.format("%s %s", this.getResources().getString(R.string.rupee),getsetvalues.getView_bill_bmdpanelty()));
        curr_bill_amt.setText(String.format("%s %s", this.getResources().getString(R.string.rupee), getsetvalues.getView_bill_netpayable()));
        arrears.setText(String.format("%s %s", this.getResources().getString(R.string.rupee), getsetvalues.getView_bill_arrears()));
        credit_adjustment.setText(String.format("%s %s", this.getResources().getString(R.string.rupee),getsetvalues.getView_bill_adjamt()));

    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(ViewBillActivity.this, languageCode);
        Resources resources = context.getResources();
        font_toolbar_title.setText(resources.getString(R.string.view_bill));
    }
}
