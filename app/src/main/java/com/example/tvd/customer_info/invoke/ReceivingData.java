package com.example.tvd.customer_info.invoke;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.tvd.customer_info.LoginActivity;
import com.example.tvd.customer_info.MainActivity;
import com.example.tvd.customer_info.values.FunctionCall;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_SUCCESS;

public class ReceivingData {
    private FunctionCall functionCall = new FunctionCall();
    private String parseServerXML(String result) {
        String value="";
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
                                value =  parser.nextText();
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
    public void get_Registration_Status(String result, Handler handler)
    {
        String res = parseServerXML(result);
        Log.d("Debug","Result is"+result);
        JSONObject jsonObject;
        try
        {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            if (StringUtils.startsWithIgnoreCase(message,"success"))
                handler.sendEmptyMessage(REGISTRATION_SUCCESS);
            else
                handler.sendEmptyMessage(REGISTRATION_FAILURE);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(REGISTRATION_FAILURE);
        }
    }
    //For Login
    public void get_Login_Status(String result, Handler handler)
    {
        String res = parseServerXML(result);
        Log.d("Debug", "Result is" + result);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");

            if (StringUtils.startsWithIgnoreCase(message, "success"))
                    handler.sendEmptyMessage(LOGIN_SUCCESS);
                else
                    handler.sendEmptyMessage(LOGIN_FAILURE);
        } catch (JSONException e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
        }
    }
}
