package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;


import static com.example.tvd.customer_info.values.ConstantValues.CONNECTION_TIME_OUT;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_SUCCESS;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText email, password;
    String get_email = "", get_password = "", TokenId = "";
    ProgressDialog progressdialog;
    SendingData sendingdata;
    FunctionCall fcall;
    LayoutInflater inflater;
    View layout;
    GetSetValues getSetValues;
    Typeface typeface;
    TextView app_name,sign_up;

    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LOGIN_SUCCESS:
                        SavePreferences("EMAIL", get_email);
                        SavePreferences("ID", getSetValues.getLogin_id());
                        progressdialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        //Below code is for custom toast message
                        inflater = getLayoutInflater();
                        layout = inflater.inflate(R.layout.toast1,
                                (ViewGroup) findViewById(R.id.toast_layout));
                        ImageView imageView = (ImageView) layout.findViewById(R.id.image);
                        imageView.setImageResource(R.drawable.tick);
                        TextView textView = (TextView) layout.findViewById(R.id.text);
                        textView.setTypeface(typeface);
                        textView.setText("Success");
                        textView.setTextSize(20);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //end of custom toast coding
                        break;
                    case LOGIN_FAILURE:
                        progressdialog.dismiss();
                        //below code is for custom toast
                        inflater = getLayoutInflater();
                        layout = inflater.inflate(R.layout.toast,
                                (ViewGroup) findViewById(R.id.toast_layout));
                        ImageView imageView1 = (ImageView) layout.findViewById(R.id.image);
                        imageView1.setImageResource(R.drawable.invalid);
                        TextView textView1 = (TextView) layout.findViewById(R.id.text);
                        textView1.setTypeface(typeface);
                        textView1.setText("Invalid Credentials!!");
                        textView1.setTextSize(20);
                        Toast toast1 = new Toast(getApplicationContext());
                        toast1.setGravity(Gravity.BOTTOM, 0, 0);
                        toast1.setDuration(Toast.LENGTH_SHORT);
                        toast1.setView(layout);
                        toast1.show();
                        //end of custom toast code
                        email.setText("");
                        password.setText("");
                        email.requestFocus();
                        break;
                    case CONNECTION_TIME_OUT:
                        progressdialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Connection Time out!!", Toast.LENGTH_SHORT).show();
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
        initialize();
        getSetValues = new GetSetValues();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
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
                        /*progressdialog = ProgressDialog.show(LoginActivity.this, "Login",
                                "Fetching details please wait..", true);*/
                        progressdialog = new ProgressDialog(LoginActivity.this, R.style.MyProgressDialogstyle);
                        progressdialog.setTitle("Connecting To Server");
                        progressdialog.setMessage("Please Wait..");
                        progressdialog.show();
                        // Creating user login session
                        // Statically storing name,password
                        /************For user login session**********/
                        //sessionManager.createUserLoginSession(get_email, get_password);
                        /********************************************/

                        SendingData.Login login = sendingdata.new Login(mHandler, getSetValues);
                        login.execute(get_email, get_password, TokenId);
                    }
                } else
                    Toast.makeText(LoginActivity.this, "Please connect to internet..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initialize() {
        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        sendingdata = new SendingData();
        fcall = new FunctionCall();
        login = (Button) findViewById(R.id.login_btn);
        app_name = (TextView) findViewById(R.id.txt_app_name);
        email = (EditText) findViewById(R.id.edit_email);
        password = (EditText) findViewById(R.id.edit_password);
        app_name.setTypeface(typeface);
    }

    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
