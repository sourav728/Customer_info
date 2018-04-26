package com.example.tvd.customer_info;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
        Consumer_list consumer_list = new Consumer_list();
        consumer_list.execute();
    }
    public void initialize()
    {
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
    public class Consumer_list extends AsyncTask<String,String,String>
    {
        String response = "";
        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String>datamap = new HashMap<>();
            datamap.put("Userid",login_id);
            datamap.put("TokenId",TokenId);
            try
            {
                response = UrlPostConnection("http://www.bc_service.hescomtrm.com/CUSTINFOSERVICE.asmx/CustomerDetails",datamap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            String res = parseServerXML(s);
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(res);
                if (jsonArray.length()>0)
                {
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        getSetValues = new GetSetValues();
                        Consumer_id = jsonObject.getString("ACCOUNT_ID");
                        rrno = jsonObject.getString("RRNO");
                        relationship = jsonObject.getString("RELATIONSHIP");
                        Log.d("Debug","CONSUMERID"+Consumer_id);
                        Log.d("Debug","RRNO"+rrno);
                        Log.d("Debug","Relationship"+relationship);
                        if (!Consumer_id.equals(""))
                            getSetValues.setConsumer_id(Consumer_id);
                        else getSetValues.setConsumer_id("NA");
                        if (!rrno.equals(""))
                            getSetValues.setRrno(rrno);
                        else getSetValues.setRrno("NA");
                        if (!relationship.equals(""))
                            getSetValues.setRelationship(relationship);
                        else getSetValues.setRelationship("NA");
                        arrayList.add(getSetValues);
                        consumerListAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(SwitchConsumerActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(SwitchConsumerActivity.this, "No Data!!", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(SwitchConsumerActivity.this, "No Data found!!", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }

    private String UrlPostConnection(String Post_Url, HashMap<String, String> datamap) throws IOException {
        String response = "";
        URL url = new URL(Post_Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        OutputStream outputStream = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.write(getPostDataString(datamap));
        writer.flush();
        writer.close();
        outputStream.close();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
        } else {
            response = "";
        }
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            Log.d("debug", result.toString());
        }
        return result.toString();
    }

    public String parseServerXML(String result) {
        String value = "";
        XmlPullParserFactory pullParserFactory;
        InputStream res;
        try {
            res = new ByteArrayInputStream(result.getBytes());
            pullParserFactory = XmlPullParserFactory.newInstance();
            pullParserFactory.setNamespaceAware(true);
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(res, null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        switch (name) {
                            case "string":
                                value = parser.nextText();
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
