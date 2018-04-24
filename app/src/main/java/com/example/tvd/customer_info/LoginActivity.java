package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.FunctionCall;


import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_SUCCESS;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText email, password;
    String get_email = "", get_password = "", TokenId = "";
    ProgressDialog progressdialog;
    SendingData sendingdata;
    FunctionCall fcall;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LOGIN_SUCCESS:
                        progressdialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case LOGIN_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Please Check the login details..", Toast.LENGTH_SHORT).show();
                        email.setText("");
                        password.setText("");
                        email.requestFocus();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        sendingdata = new SendingData();
        fcall = new FunctionCall();
        login = (Button) findViewById(R.id.login_btn);
        email = (EditText) findViewById(R.id.edit_email);
        password = (EditText) findViewById(R.id.edit_password);
        TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fcall.isInternetOn(LoginActivity.this)) {
                    get_email = email.getText().toString();
                    get_password = password.getText().toString();
                    if (email.getText().length() <= 0) {
                        email.setError("Please Enter Email!!");
                    } else if (password.getText().length() <= 0) {
                        password.setError("Please Enter Password!!");
                    } else {

                        progressdialog = ProgressDialog.show(LoginActivity.this, "Login",
                                "Fetching details please wait..", true);
                        SendingData.Login login = sendingdata.new Login(mHandler);
                        login.execute(get_email, get_password, TokenId);
                    }
                } else
                    Toast.makeText(LoginActivity.this, "Please connect to internet..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
