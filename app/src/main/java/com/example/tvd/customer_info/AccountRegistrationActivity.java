package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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

import com.example.tvd.customer_info.invoke.SendingData;
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
        getsetvalues = new GetSetValues();

        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF",MODE_PRIVATE);
        login_id = sharedPreferences.getString("ID","");
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

                SendingData.Customer_Search customer_search = sendingData.new Customer_Search(mHandler);
                customer_search.execute(get_account_id, get_rrno, TokenId);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendingData.Customer_Data_Insert customer_data_insert = sendingData.new Customer_Data_Insert(mHandler);
                customer_data_insert.execute(login_id,get_account_id,get_rrno,"",TokenId);
            }
        });
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

        font_acount.setTypeface(typeface);
        font_rrno.setTypeface(typeface);
        font_toolbar_title.setTypeface(typeface);
        font_toolbar_title.setText("Account Registration");
        toolbar.setNavigationIcon(R.drawable.back);
        search.setTypeface(typeface);
    }
}
