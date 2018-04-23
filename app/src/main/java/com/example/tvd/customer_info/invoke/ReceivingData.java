package com.example.tvd.customer_info.invoke;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by TVD on 4/23/2018.
 */

public class ReceivingData {
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
 public void getRegistration_Details(String result)
 {
     result = parseServerXML(result);
     JSONArray jsonArray;
     try {
         jsonArray = new JSONArray(result);
         for (int i=0;i<jsonArray.length();i++)
         {
             JSONObject jsonObject = jsonArray.getJSONObject(i);
             String message = jsonObject.getString("message");
             if (StringUtils.startsWithIgnoreCase(message,"Success"))
             {
                Log.d("Debugg","Login Success");
             }
             else
             {
                 Log.d("Debugg","Login Failure");
             }
         }
     } catch (JSONException e) {
         e.printStackTrace();
     }
 }
}
