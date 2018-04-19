package com.example.tvd.customer_info;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountRegistrationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Typeface typeface;
    TextView font_toolbar_title,font_acount, font_rrno;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_registration);
        typeface= Typeface.createFromAsset(getAssets(),"timesnewroman.ttf");
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
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        font_acount = (TextView) findViewById(R.id.txt_accountid);
        font_rrno = (TextView) findViewById(R.id.txt_rrno);
        save = (Button) findViewById(R.id.btn_save);
        font_acount.setTypeface(typeface);
        font_rrno.setTypeface(typeface);
        font_toolbar_title.setTypeface(typeface);
        font_toolbar_title.setText("Account Registration");
        toolbar.setNavigationIcon(R.drawable.back);
        save.setTypeface(typeface);
    }
}
