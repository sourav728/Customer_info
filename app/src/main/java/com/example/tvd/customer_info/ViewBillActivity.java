package com.example.tvd.customer_info;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewBillActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Typeface typeface;
    TextView font_toolbar_title;
    TextView billing_period, reading_date, bill_no, due_date, sanc_load, connection_type, type_of_supply, tariff_plan,
            previous_reading, present_reading, constant, consumption, average, fixed_charge, energy_charge, fac,
            tod_changes, pf_panelty, ex_load_md_panelty, interest, others, tax, curr_bill_amt, arrears, credit_adjustment,
            gok_subsidy;
    TextView font_bill_details, font_billing_period, font_reading_date, font_bill_no, font_due_date, font_personal_details,
            font_account_details, font_connection_details, font_sanc_load, font_connection_type, font_type_of_supply,
            font_tariff_plan, font_consumption_details, font_previous_reading, font_present_reading, font_constant,
            font_consumption, font_average, font_fixed_charges, font_energy_charges, font_additional_charges, font_tod_changes,
            font_pf_panelty, font_ex_load_panelty, font_interest, font_others, font_tax, font_curr_bill_amt, font_arrears,
            font_credit_adjustment, font_gok_subsidy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        initialize();
        toolbar.setNavigationIcon(R.drawable.back);
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
        tariff_plan = (TextView) findViewById(R.id.txt_tariff_plan);
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
        font_tariff_plan = (TextView) findViewById(R.id.font_tariff_plan);
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

        //setting typespace values
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
        font_tariff_plan.setTypeface(typeface);
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
        font_curr_bill_amt.setTypeface(typeface);
        font_arrears.setTypeface(typeface);
        font_credit_adjustment.setTypeface(typeface);
        font_gok_subsidy.setTypeface(typeface);
    }
}
