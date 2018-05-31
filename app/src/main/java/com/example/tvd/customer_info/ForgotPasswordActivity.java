package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.GetSetValues;

import static com.example.tvd.customer_info.values.ConstantValues.EMAIL_SEND_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.EMAIL_SEND_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.SMS_SEND_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.SMS_SEND_SUCCESS;

public class ForgotPasswordActivity extends AppCompatActivity {
    ProgressDialog progressdialog;
    SendingData sendingdata;
    EditText email, mobile;
    Button send;
    Boolean email_selected = false, mobile_selected = false;
    String TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";
    String getemail = "", getmobile = "";
    FloatingActionButton back;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SMS_SEND_SUCCESS:
                        progressdialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "SMS send Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this, StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                    case SMS_SEND_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "SMS send Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                    case EMAIL_SEND_SUCCESS:
                        progressdialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Email send Success", Toast.LENGTH_SHORT).show();
                        break;
                    case EMAIL_SEND_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Email send Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        //For Hiding softkeys
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        initialize();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getemail = email.getText().toString();
                getmobile = mobile.getText().toString();

                if (TextUtils.isEmpty(email.getText()) && TextUtils.isEmpty(mobile.getText())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter atleast one option!!", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(email.getText()) || !TextUtils.isEmpty(mobile.getText())) {
                    if (!TextUtils.isEmpty(email.getText())) {
                        Toast.makeText(ForgotPasswordActivity.this, "Email Selected..", Toast.LENGTH_SHORT).show();
                        progressdialog = new ProgressDialog(ForgotPasswordActivity.this, R.style.MyProgressDialogstyle);
                        progressdialog.setTitle("Sending EMAIL");
                        progressdialog.setMessage("Please Wait..");
                        progressdialog.show();
                        SendingData.SendEmail sendEmail = sendingdata.new SendEmail(mHandler);
                        sendEmail.execute(getemail, TokenId);
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Mobile Selected..", Toast.LENGTH_SHORT).show();
                        progressdialog = new ProgressDialog(ForgotPasswordActivity.this, R.style.MyProgressDialogstyle);
                        progressdialog.setTitle("Sending SMS");
                        progressdialog.setMessage("Please Wait..");
                        progressdialog.show();

                        SendingData.SendSMS sendSMS = sendingdata.new SendSMS(mHandler);
                        sendSMS.execute(getmobile, TokenId);
                    }
                } else
                    Toast.makeText(ForgotPasswordActivity.this, "Choose either anyone option!!", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, StartActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initialize() {
        sendingdata = new SendingData();
        email = (EditText) findViewById(R.id.edit_email);
        mobile = (EditText) findViewById(R.id.edit_mobile);
        send = (Button) findViewById(R.id.send_btn);
        back = (FloatingActionButton) findViewById(R.id.fab);
    }
}
