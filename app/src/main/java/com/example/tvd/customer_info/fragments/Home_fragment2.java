package com.example.tvd.customer_info.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvd.customer_info.NotificationActivity;
import com.example.tvd.customer_info.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class Home_fragment2 extends Fragment {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    TextView textcartitemcount;
    int mCartItemCount = 10;

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

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

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
                //moving to notification activity
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
}
