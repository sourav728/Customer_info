package com.example.tvd.customer_info;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tvd.customer_info.fragments.home_fragment;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.tvd.customer_info.values.ConstantValues.DEACTIVATE_ACCOUNT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int RequestPermissionCode = 1;
    private boolean doubleBackToExitPressedOnce = true;
    private static AppCompatActivity thisActivity;
    TextView name, email;
    TextView font_toolbar_title;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String useremail = sharedPreferences.getString("EMAIL", "");

        String curr_consumer_id = sharedPreferences.getString("Curr_Cons_ID", "");

        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        thisActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        font_toolbar_title.setTypeface(typeface);

        //below code will check that if any shared preference value is available which is set on Consumer
        //list adapter if so then it will set that value to toolbar if not then it will set default value to toolbar
        if (!curr_consumer_id.equals(""))
            font_toolbar_title.setText(curr_consumer_id);
        else
            font_toolbar_title.setText("CustomerInfo");
        //End of code
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        email = (TextView) view.findViewById(R.id.nav_email);
        email.setText(useremail);

        NavigationView logout_view = (NavigationView) findViewById(R.id.nav_view2);
        logout_view.setNavigationItemSelectedListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermissionAbove();
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        onNavigationItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;

        int id = item.getItemId();

        replaceFragment(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(int id) {
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new home_fragment();
        } else if (id == R.id.nav_logout) {
            /*Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();*/
            SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
    }

    public void checkPermissionAbove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                loadscreen();
            } else requestPermission();
        } else {
            loadscreen();
        }
    }

    private void loadscreen() {
        replaceFragment(R.id.nav_home);
    }

    @TargetApi(23)
    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        WRITE_EXTERNAL_STORAGE,
                        READ_PHONE_STATE
                }, RequestPermissionCode);
    }

    @TargetApi(23)
    private boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean ReadStoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneStatePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (ReadStoragePermission && ReadPhoneStatePermission) {
                        loadscreen();
                    } else {
                        checkPermissionAbove();
                    }
                }
                break;
        }
    }


}
