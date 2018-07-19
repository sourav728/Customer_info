package com.example.tvd.customer_info;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.example.tvd.customer_info.values.ConstantValues.CONNECTION_TIME_OUT;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_SUCCESS;

public class StartActivity extends AppCompatActivity {
    CardView cv;
    FloatingActionButton fab;
    Button login;
    EditText password;
    AutoCompleteTextView email;
    String get_email = "", get_password = "", TokenId = "", emaidId = "";
    ProgressDialog progressdialog;
    SendingData sendingdata;
    FunctionCall fcall;
    LayoutInflater inflater;
    View layout;
    GetSetValues getSetValues;
    Typeface typeface;
    TextView forgot_password;


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
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
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
                        Toast.makeText(StartActivity.this, "Server Is Busy Please try again later!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //todo For Handling Softkeys
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initialize();
        getSetValues = new GetSetValues();
        //Full screen code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        //End
        TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fcall.isInternetOn(StartActivity.this)) {
                    //todo Below code is for storing userid in shared preferences
                    emaidId = email.getText().toString().trim();
                    SharedPreferences ss = getSharedPreferences("loginSession_key", 0);
                    Set<String> hs = ss.getStringSet("set", new HashSet<String>());
                    hs.add(emaidId);
                    SharedPreferences.Editor edit = ss.edit();
                    edit.clear();
                    edit.putStringSet("set", hs);
                    edit.commit();
                    //todo End of this

                    get_email = email.getText().toString();
                    get_password = password.getText().toString();
                    if (TextUtils.isEmpty(email.getText())) {
                        email.setError("Please Enter Email!!");
                    } else if (TextUtils.isEmpty(password.getText())) {
                        password.setError("Please Enter Password!!");
                    } else {
                        progressdialog = new ProgressDialog(StartActivity.this, R.style.MyProgressDialogstyle);
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
                    Toast.makeText(StartActivity.this, "Please connect to internet..", Toast.LENGTH_SHORT).show();
            }
        });
        setListener();
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListener() {

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(StartActivity.this, RegisterActivity.class), options.toBundle());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);

        // todo get Session cache value in login box
        SharedPreferences sss = getSharedPreferences("loginSession_key", 0);
        Log.d("debug", "2.set = " + sss.getStringSet("set", new HashSet<String>()));

        Log.d("debug", "LoginSession ->" + sss.getStringSet("set", new HashSet<String>()));

        ArrayList<String> al = new ArrayList<>();
        al.addAll(sss.getStringSet("set", new HashSet<String>()));

        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, al);
        //Getting the instance of AutoCompleteTextView
        email.setThreshold(1);//will start working from first character
        email.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

    public void initialize() {
        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        cv = (CardView) findViewById(R.id.cv);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        sendingdata = new SendingData();
        fcall = new FunctionCall();
        login = (Button) findViewById(R.id.login_btn);
        email = (AutoCompleteTextView) findViewById(R.id.edit_email);
        password = (EditText) findViewById(R.id.edit_password);
        forgot_password = (TextView) findViewById(R.id.txt_forgot_password);

    }

    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


}
