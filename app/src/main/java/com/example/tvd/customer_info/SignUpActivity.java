package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.FunctionCall;

import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_SUCCESS;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Button signup;
    TextView signup_text, login_text,app_name;
    EditText name, email, phonenumber, password;
    SendingData sendingData;
    String TokenId = "", cust_name = "", cust_email = "", cust_phone = "", cust_pass = "";
    ProgressDialog progressdialog;
    FunctionCall fcall;
    Typeface typeface;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REGISTRATION_SUCCESS:
                        progressdialog.dismiss();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case REGISTRATION_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Please Check the Registration details..", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        email.setText("");
                        phonenumber.setText("");
                        password.setText("");
                        name.requestFocus();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initialize();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        signup.setOnClickListener(this);
        signup_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
        TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";
    }

    public void initialize()
    {
        typeface = Typeface.createFromAsset(getAssets(),"timesnewroman.ttf");
        signup = (Button) findViewById(R.id.signup_btn);
        signup_text = (TextView) findViewById(R.id.signup);
        login_text = (TextView) findViewById(R.id.login);
        app_name = (TextView) findViewById(R.id.txt_app_name);
        name = (EditText) findViewById(R.id.edit_name);
        email = (EditText) findViewById(R.id.edit_email);
        phonenumber = (EditText) findViewById(R.id.edit_phonenumber);
        password = (EditText) findViewById(R.id.edit_password);
        sendingData = new SendingData();
        fcall = new FunctionCall();
        app_name.setTypeface(typeface);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_btn:
                if (fcall.isInternetOn(SignUpActivity.this))
                    register_user();
                else
                    Toast.makeText(this, "Please connect to internet..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signup:
                break;
            case R.id.login:
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void register_user() {
        cust_name = name.getText().toString();
        cust_email = email.getText().toString();
        cust_phone = phonenumber.getText().toString();
        cust_pass = password.getText().toString();
        if (name.getText().length() <= 0) {
            name.setError("Enter Name");
        } else if (email.getText().length() <= 0) {
            email.setError("Enter Email");
        } else if (phonenumber.getText().length() <= 0) {
            phonenumber.setError("Enter phone");
        } else if (password.getText().length() <= 0) {
            password.setError("Enter password");
        } else {
            //Below code for email and mobile no validation
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(cust_email).matches()) {
                if (android.util.Patterns.PHONE.matcher(cust_phone).matches()) {
                    /*progressdialog = ProgressDialog.show(SignUpActivity.this, "Registration",
                            "Fetching details please wait..", true);*/
                    progressdialog = new ProgressDialog(SignUpActivity.this, R.style.MyProgressDialogstyle);
                    progressdialog.setTitle("Connecting To Server");
                    progressdialog.setMessage("Please Wait..");
                    progressdialog.show();

                    SendingData.Sign_UP signup = sendingData.new Sign_UP(mHandler);
                    signup.execute(cust_name, cust_email, cust_phone, cust_pass, TokenId);
                }else Toast.makeText(this, "Phone number is not valid!!", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Email is not valid!!", Toast.LENGTH_SHORT).show();
        }
    }
}
