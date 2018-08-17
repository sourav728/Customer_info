package com.example.tvd.customer_info.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tvd.customer_info.AccountRegistrationActivity;
import com.example.tvd.customer_info.NotificationActivity;
import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.SwitchConsumerActivity;
import com.example.tvd.customer_info.helper.LocaleHelper;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tvd.customer_info.values.ConstantValues.SHOWCASE_ID1;

public class Home_fragment2 extends Fragment {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    TextView textcartitemcount;
    ImageView notification;
    int mCartItemCount = 10;
    LinearLayout add_consumer, switch_consumer;
    TextView txt_add_consumer, txt_switch_consumer_account;
    Typeface typeface;
    Toolbar toolbar;
    Animation shake;
    private Menu menu;

    public Home_fragment2() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fragment2, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "calibri.ttf");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        String language = sharedPreferences.getString("LANGUAGE", "");

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        add_consumer = (LinearLayout) view.findViewById(R.id.lin_add_consumer);
        switch_consumer = (LinearLayout) view.findViewById(R.id.lin_switch_consumer);


        carouselView.setImageListener(imageListener);
        add_consumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountRegistrationActivity.class);
                getActivity().startActivity(intent);
            }
        });
        switch_consumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SwitchConsumerActivity.class);
                getActivity().startActivity(intent);
            }
        });
        txt_add_consumer = (TextView) view.findViewById(R.id.txt_add_consumer);
        txt_add_consumer.setTypeface(typeface);
        txt_switch_consumer_account = (TextView) view.findViewById(R.id.txt_switch_consumer);
        txt_switch_consumer_account.setTypeface(typeface);
        if (!language.equals("")) {
            if (language.equals("KN")) {
                updateViews("KN");
            } else if (language.equals("en")) {
                updateViews("en");
            }
        }

        //todo below line codes is for showcaseView
      /*  ViewTarget target = new ViewTarget(txt_add_consumer);
        new ShowcaseView.Builder(getActivity())
                .setContentTitle("You can Add New Consumer and Switch to Another Consumer")
                .setContentText("Click here")
                .useDecorViewAsParent() //this is the difference
                .setTarget(target)
                .build();*/

        new MaterialShowcaseView.Builder(getActivity())
                .setTarget(txt_add_consumer)
                .setDismissText("GOT IT")
                .setContentText("You can Add New Consumer and Switch to Another Consumer")
                .setDelay(3000) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse(SHOWCASE_ID1) // provide a unique ID used to ensure it is only shown once
                .show();

        //todo end of showcaseView Part
        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_notification);
        View actionview = MenuItemCompat.getActionView(menuItem);
        textcartitemcount = (TextView) actionview.findViewById(R.id.cart_badge);
        notification = (ImageView) actionview.findViewById(R.id.img_notification_icon);
        final Animation animShake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        notification.startAnimation(animShake);

        setupBadge();
        actionview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (textcartitemcount != null) {
            if (mCartItemCount == 0) {
                if (textcartitemcount.getVisibility() != View.GONE) {
                    textcartitemcount.setVisibility(View.GONE);
                }
            } else {
                textcartitemcount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textcartitemcount.getVisibility() != View.VISIBLE) {
                    textcartitemcount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(getActivity(), languageCode);
        Resources resources = context.getResources();
        txt_add_consumer.setText(resources.getString(R.string.addconsumer));
        txt_switch_consumer_account.setText(resources.getString(R.string.switchconsumer));
    }
}
