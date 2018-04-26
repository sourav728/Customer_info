package com.example.tvd.customer_info.invoke;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.example.tvd.customer_info.values.ConstantValues.CONNECTION_TIME_OUT;
import static com.example.tvd.customer_info.values.ConstantValues.INSERTION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.INSERTION_SUCCESSFULL;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.SEARCH_FOUND;
import static com.example.tvd.customer_info.values.ConstantValues.SEARCH_NOT_FOUND;

public class ReceivingData {
    private FunctionCall functionCall = new FunctionCall();

    private String parseServerXML(String result) {
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

    //For Registration
    public void get_Registration_Status(String result, Handler handler) {
        String res = parseServerXML(result);
        Log.d("Debug", "Result is" + result);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            String id = jsonObject.getString("id");
            if (StringUtils.startsWithIgnoreCase(message, "success"))
                handler.sendEmptyMessage(REGISTRATION_SUCCESS);
            else
                handler.sendEmptyMessage(REGISTRATION_FAILURE);
        } catch (JSONException e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(REGISTRATION_FAILURE);
        }
    }

    //For Login
    public void get_Login_Status(String result, Handler handler, GetSetValues getSetValues) {
        String res = parseServerXML(result);
        Log.d("Debug", "Result is" + result);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            String id = jsonObject.getString("id");
            Log.d("Debug", "ID" + id);
            if (StringUtils.startsWithIgnoreCase(message, "success")) {
                getSetValues.setLogin_id(id);
                handler.sendEmptyMessage(LOGIN_SUCCESS);
            } else
                handler.sendEmptyMessage(LOGIN_FAILURE);
        } catch (JSONException e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(CONNECTION_TIME_OUT);
        }
    }

    //For customer search
    public void get_customersearch_info(String result, Handler handler) {
        String res = parseServerXML(result);
        Log.d("Debug", "Result is" + result);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            if (StringUtils.startsWithIgnoreCase(message, "success"))
                handler.sendEmptyMessage(SEARCH_FOUND);
            else
                handler.sendEmptyMessage(SEARCH_NOT_FOUND);
        } catch (JSONException e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(SEARCH_NOT_FOUND);
        }
    }

    public void get_consumerinsert_info(String result, Handler handler) {
        String res = parseServerXML(result);
        Log.d("Debug", "Result is" + result);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            if (StringUtils.startsWithIgnoreCase(message, "success"))
                handler.sendEmptyMessage(INSERTION_SUCCESSFULL);
            else
                handler.sendEmptyMessage(INSERTION_FAILURE);
        } catch (JSONException e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(INSERTION_FAILURE);
        }
    }

}
