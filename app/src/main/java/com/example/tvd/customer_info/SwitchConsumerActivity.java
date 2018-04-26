package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.adapter.ConsumerListAdapter;
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

import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_SUCCESS;

public class SwitchConsumerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<GetSetValues>arrayList;
    GetSetValues getSetValues;
    private ConsumerListAdapter consumerListAdapter;
    String TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";
    String Consumer_id="",rrno="",relationship="",login_id="";
    TextView font_toolbar_text;
    Typeface typeface;
    SendingData sendingdata;
    ProgressDialog progressdialog;
    private final Handler mHandler;
    {
        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case SWITCH_CONSUMER_SUCCESS:
                        progressdialog.dismiss();
                        Toast.makeText(SwitchConsumerActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        break;
                    case SWITCH_CONSUMER_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(SwitchConsumerActivity.this, "Failure!!", Toast.LENGTH_SHORT).show();
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
        initialize();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*Consumer_list consumer_list = new Consumer_list();
        consumer_list.execute();*/
        /*progressdialog = ProgressDialog.show(SwitchConsumerActivity.this, "Connecting TO Server",
                "Fetching details please wait..", true);*/
        progressdialog = new ProgressDialog(this, R.style.MyProgressDialogstyle);
        progressdialog.setTitle("Connecting To Server");
        progressdialog.setMessage("Please Wait..");
        progressdialog.show();

        SendingData.See_consumer_Details see_consumer_details = sendingdata.new See_consumer_Details(mHandler,getSetValues,arrayList,consumerListAdapter);
        see_consumer_details.execute(login_id,TokenId);
    }
    public void initialize()
    {
        sendingdata = new SendingData();
        typeface = Typeface.createFromAsset(getAssets(),"timesnewroman.ttf");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        font_toolbar_text = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSetValues = new GetSetValues();
        recyclerView = (RecyclerView) findViewById(R.id.consumer_recycler_view);
        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.consumer_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        consumerListAdapter = new ConsumerListAdapter(this, arrayList, getSetValues);
        recyclerView.setAdapter(consumerListAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF",MODE_PRIVATE);
        login_id = sharedPreferences.getString("ID","");
        font_toolbar_text.setTypeface(typeface);
        font_toolbar_text.setText("ConsumerLists");
    }

}
