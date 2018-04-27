package com.example.tvd.customer_info;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ComplaintRegistration extends AppCompatActivity {
    private Toolbar toolbar;
    TextView font_toolbar_title;
    Typeface typeface;
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
    public void initialize()
    {
        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.back);
        font_toolbar_title.setTypeface(typeface);
        font_toolbar_title.setText("Complaint Registration");
    }
}
