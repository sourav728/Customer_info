package com.example.tvd.customer_info.invoke;

import android.os.Handler;
import android.util.Log;

import com.example.tvd.customer_info.adapter.ConsumerListAdapter;
import com.example.tvd.customer_info.values.FunctionCall;
import com.example.tvd.customer_info.values.GetSetValues;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_DEACTIVATED_SUCCESSFULLY;
import static com.example.tvd.customer_info.values.ConstantValues.ACCOUNT_DEACTIVATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.CONNECTION_TIME_OUT;
import static com.example.tvd.customer_info.values.ConstantValues.EMAIL_SEND_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.EMAIL_SEND_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.INSERTION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.INSERTION_SUCCESSFULL;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.LOGIN_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.PASSWORD_CHANGED_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.PASSWORD_CHANGED_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.SEARCH_FOUND;
import static com.example.tvd.customer_info.values.ConstantValues.SEARCH_NOT_FOUND;
import static com.example.tvd.customer_info.values.ConstantValues.SMS_SEND_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.SMS_SEND_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.SWITCH_CONSUMER_SUCCESS;
import static com.example.tvd.customer_info.values.ConstantValues.VIEW_BILL_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.VIEW_BILL_SUCCESS;

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
    public void get_customersearch_info(String result, Handler handler, GetSetValues getSetValues) {
        String res = parseServerXML(result);
        Log.d("Debug", "Result is" + result);
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(res);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String CONSUMER_NAME = jsonObject.getString("CONSUMER_NAME");
                    String ADD1 = jsonObject.getString("ADD1");
                    String ETVMETER = jsonObject.getString("ETVMETER");
                    String KWHP = jsonObject.getString("KWHP");
                    String TARIFF = jsonObject.getString("TARIFF");
                    String DATE_OF_SERVICE = jsonObject.getString("DATE_OF_SERVICE");
                    String mobile_no = jsonObject.getString("mobile_no");
                    String email = jsonObject.getString("email_id");
                    String subdivision = jsonObject.getString("subdiv");
                    Log.d("Consumer_Name", "Name" + CONSUMER_NAME);
                    if (!CONSUMER_NAME.equals(""))
                        getSetValues.setCons_name(CONSUMER_NAME);
                    else getSetValues.setCons_name("NA");
                    if (!ADD1.equals(""))
                        getSetValues.setCons_address(ADD1);
                    else getSetValues.setCons_address("NA");
                    if (!ETVMETER.equals(""))
                        getSetValues.setCons_etv_meter(ETVMETER);
                    else getSetValues.setCons_etv_meter("NA");
                    if (!KWHP.equals(""))
                        getSetValues.setCons_kwhp(KWHP);
                    else getSetValues.setCons_kwhp("NA");
                    if (!TARIFF.equals(""))
                        getSetValues.setCons_tariff(TARIFF);
                    else getSetValues.setCons_tariff("NA");
                    if (!DATE_OF_SERVICE.equals(""))
                        getSetValues.setCons_date_of_service(DATE_OF_SERVICE);
                    else getSetValues.setCons_date_of_service("NA");
                    if (!mobile_no.equals(""))
                        getSetValues.setCons_mobile_no(mobile_no);
                    else getSetValues.setCons_mobile_no("na");
                    if (!email.equals(""))
                        getSetValues.setCons_email(email);
                    else getSetValues.setCons_email("NA");
                    if (!subdivision.equals(""))
                        getSetValues.setCons_subdivision(subdivision);
                    else getSetValues.setCons_subdivision("NA");
                }
                handler.sendEmptyMessage(SEARCH_FOUND);
            } else handler.sendEmptyMessage(SEARCH_NOT_FOUND);
        } catch (JSONException e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(SEARCH_NOT_FOUND);
        }

    }

    public void get_consumerinsert_info(String result, Handler handler, GetSetValues getSetValues) {
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

    //For getting Consumer id's related to one account
    public void get_consumer_list(String result, Handler handler, GetSetValues getSetValues, ArrayList<GetSetValues> arrayList, ConsumerListAdapter consumerListAdapter) {
        String res = parseServerXML(result);
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(res);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    getSetValues = new GetSetValues();
                    String Consumer_id = jsonObject.getString("ACCOUNT_ID");
                    String rrno = jsonObject.getString("RRNO");
                    String relationship = jsonObject.getString("RELATIONSHIP");
                    Log.d("Debug", "CONSUMERID" + Consumer_id);
                    Log.d("Debug", "RRNO" + rrno);
                    Log.d("Debug", "Relationship" + relationship);
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
                handler.sendEmptyMessage(SWITCH_CONSUMER_SUCCESS);
            } else {
                handler.sendEmptyMessage(SWITCH_CONSUMER_FAILURE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(SWITCH_CONSUMER_FAILURE);
        }
    }

    //For Deactivate Account
    public void getDeactivate_Details(String result, Handler handler) {
        String res = parseServerXML(result);
        Log.d("Debug", "Deactivation Result" + res);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            if (StringUtils.startsWithIgnoreCase(message, "success"))
                handler.sendEmptyMessage(ACCOUNT_DEACTIVATED_SUCCESSFULLY);
            else
                handler.sendEmptyMessage(ACCOUNT_DEACTIVATION_FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(ACCOUNT_DEACTIVATION_FAILURE);
        }
    }

    //For getting Bill details of customer
    public void getBillDetails(String result, Handler handler, GetSetValues getSetValues) {
        String res = parseServerXML(result);
        Log.d("Debug", "Bill Details Result" + res);
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(res);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String date1 = jsonObject.getString("date1");
                    String DueDate = jsonObject.getString("DueDate");
                    String CONSUMER_NAME = jsonObject.getString("CONSUMER_NAME");
                    String ADD1 = jsonObject.getString("ADD1");
                    String mobile_no = jsonObject.getString("mobile_no");
                    String email_id = jsonObject.getString("email_id");
                    String consid = jsonObject.getString("consid");
                    String rrno = jsonObject.getString("rrno");
                    String ETVMETER = jsonObject.getString("ETVMETER");
                    String KWHP = jsonObject.getString("KWHP");
                    String TARIFF = jsonObject.getString("TARIFF");
                    String PREVREAD = jsonObject.getString("PREVREAD");
                    String CURREAD = jsonObject.getString("CURREAD");
                    String CON = jsonObject.getString("CON");
                    String AVGCON = jsonObject.getString("AVGCON");
                    String FC = jsonObject.getString("FC");
                    String EC = jsonObject.getString("EC");
                    String FAC = jsonObject.getString("FAC");
                    String RBTAMT = jsonObject.getString("RBTAMT");
                    String PFPENALTY = jsonObject.getString("PFPENALTY");
                    String BMDPENALTY = jsonObject.getString("BMDPENALTY");
                    String DI = jsonObject.getString("DI");
                    String DO = jsonObject.getString("DO");
                    String DT = jsonObject.getString("DT");
                    String NET_PAYABLE_AMOUNT = jsonObject.getString("NET_PAYABLE_AMOUNT");
                    String ARREARS = jsonObject.getString("ARREARS");
                    String ADJAMOUNT = jsonObject.getString("ADJAMOUNT");
                    String DG = jsonObject.getString("DG");

                    if (!date1.equals(""))
                        getSetValues.setView_bill_date1(date1);
                    else getSetValues.setView_bill_date1("NA");
                    if (!DueDate.equals(""))
                        getSetValues.setView_bill_due_date(DueDate);
                    else getSetValues.setView_bill_due_date("NA");
                    if (!CONSUMER_NAME.equals(""))
                        getSetValues.setView_bill_cons_name(CONSUMER_NAME);
                    else getSetValues.setView_bill_cons_name("NA");
                    if (!ADD1.equals(""))
                        getSetValues.setView_bill_add(ADD1);
                    else getSetValues.setView_bill_add("NA");
                    if (!mobile_no.equals(""))
                        getSetValues.setView_bill_mobileno(mobile_no);
                    else getSetValues.setView_bill_mobileno("NA");
                    if (!email_id.equals(""))
                        getSetValues.setView_bill_email(email_id);
                    else getSetValues.setView_bill_email("NA");
                    if (!consid.equals(""))
                        getSetValues.setView_bill_cons_id(consid);
                    else getSetValues.setView_bill_cons_id("NA");
                    if (!rrno.equals(""))
                        getSetValues.setView_bill_rrno(rrno);
                    else getSetValues.setView_bill_rrno("NA");
                    if (!ETVMETER.equals(""))
                        getSetValues.setView_bill_etv_meter(ETVMETER);
                    else getSetValues.setView_bill_etv_meter("NA");
                    if (!KWHP.equals(""))
                        getSetValues.setView_bill_kwhp(KWHP);
                    else getSetValues.setView_bill_kwhp(KWHP);
                    if (!TARIFF.equals(""))
                        getSetValues.setView_bill_tariff(TARIFF);
                    else getSetValues.setView_bill_tariff("NA");
                    if (!PREVREAD.equals(""))
                        getSetValues.setView_bill_prevread(PREVREAD);
                    else getSetValues.setView_bill_prevread("NA");
                    if (!CURREAD.equals(""))
                        getSetValues.setView_bill_curr_read(CURREAD);
                    else getSetValues.setView_bill_curr_read("NA");
                    if (!CON.equals(""))
                        getSetValues.setView_bill_con(CON);
                    else getSetValues.setView_bill_con("NA");
                    if (!AVGCON.equals(""))
                        getSetValues.setView_bill_avgcon(AVGCON);
                    else getSetValues.setView_bill_avgcon("NA");
                    if (!FC.equals(""))
                        getSetValues.setView_bill_fc(FC);
                    else getSetValues.setView_bill_fc("NA");
                    if (!EC.equals(""))
                        getSetValues.setView_bill_ec(EC);
                    else getSetValues.setView_bill_ec("NA");
                    if (!FAC.equals(""))
                        getSetValues.setView_bill_fac(FAC);
                    else getSetValues.setView_bill_fac("NA");
                    if (!RBTAMT.equals(""))
                        getSetValues.setView_bill_rbtamt(RBTAMT);
                    else getSetValues.setView_bill_rbtamt("NA");
                    if (!PFPENALTY.equals(""))
                        getSetValues.setView_bill_pfpanelty(PFPENALTY);
                    else getSetValues.setView_bill_pfpanelty("NA");
                    if (!BMDPENALTY.equals(""))
                        getSetValues.setView_bill_bmdpanelty(BMDPENALTY);
                    else getSetValues.setView_bill_bmdpanelty("NA");
                    if (!DI.equals(""))
                        getSetValues.setView_bill_di(DI);
                    else getSetValues.setView_bill_di("NA");
                    if (!DO.equals(""))
                        getSetValues.setView_bill_do(DO);
                    else getSetValues.setView_bill_do("NA");
                    if (!DT.equals(""))
                        getSetValues.setView_bill_dt(DT);
                    else getSetValues.setView_bill_dt("NA");
                    if (!NET_PAYABLE_AMOUNT.equals(""))
                        getSetValues.setView_bill_netpayable(NET_PAYABLE_AMOUNT);
                    else getSetValues.setView_bill_netpayable("NA");
                    if (!ARREARS.equals(""))
                        getSetValues.setView_bill_arrears(ARREARS);
                    else getSetValues.setView_bill_arrears("NA");
                    if (!ADJAMOUNT.equals(""))
                        getSetValues.setView_bill_adjamt(ADJAMOUNT);
                    else getSetValues.setView_bill_adjamt("NA");
                    if (!DG.equals(""))
                        getSetValues.setView_bill_dg(DG);
                    else getSetValues.setView_bill_dg("NA");
                }
                handler.sendEmptyMessage(VIEW_BILL_SUCCESS);
            } else handler.sendEmptyMessage(VIEW_BILL_FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(VIEW_BILL_FAILURE);
        }
    }

    //For getting SMS details
    public void getSMS_details(String result, Handler handler) {
        String res = parseServerXML(result);
        Log.d("Debug", "SMS Result Result" + res);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            if (StringUtils.startsWithIgnoreCase(message, "Success"))
                handler.sendEmptyMessage(SMS_SEND_SUCCESS);
            else
                handler.sendEmptyMessage(SMS_SEND_FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(SMS_SEND_FAILURE);
        }
    }

    //For getting Email details
    public void getEmail_Details(String result, Handler handler) {
        String res = parseServerXML(result);
        Log.d("Debug", "SMS Result Result" + res);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            if (StringUtils.startsWithIgnoreCase(message, "Success"))
                handler.sendEmptyMessage(EMAIL_SEND_SUCCESS);
            else
                handler.sendEmptyMessage(EMAIL_SEND_FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(EMAIL_SEND_FAILURE);
        }
    }

    //For Change password
    public void get_Password_change_status(String result, Handler handler) {
        String res = parseServerXML(result);
        Log.d("Debug", "Password Change Status" + result);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(res);
            String message = jsonObject.getString("message");
            if (StringUtils.startsWithIgnoreCase(message, "Success"))
                handler.sendEmptyMessage(PASSWORD_CHANGED_SUCCESS);
            else
                handler.sendEmptyMessage(PASSWORD_CHANGED_FAILURE);
        } catch (Exception e) {
            e.printStackTrace();
            functionCall.logStatus("JSON Exception Failure!!");
            handler.sendEmptyMessage(PASSWORD_CHANGED_FAILURE);
        }
    }

    //For getting complaint status
    public void get_Complaint_status(String result, Handler handler) {
        String res = parseServerXML(result);
        Log.d("Debug", "Complaint status");
    }
}
