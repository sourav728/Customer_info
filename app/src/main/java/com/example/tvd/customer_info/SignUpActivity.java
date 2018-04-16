package com.example.tvd.customer_info;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    Button signup;
    TextView signup_text,login_text;
    EditText name,email,phonenumber,password;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        signup = (Button) findViewById(R.id.signup_btn);
        signup_text = (TextView) findViewById(R.id.signup);
        login_text = (TextView) findViewById(R.id.login);

        name = (EditText) findViewById(R.id.edit_name);
        email = (EditText) findViewById(R.id.edit_email);
        phonenumber = (EditText) findViewById(R.id.edit_phonenumber);
        password = (EditText) findViewById(R.id.edit_password);

        sharedPreferences = getApplicationContext().getSharedPreferences("Reg",0);
        editor = sharedPreferences.edit();

        signup.setOnClickListener(this);
        signup_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signup_btn:
               /* Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);*/
                 register_user();
                break;
            case R.id.signup:
                break;
            case R.id.login:
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void register_user()
    {
        String cust_name = name.getText().toString();
        String cust_email = email.getText().toString();
        String cust_phone = phonenumber.getText().toString();
        String cust_pass = password.getText().toString();
        if (name.getText().length()<=0)
        {
            name.setError("Enter Name");
        }
        else if (email.getText().length()<=0)
        {
            email.setError("Enter Email");
        }
        else if (phonenumber.getText().length()<=0)
        {
            phonenumber.setError("Enter phone");
        }
        else if (password.getText().length()<=0)
        {
            password.setError("Enter password");
        }
        else
        {
            editor.putString("Name",cust_name);
            editor.putString("Email",cust_email);
            editor.putString("Phone",cust_phone);
            editor.putString("Password",cust_pass);
            editor.commit();
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
