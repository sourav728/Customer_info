package com.example.tvd.customer_info.invoke;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.tvd.customer_info.LoginActivity;
import com.example.tvd.customer_info.MainActivity;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class SendingData {
    private ReceivingData receivingData = new ReceivingData();

    private String UrlPostConnection(String Post_Url, HashMap<String, String> datamap) throws IOException {
        String response = "";
        URL url = new URL(Post_Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(getPostDataString(datamap));
        writer.flush();
        writer.close();
        os.close();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
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
        }

        return result.toString();
    }

    //For Registration
    public class Sign_UP extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;

        public Sign_UP(Handler handler)
        {
            this.handler = handler;
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("Name",params[0]);
            datamap.put("Email",params[1]);
            datamap.put("PhoneNo",params[2]);
            datamap.put("Password",params[3]);
            datamap.put("TokenId",params[4]);
            try {
                response = UrlPostConnection("http://www.bc_service.hescomtrm.com/CUSTINFOSERVICE.asmx/CustomerRegistration", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
           super.onPostExecute(result);
           receivingData.get_Registration_Status(result, handler);
        }
    }

    //For Login
    public class Login extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;
        public Login(Handler handler)
        {
            this.handler = handler;
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("Email", params[0]);
            datamap.put("UserPassword", params[1]);
            datamap.put("TokenId", params[2]);
            try {
                response = UrlPostConnection("http://www.bc_service.hescomtrm.com/CUSTINFOSERVICE.asmx/CustomerLogin", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.get_Login_Status(result, handler);
        }
    }

}
