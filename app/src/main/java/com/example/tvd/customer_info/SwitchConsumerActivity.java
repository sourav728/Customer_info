package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.adapter.ConsumerListAdapter;
import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.GetSetValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_DEACTIVATED_SUCCESSFULLY;
import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_DEACTIVATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.DEACTIVATE_ACCOUNT;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_SUCCESS;

public class SwitchConsumerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<GetSetValues> arrayList;
    GetSetValues getSetValues;
    private ConsumerListAdapter consumerListAdapter;
    String TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";
    String Consumer_id = "", rrno = "", relationship = "", login_id = "";
    TextView font_toolbar_text;
    Typeface typeface;
    SendingData sendingdata;
    ProgressDialog progressdialog;
    Context context;
    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SWITCH_CONSUMER_SUCCESS:
                        progressdialog.dismiss();
                        Toast.makeText(SwitchConsumerActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        break;
                    case SWITCH_CONSUMER_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(SwitchConsumerActivity.this, "No Consumer ID Added!!", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case ACCOUNT_DEACTIVATED_SUCCESSFULLY:
                        progressdialog.dismiss();
                        Toast.makeText(SwitchConsumerActivity.this, "Account Deactivated..", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("SWITCH_CONSUMER_ID", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        break;
                    case ACCOUNT_DEACTIVATION_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(SwitchConsumerActivity.this, "Account Deactivation Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_consumer);
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String language = sharedPreferences.getString("LANGUAGE", "");
        initialize();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressdialog = new ProgressDialog(this, R.style.MyProgressDialogstyle);
        progressdialog.setTitle("Connecting To Server");
        progressdialog.setMessage("Please Wait..");
        progressdialog.show();

        SendingData.See_consumer_Details see_consumer_details = sendingdata.new See_consumer_Details(mHandler, getSetValues, arrayList, consumerListAdapter);
        see_consumer_details.execute(login_id, TokenId);
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
        sendingdata = new SendingData();
        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        font_toolbar_text = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSetValues = new GetSetValues();

        recyclerView = (RecyclerView) findViewById(R.id.consumer_recycler_view);
        arrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.consumer_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        consumerListAdapter = new ConsumerListAdapter(arrayList, this, getSetValues);
        recyclerView.setAdapter(consumerListAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        login_id = sharedPreferences.getString("ID", "");
        font_toolbar_text.setTypeface(typeface);
       // font_toolbar_text.setText("ConsumerLists");
    }

    public void show_deactivate_dialog(int id, int position, ArrayList<GetSetValues> arrayList) {
        final AlertDialog alertDialog;
        final GetSetValues getSetValues = arrayList.get(position);
        switch (id) {
            case DEACTIVATE_ACCOUNT:
                AlertDialog.Builder deactivate = new AlertDialog.Builder(this);
                deactivate.setTitle("Deactivate ID");
                deactivate.setCancelable(false);
                RelativeLayout deactivate_layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.deactivate_layout, null);
                deactivate.setView(deactivate_layout);
                TextView consumerid = (TextView) deactivate_layout.findViewById(R.id.txt_consumer_id1);
                TextView rrno = (TextView) deactivate_layout.findViewById(R.id.txt_rrno1);
                Button deactivate_btn = (Button) deactivate_layout.findViewById(R.id.dialog_positive_btn);
                deactivate_btn.setText("Deactivate");
                Button cancel_btn = (Button) deactivate_layout.findViewById(R.id.dialog_negative_btn);
                consumerid.setText(getSetValues.getConsumer_id());
                rrno.setText(getSetValues.getRrno());
                alertDialog = deactivate.create();
                deactivate_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressdialog = new ProgressDialog(SwitchConsumerActivity.this, R.style.MyProgressDialogstyle);
                        progressdialog.setTitle("Connecting To Server");
                        progressdialog.setMessage("Please Wait..");
                        progressdialog.show();
                        SendingData.Deactivate_ID deactivate_id = sendingdata.new Deactivate_ID(mHandler);
                        deactivate_id.execute(getSetValues.getConsumer_id(), login_id, TokenId);
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
    }

    public void swap_Account(int position, ArrayList<GetSetValues> arrayList) {
        final GetSetValues getSetValues = arrayList.get(position);
        SavePreferences("Curr_Cons_ID", getSetValues.getConsumer_id());
        SavePreferences("Curr_position", position + "");
        Intent intent = new Intent(SwitchConsumerActivity.this, MainActivity.class);
        //below code will remove all the other activites on top
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        //here to set click value

    }

    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("SWITCH_CONSUMER_ID", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(SwitchConsumerActivity.this, languageCode);
        Resources resources = context.getResources();
        font_toolbar_text.setText(resources.getString(R.string.switchconsumer));
    }
}
