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
    TextView font_toolbar_title,font_billing_details,font_billing_period,font_reading_date,font_bill_no,font_due_date,
             font_personal_details,font_account_details,font_connection_details;
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
    public void initialize()
    {
        typeface = Typeface.createFromAsset(getAssets(),"timesnewroman.ttf");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        font_billing_details = (TextView) findViewById(R.id.txt_billing_details);
        font_billing_period = (TextView) findViewById(R.id.txt_billing_period);
        font_reading_date = (TextView) findViewById(R.id.txt_reading_date);
        font_bill_no = (TextView) findViewById(R.id.txt_bill_no);
        font_due_date = (TextView) findViewById(R.id.txt_due_date);
        font_personal_details = (TextView) findViewById(R.id.txt_personal_details);
        font_account_details = (TextView) findViewById(R.id.txt_account_details);
        font_connection_details = (TextView) findViewById(R.id.txt_connection_details);
        font_toolbar_title.setTypeface(typeface);
        font_toolbar_title.setText("Bill Details");
    }
}
