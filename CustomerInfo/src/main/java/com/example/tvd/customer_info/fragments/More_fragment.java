package com.example.tvd.customer_info.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.Billing_Collection_Tabbed_Activity;
import com.example.tvd.customer_info.ChangePassword;
import com.example.tvd.customer_info.ComplaintRegistration_TabbedActivity;
import com.example.tvd.customer_info.FeedbackActivity;
import com.example.tvd.customer_info.HelpActivity;
import com.example.tvd.customer_info.Location;
import com.example.tvd.customer_info.MainActivity;
import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.values.GetSetValues;

import org.json.JSONArray;
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

import static android.content.Context.MODE_PRIVATE;

public class More_fragment extends Fragment {
    public static final String GETSET = "getset";
    RelativeLayout language, location,complaints,change_pass, feedback, help, billing_collection;
    AlertDialog alertDialog1;
    GetSetValues getSetValues;
    CharSequence[] values = {"English", "Kannada"};
    String LONGITUDE = "", LATITUDE = "",CSDNAME="";
    ArrayList<GetSetValues> arrayList;
    String TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";
    TextView changelanguage,txt_location,txt_complaint,txt_change_pass,txt_feedback,txt_help,txt_bill_coll;
    Intent intent;
    String current_con_id="";
    public More_fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_fragment, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String language2 = sharedPreferences.getString("LANGUAGE", "");

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("SWITCH_CONSUMER_ID", MODE_PRIVATE);
        current_con_id = sharedPreferences1.getString("Curr_Cons_ID", "");

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        language = (RelativeLayout) view.findViewById(R.id.relative_language);
        location = (RelativeLayout) view.findViewById(R.id.relative_location);
        complaints = (RelativeLayout) view.findViewById(R.id.relative_complaints);
        change_pass = (RelativeLayout) view.findViewById(R.id.relative_changepassword);
        feedback = (RelativeLayout) view.findViewById(R.id.relative_feedback);
        help = (RelativeLayout) view.findViewById(R.id.relative_help);
        billing_collection = (RelativeLayout) view.findViewById(R.id.relative_billing_collection);

        arrayList = new ArrayList<>();

        changelanguage = (TextView) view.findViewById(R.id.txt_language);
        txt_location = (TextView) view.findViewById(R.id.txt_location);
        txt_complaint = (TextView) view.findViewById(R.id.txt_complaint_registration);
        txt_change_pass = (TextView) view.findViewById(R.id.txt_change_pass);
        txt_feedback = (TextView) view.findViewById(R.id.txt_feedback);
        txt_help = (TextView) view.findViewById(R.id.txt_help);
        txt_bill_coll = (TextView) view.findViewById(R.id.txt_billing_collection);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialogWithRadioButtonGroup();
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectURL connectURL = new ConnectURL();
                connectURL.execute();
            }
        });
        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), ComplaintRegistration_TabbedActivity.class);
               startActivity(intent);

            }
        });
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), HelpActivity.class);
               startActivity(intent);
            }
        });
        billing_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!current_con_id.equals(""))
                {
                    Intent intent = new Intent(getActivity(), Billing_Collection_Tabbed_Activity.class);
                    startActivity(intent);
                }else Toast.makeText(getActivity(), "Please Select Consumer ID!!", Toast.LENGTH_SHORT).show();
            }
        });


        //below code is for loading different font
        if (!language2.equals(""))
        {
            if (language2.equals("KN"))
            {
                updateViews("KN");
            }
            else if (language2.equals("en"))
            {
                updateViews("en");
            }
            else if (language2.equals(""))
            {
                updateViews("NA");
            }
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_more, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void CreateAlertDialogWithRadioButtonGroup() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Your Choice");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        Toast.makeText(getActivity(), "English font applied", Toast.LENGTH_LONG).show();
                        SavePreferences("LANGUAGE","en");
                       // updateViews("en");
                        intent = new Intent(getActivity(), MainActivity.class);
                        //below code will remove all the other activites on top
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Kannada font applied", Toast.LENGTH_LONG).show();
                        SavePreferences("LANGUAGE","KN");
                       // updateViews("KN");
                        intent = new Intent(getActivity(), MainActivity.class);
                        //below code will remove all the other activites on top
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    public class ConnectURL extends AsyncTask<String, String, String> {
        String response = "";

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("subdivisioncode", "540038");
            datamap.put("TokenId", TokenId);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/SubdivisionLocation", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            String res = parseServerXML(s);
            JSONArray jsonarray;
            try {
                jsonarray = new JSONArray(res);
                if (jsonarray.length() > 0) {
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonObject = jsonarray.getJSONObject(i);
                        LATITUDE = jsonObject.getString("LATITUDE");
                        LONGITUDE = jsonObject.getString("LONGITUDE");
                        CSDNAME = jsonObject.getString("CSDNAME");
                        Log.d("Debugg", "LATITUDE" + LATITUDE);
                        Log.d("Debugg", "LONGITUDE" + LONGITUDE);
                        Log.d("Debug", "CSDNAME"+ CSDNAME);

                    }
                    Intent intent = new Intent(getActivity(), Location.class);
                    intent.putExtra("LATITUDE", LATITUDE);
                    intent.putExtra("LONGITUDE", LONGITUDE);
                    intent.putExtra("CSDNAME", CSDNAME);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Success..", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                Toast.makeText(getActivity(), "No Values found!!", Toast.LENGTH_SHORT).show();
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

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(getActivity(), languageCode);
        Resources resources = context.getResources();
        changelanguage.setText(resources.getString(R.string.changelanguage));
        txt_location.setText(resources.getString(R.string.hescom_offices));
        txt_complaint.setText(resources.getString(R.string.complaint_registration));
        txt_change_pass.setText(resources.getString(R.string.changepassword));
        txt_feedback.setText(resources.getString(R.string.feedback));
        txt_help.setText(resources.getString(R.string.help));
        txt_bill_coll.setText(resources.getString(R.string.billingcollectionsummary));
    }
    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
