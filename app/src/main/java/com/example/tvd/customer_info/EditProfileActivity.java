package com.example.tvd.customer_info;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {
    LinearLayout email_hide, mobile_hide, show_email, show_mobile;
    ImageView img_mail, img_mobile;
    private Toolbar toolbar;
    Typeface typeface;
    TextView font_toolbar_title, font_email, font_mobile;
    String useremail="";
    EditText email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initialize();


        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_email.setVisibility(View.VISIBLE);
                img_mail.setVisibility(View.INVISIBLE);
            }
        });
        img_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_mobile.setVisibility(View.VISIBLE);
                img_mobile.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void initialize()
    {
        typeface = Typeface.createFromAsset(getAssets(),"calibri.ttf");
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        useremail = sharedPreferences.getString("EMAIL", "");

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        email_hide = (LinearLayout) findViewById(R.id.lin_email);
        mobile_hide = (LinearLayout) findViewById(R.id.lin_mobile);
        show_email = (LinearLayout) findViewById(R.id.lin_show_email);
        show_mobile = (LinearLayout) findViewById(R.id.lin_show_mobile);
        img_mail = (ImageView) findViewById(R.id.img_edit_mail);
        img_mobile = (ImageView) findViewById(R.id.img_edit_mobile);
        font_email = (TextView) findViewById(R.id.txt_email);
        font_mobile = (TextView) findViewById(R.id.txt_mobile);

        email = (EditText) findViewById(R.id.edit_email);
        phone = (EditText) findViewById(R.id.edit_mobile);

        //setting values to edittext from shared preference
        email.setText(useremail);
        phone.setText("87678879865");

        font_toolbar_title.setTypeface(typeface);
        font_toolbar_title.setText("Personal Information");
        font_email.setTypeface(typeface);
        font_mobile.setTypeface(typeface);
    }

}
