package com.example.tvd.customer_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.fragments.BillingFragment;
import com.example.tvd.customer_info.fragments.CollectionFragment;
import com.example.tvd.customer_info.helper.LocaleHelper;
import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;
import java.util.List;

import static com.example.tvd.customer_info.values.ConstantValues.LT_BILLING_SUMMARY_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.LT_BILLING_SUMMARY_SUCCESS;

public class Billing_Collection_Tabbed_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView font_toolbar_text;
    Typeface typeface;
    SendingData sendingData;
    GetSetValues getSetValues;
    ProgressDialog progressdialog;
    String current_con_id="";
    private final Handler mHandler;
    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LT_BILLING_SUMMARY_SUCCESS:
                        progressdialog.dismiss();
                        Toast.makeText(Billing_Collection_Tabbed_Activity.this, "Success..", Toast.LENGTH_SHORT).show();
                        setValuesin_Shared_Preferences();
                        break;
                    case LT_BILLING_SUMMARY_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(Billing_Collection_Tabbed_Activity.this, "Failure!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing__collection__tabbed_);
        sendingData = new SendingData();
        getSetValues = new GetSetValues();

        typeface = Typeface.createFromAsset(getAssets(), "calibri.ttf");
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String language = sharedPreferences.getString("LANGUAGE", "");

        SharedPreferences sharedPreferences1 = getSharedPreferences("SWITCH_CONSUMER_ID", MODE_PRIVATE);
        current_con_id = sharedPreferences1.getString("Curr_Cons_ID", "");

        font_toolbar_text = (TextView) findViewById(R.id.toolbar_title);
        //below code is for loading different font
        if (!language.equals("")) {
            if (language.equals("KN")) {
                updateViews("KN");
            } else if (language.equals("en")) {
                updateViews("en");
            }
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressdialog = new ProgressDialog(this, R.style.MyProgressDialogstyle);
        progressdialog.setTitle("Connecting To Server");
        progressdialog.setMessage("Please Wait..");
        progressdialog.show();
        SendingData.LTBilling_Summary ltBilling_summary = sendingData.new LTBilling_Summary(mHandler, getSetValues);
        /*************Below Acc ID is hardcoaded********************/
        ltBilling_summary.execute(current_con_id);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        Billing_Collection_Adapter adapter = new Billing_Collection_Adapter(getSupportFragmentManager());
        adapter.addfragment(new BillingFragment(), "Billing");
        adapter.addfragment(new CollectionFragment(),"Collection");
        viewPager.setAdapter(adapter);
    }

    class Billing_Collection_Adapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Billing_Collection_Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addfragment(Fragment fragment,String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(Billing_Collection_Tabbed_Activity.this, languageCode);
        Resources resources = context.getResources();
        font_toolbar_text.setText(R.string.billingcollection);
    }
    public void setValuesin_Shared_Preferences()
    {
        SavePreferences("LT_BILLING_CON_ID",getSetValues.getLt_billing_consumer_id());
        Log.d("Debug","LT_BILLING_CON_ID"+getSetValues.getLt_billing_consumer_id());
        SavePreferences("LT_BILLING_CON_NAME",getSetValues.getLt_billing_consumer_name());
        Log.d("Debug","LT_BILLING_CON_NAME"+getSetValues.getLt_billing_consumer_name());
        SavePreferences("LT_BILLING_TARIFF", getSetValues.getLt_billing_tariff());
        Log.d("Debug","LT_BILLING_TARIFF"+getSetValues.getLt_billing_tariff());
        SavePreferences("LT_BILLING_MOBILE_NO", getSetValues.getLt_billing_mobile_no());
        Log.d("Debug","LT_BILLING_MOBILE_NO"+getSetValues.getLt_billing_mobile_no());
    }
    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
