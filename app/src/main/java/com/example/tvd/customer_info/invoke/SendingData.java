package com.example.tvd.customer_info.invoke;


import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;


import com.example.tvd.customer_info.adapter.ComplaintHistoryAdapter;
import com.example.tvd.customer_info.adapter.ConsumerListAdapter;
import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.example.tvd.customer_info.values.ConstantValues.CONNECTION_TIME_OUT;

public class SendingData {
    private ReceivingData receivingData = new ReceivingData();
    private FunctionCall fcall = new FunctionCall();
    private Handler handler;

    //In below code try catch has been added to check the response timeout
    private String UrlPostConnection(String Post_Url, HashMap<String, String> datamap) throws IOException {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Debug", "SERVER TIME OUT");
            //handler.sendEmptyMessage(CONNECTION_TIME_OUT);
        }
        return null;
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

        public Sign_UP(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("Name", params[0]);
            datamap.put("Email", params[1]);
            datamap.put("PhoneNo", params[2]);
            datamap.put("Password", params[3]);
            datamap.put("TokenId", params[4]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/CustomerRegistration", datamap);
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
        GetSetValues getSetValues;

        public Login(Handler handler, GetSetValues getSetValues) {
            this.handler = handler;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("Email", params[0]);
            datamap.put("UserPassword", params[1]);
            datamap.put("TokenId", params[2]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/CustomerLogin", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.get_Login_Status(result, handler, getSetValues);
        }
    }

    //For customer search
    public class Customer_Search extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;
        GetSetValues getSetValues;

        public Customer_Search(Handler handler, GetSetValues getSetValues) {
            this.handler = handler;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("AccountId", params[0]);
            datamap.put("RRNO", params[1]);
            datamap.put("TokenId", params[2]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/CustomerSearch", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.get_customersearch_info(result, handler, getSetValues);
        }
    }

    //For consumer data insert into web service
    public class Customer_Data_Insert extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;
        GetSetValues getSetValues;

        public Customer_Data_Insert(Handler handler, GetSetValues getSetValues) {
            this.handler = handler;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("Userid", params[0]);
            datamap.put("AccountId", params[1]);
            datamap.put("RRNO", params[2]);
            datamap.put("Relationship", params[3]);
            datamap.put("TokenId", params[4]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/CustomerDataInsert", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.get_consumerinsert_info(result, handler, getSetValues);
        }
    }

    //For sending Consumer id's related to one account
    public class See_consumer_Details extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;
        GetSetValues getSetValues;
        ArrayList<GetSetValues> arrayList;
        ConsumerListAdapter consumerListAdapter;

        public See_consumer_Details(Handler handler, GetSetValues getSetValues, ArrayList<GetSetValues> arrayList, ConsumerListAdapter consumerListAdapter) {
            this.handler = handler;
            this.getSetValues = getSetValues;
            this.arrayList = arrayList;
            this.consumerListAdapter = consumerListAdapter;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("Userid", params[0]);
            datamap.put("TokenId", params[1]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/CustomerDetails", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.get_consumer_list(result, handler, getSetValues, arrayList, consumerListAdapter);
        }
    }

    //For Account Deactivation
    public class Deactivate_ID extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;

        public Deactivate_ID(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("AccountId", params[0]);
            datamap.put("userid", params[1]);
            datamap.put("TokenId", params[2]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/AccountDisable", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.getDeactivate_Details(result, handler);
            super.onPostExecute(result);
        }
    }

    //For Sending data to view bill
    public class ViewBill extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;
        GetSetValues getSetValues;

        public ViewBill(Handler handler, GetSetValues getSetValues) {
            this.handler = handler;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("AccountId", params[0]);
            datamap.put("TokenId", params[1]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/BillDetails", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.getBillDetails(result, handler, getSetValues);
            super.onPostExecute(result);
        }
    }

    //For Sending Password into mobile
    public class SendSMS extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;

        public SendSMS(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("MobNo", params[0]);
            Log.d("Debug", "Mobile No" + params[0]);
            datamap.put("TokenId", params[1]);
            Log.d("Debug", "Token Id" + params[1]);

            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/ForgotPassWord", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.getSMS_details(result, handler);
            super.onPostExecute(result);
        }
    }

    //For Sending Email
    public class SendEmail extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;

        public SendEmail(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("Email", params[0]);
            datamap.put("TokenId", params[1]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/SendEmail", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.getEmail_Details(result, handler);
            super.onPostExecute(result);
        }
    }

    //For Password Reset
    public class PasswordReset extends AsyncTask<String, String, String> {
        String response = "";
        Handler handler;

        public PasswordReset(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("Userid", params[0]);
            datamap.put("OldPassword", params[1]);
            datamap.put("NewPassword", params[2]);
            datamap.put("TokenId", params[3]);
            try {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/CUSTINFOSERVICE.asmx/ChangePassword", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.get_Password_change_status(result, handler);
            super.onPostExecute(result);
        }
    }
    //Customer Search Based on CONSID
    public class CustomerSearch_CONSID extends AsyncTask<String,String,String>
    {
        String response = "";
        Handler handler;
        GetSetValues getSetValues;
        public CustomerSearch_CONSID(Handler handler, GetSetValues getSetValues)
        {
            this.handler = handler;
            this.getSetValues = getSetValues;
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String>datamap = new HashMap<>();
            datamap.put("CONSID",params[0]);
            try
            {
                response = UrlPostConnection("http://bc_service2.hescomtrm.com/Service.asmx/CustomerSearch",datamap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.get_customer_search_details_CONSID(result,handler, getSetValues);
            super.onPostExecute(result);
        }
    }

    //Customer Search Based on RRNO
    public class CustomerSearch_RRNO extends AsyncTask<String,String,String>
    {
        String response = "";
        Handler handler;
        GetSetValues getSetValues;
        public CustomerSearch_RRNO(Handler handler, GetSetValues getSetValues)
        {
            this.handler = handler;
            this.getSetValues = getSetValues;
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String>datamap = new HashMap<>();
            datamap.put("RRNO",params[0]);
            try
            {
                response = UrlPostConnection("http://bc_service.hescomtrm.com/Service.asmx/CustomerSearch2",datamap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.get_customer_search_details_RRNO(result,handler, getSetValues);
            super.onPostExecute(result);
        }
    }
    //For Complaint Register
    public class Complaint_Register extends AsyncTask<String,String,String>
    {
        String response = "";
        Handler handler;
        GetSetValues getSetValues;
        public Complaint_Register(Handler handler, GetSetValues getSetValues)
        {
            this.handler = handler;
            this.getSetValues = getSetValues;
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String>datamap = new HashMap<>();
            datamap.put("RRNO",params[0]);
            datamap.put("CONSID",params[1]);
            datamap.put("SUBDIVCODE",params[2]);
            datamap.put("MOBILE_NO",params[3]);
            datamap.put("COMP_TYPE",params[4]);
            datamap.put("COMP_SUBCAT",params[5]);
            datamap.put("LANDMARK",params[6]);
            datamap.put("REMARKS",params[7]);
            Log.d("Debug","RRNO"+params[0]);
            Log.d("Debug","CONSID"+params[1]);
            Log.d("Debug","SUBDIVCODE"+params[2]);
            Log.d("Debug","MOBILE_NO"+params[3]);
            Log.d("Debug","COMP_TYPE"+params[4]);
            Log.d("Debug","COMP_SUBCAT"+params[5]);
            Log.d("Debug","LANDMARK"+params[6]);
            Log.d("Debug","REMARKS"+params[7]);
            try
            {
                response = UrlPostConnection("http://bc_service.hescomtrm.com/Service.asmx/Customer_Complaint",datamap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.getComplaint_registration_status(result,handler);
            super.onPostExecute(result);
        }
    }
    //For Complaint Search
    public class ComplaintSearch extends AsyncTask<String,String,String>
    {
        String response ="";
        Handler handler;
        GetSetValues getSetValues;
        ArrayList<GetSetValues>arrayList;
        ComplaintHistoryAdapter complaintHistoryAdapter;
        public ComplaintSearch(Handler handler,GetSetValues getSetValues, ArrayList<GetSetValues>arrayList, ComplaintHistoryAdapter complaintHistoryAdapter)
        {
            this.handler = handler;
            this.getSetValues = getSetValues;
            this.arrayList = arrayList;
            this.complaintHistoryAdapter = complaintHistoryAdapter;
        }
        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String>datamap = new HashMap<>();
            datamap.put("CONSID",params[0]);
            try
            {
                response = UrlPostConnection("http://bc_service.hescomtrm.com/Service.asmx/Complaint_Search",datamap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            receivingData.getComplaintSearch_status(result, handler, getSetValues,arrayList,complaintHistoryAdapter);
            super.onPostExecute(result);
        }
    }
}
