package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.receiver.NetworkChangeReceiver;
import com.example.tvd.customer_info.values.GetSetValues;

import static com.example.tvd.customer_info.values.ConstantValues.INSERTION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.INSERTION_SUCCESSFULL;
import static com.example.tvd.customer_info.values.ConstantValues.SEARCH_FOUND;
import static com.example.tvd.customer_info.values.ConstantValues.SEARCH_NOT_FOUND;

public class AccountRegistrationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Typeface typeface;
    TextView font_toolbar_title, font_acount, font_rrno;
    Button search,add;
    EditText account_id, rrno;
    SendingData sendingData;
    ProgressDialog progressDialog;
    String TokenId = "", get_account_id = "", get_rrno = "",login_id="";
    LinearLayout show_hide;
    FrameLayout frame_show_hide;
    GetSetValues getsetvalues;
    static TextView tv_check_connection;
    private BroadcastReceiver mNetworkReceiver;
    private TextView connection_load,connection_type,type_of_supply,tariff_plan,installed_date,address,email,subdivision,cons_name;
    private TextView font_personal_details,font_dubdiv_details,font_connection_details,font_connection_load,font_connection_type,
                     font_type_of_supply,font_tariff_plan,font_installed_date;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SEARCH_FOUND:
                        progressDialog.dismiss();
                        Toast.makeText(AccountRegistrationActivity.this, "Search Found", Toast.LENGTH_SHORT).show();
                        show_hide.setVisibility(View.VISIBLE);
                        frame_show_hide.setVisibility(View.VISIBLE);
                        setTextView_values();
                        break;
                    case SEARCH_NOT_FOUND:
                        progressDialog.dismiss();
                        Toast.makeText(AccountRegistrationActivity.this, "Search Not Found!!", Toast.LENGTH_SHORT).show();
                        account_id.setText("");
                        rrno.setText("");
                        account_id.requestFocus();
                        show_hide.setVisibility(View.GONE);
                        frame_show_hide.setVisibility(View.GONE);
                        break;
                    case INSERTION_SUCCESSFULL:
                        progressDialog.dismiss();
                        Toast.makeText(AccountRegistrationActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case INSERTION_FAILURE:
                        progressDialog.dismiss();
                        Toast.makeText(AccountRegistrationActivity.this, "Insertion Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_registration);

        mNetworkReceiver = new NetworkChangeReceiver();
        getsetvalues = new GetSetValues();
        registerNetworkBroadcastForNougat();
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF",MODE_PRIVATE);
        login_id = sharedPreferences.getString("ID","");
        String language = sharedPreferences.getString("LANGUAGE", "");

        Log.d("Debug","Login ID"+login_id);
        TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";
        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        initialize();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_account_id = account_id.getText().toString();
                get_rrno = rrno.getText().toString();
               /* progressDialog = ProgressDialog.show(AccountRegistrationActivity.this, "Searching..",
                        "Fetching details please wait..", true);*/
                progressDialog = new ProgressDialog(AccountRegistrationActivity.this, R.style.MyProgressDialogstyle);
                progressDialog.setTitle("Connecting To Server");
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();

                SendingData.Customer_Search customer_search = sendingData.new Customer_Search(mHandler,getsetvalues);
                customer_search.execute(get_account_id, get_rrno, TokenId);
               // setTextView_values();
            }
        });
        //setting values into textviews

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendingData.Customer_Data_Insert customer_data_insert = sendingData.new Customer_Data_Insert(mHandler,getsetvalues);
                customer_data_insert.execute(login_id,get_account_id,get_rrno,"",TokenId);
            }
        });

        //below code is for loading different font
        if (!language.equals(""))
        {
            if (language.equals("KN"))
            {
                updateViews("KN");
            }
            else if (language.equals("en"))
            {
                updateViews("en");
            }
        }
    }

    public void initialize() {

        sendingData = new SendingData();
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        font_acount = (TextView) findViewById(R.id.txt_accountid);
        font_rrno = (TextView) findViewById(R.id.txt_rrno);
        search = (Button) findViewById(R.id.btn_search);
        add = (Button) findViewById(R.id.btn_add);
        account_id = (EditText) findViewById(R.id.edit_account_id);
        rrno = (EditText) findViewById(R.id.edit_rrno);
        show_hide = (LinearLayout) findViewById(R.id.lin_search_details);
        frame_show_hide = (FrameLayout) findViewById(R.id.frame_layout_show_hide);
      //  tv_check_connection = (TextView) findViewById(R.id.tv_check_connection);

        font_acount.setTypeface(typeface);
        font_rrno.setTypeface(typeface);
        font_toolbar_title.setTypeface(typeface);
        //font_toolbar_title.setText("Account Registration");
        toolbar.setNavigationIcon(R.drawable.back);
        search.setTypeface(typeface);

        //All textviews initialization for setting customer details
        connection_load = (TextView) findViewById(R.id.txt_connection_load);
        connection_type = (TextView) findViewById(R.id.txt_connection_type);
        type_of_supply = (TextView) findViewById(R.id.txt_type_of_supply);
        tariff_plan = (TextView) findViewById(R.id.txt_tariff_plan);
        installed_date = (TextView) findViewById(R.id.txt_installed_date);
        address = (TextView) findViewById(R.id.txt_address);
        email = (TextView) findViewById(R.id.txt_email);
        subdivision = (TextView) findViewById(R.id.txt_subdiv);
        cons_name = (TextView) findViewById(R.id.txt_name);
        //All Textviews for setting font to textview label
        font_personal_details = (TextView) findViewById(R.id.font_personal_details);
        font_dubdiv_details = (TextView) findViewById(R.id.font_dubdiv_details);
        font_connection_details = (TextView) findViewById(R.id.font_connection_details);
        font_connection_load = (TextView) findViewById(R.id.font_connection_load);
        font_type_of_supply = (TextView) findViewById(R.id.font_type_of_supply);
        font_tariff_plan = (TextView) findViewById(R.id.font_tariff_plan);
        font_installed_date = (TextView) findViewById(R.id.font_installed_date);
        //setting setTypeface
        font_personal_details.setTypeface(typeface);
        font_dubdiv_details.setTypeface(typeface);
        font_connection_details.setTypeface(typeface);
        font_connection_load.setTypeface(typeface);

        font_type_of_supply.setTypeface(typeface);
        font_tariff_plan.setTypeface(typeface);
        font_installed_date.setTypeface(typeface);
    }
    public void setTextView_values()
    {
        cons_name.setText(getsetvalues.getCons_name());
        Log.d("Debug","Cons_name"+cons_name);
        connection_load.setText(getsetvalues.getCons_kwhp());
        Log.d("Debug","connection load"+connection_load);
        address.setText(getsetvalues.getCons_address());
        email.setText(getsetvalues.getCons_email());
        subdivision.setText(getsetvalues.getCons_subdivision());
        tariff_plan.setText(getsetvalues.getCons_tariff());
        installed_date.setText(getsetvalues.getCons_date_of_service());
    }
   /* public static void dialog(boolean value){
        if(value){
            tv_check_connection.setText("Back Online");
            tv_check_connection.setBackgroundColor(Color.parseColor("#558B2F"));
            tv_check_connection.setTextColor(Color.WHITE);
            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    tv_check_connection.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else {
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText("No Internet Connection!!");
            tv_check_connection.setBackgroundColor(Color.RED);
            tv_check_connection.setTextColor(Color.WHITE);
        }
    }*/

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(AccountRegistrationActivity.this, languageCode);
        Resources resources = context.getResources();
        font_toolbar_title.setText(resources.getString(R.string.account_registration));
    }

}
