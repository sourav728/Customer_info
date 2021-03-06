package com.example.tvd.customer_info.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/*import static com.example.tvd.customer_info.AccountRegistrationActivity.dialog;*/

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Debug","OnReceive started..");
        try
        {
            if (isOnline(context))
            {
                //dialog(true);
                //dialog3(true);
                Log.d("Debug","You are Online..");
            }
            else
            {
               // dialog(false);
               // dialog3(false);
                Log.d("Debug","Connectivity failure!!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
