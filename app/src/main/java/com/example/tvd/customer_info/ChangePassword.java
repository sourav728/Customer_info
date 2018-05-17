package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvd.customer_info.invoke.SendingData;

import static com.example.tvd.customer_info.values.ConstantValues.PASSWORD_CHANGED_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.PASSWORD_CHANGED_SUCCESS;

public class ChangePassword extends AppCompatActivity {
    EditText old_pass, new_pass;
    Button send;
    String Token_Id = "0x9851FFA7317D3E4F191A969454138816104173F9";
    SendingData sendingdata;
    ProgressDialog progressdialog;
    String login_id = "";
    FloatingActionButton back;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PASSWORD_CHANGED_SUCCESS:
                        progressdialog.dismiss();
                        Toast.makeText(ChangePassword.this, "Password change Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePassword.this, StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                    case PASSWORD_CHANGED_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(ChangePassword.this, "Password Change Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        login_id = sharedPreferences.getString("ID", "");
        initialize();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(old_pass.getText()) && !TextUtils.isEmpty(new_pass.getText())) {
                    progressdialog = new ProgressDialog(ChangePassword.this, R.style.MyProgressDialogstyle);
                    progressdialog.setTitle("Connecting To Server");
                    progressdialog.setMessage("Please Wait..");
                    progressdialog.show();
                    String old_password = old_pass.getText().toString();
                    String new_password = new_pass.getText().toString();
                    SendingData.PasswordReset password_reset = sendingdata.new PasswordReset(mHandler);
                    password_reset.execute(login_id, old_password, new_password, Token_Id);
                } else
                    Toast.makeText(ChangePassword.this, "Please Enter old and new password!!", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initialize() {
        old_pass = (EditText) findViewById(R.id.edit_old_password);
        new_pass = (EditText) findViewById(R.id.edit_new_password);
        send = (Button) findViewById(R.id.send_btn);
        sendingdata = new SendingData();
    }
}
