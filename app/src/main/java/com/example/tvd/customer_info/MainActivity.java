package com.example.tvd.customer_info;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.fragments.home_fragment;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.tvd.customer_info.values.UserSession.PREFER_NAME;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int RequestPermissionCode = 1;
    private boolean doubleBackToExitPressedOnce = true;
    private static AppCompatActivity thisActivity;
    TextView name,email;
    private SharedPreferences sharedPreferences;
    TextView font_toolbar_title;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeface = Typeface.createFromAsset(getAssets(),"timesnewroman.ttf");
        thisActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        font_toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        font_toolbar_title.setTypeface(typeface);
        font_toolbar_title.setText("CustomerInfo");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        name = (TextView) view.findViewById(R.id.nav_name);
        email = (TextView) view.findViewById(R.id.nav_email);
        name.setText(sharedPreferences.getString("Name",""));
        email.setText(sharedPreferences.getString("Email",""));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermissionAbove();
            }
        },1000);
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

    private void replaceFragment(int id)
    {
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new home_fragment();
        }
        if (fragment!= null)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void checkPermissionAbove()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkPermission())
            {
                loadscreen();
            }else requestPermission();
        }else {
            loadscreen();
        }
    }
    private void loadscreen()
    {
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
                    if (ReadStoragePermission  && ReadPhoneStatePermission) {
                        loadscreen();
                    } else {
                        checkPermissionAbove();
                    }
                }
                break;
        }
    }

}
