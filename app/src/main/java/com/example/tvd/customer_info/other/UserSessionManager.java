package com.example.tvd.customer_info.other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.tvd.customer_info.LoginActivity;
import com.example.tvd.customer_info.MainActivity;

import java.util.HashMap;

public class UserSessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "UserLoginCheck";
    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    // User name (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    // Password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";
    //constructor
    public UserSessionManager(Context context)
    {
        this.context = context;
        preferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    //Create login session
    public void createUserLoginSession(String email, String password){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_EMAIL, email);

        // Storing email in pref
        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();
    }
    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

            return true;
        }
        else
        {
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
        }
        return false;
    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));

        // user email id
        user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);

    }
    // Check for login
    public boolean isUserLoggedIn(){
        return preferences.getBoolean(IS_USER_LOGIN, false);
    }

}
