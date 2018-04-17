package com.example.tvd.customer_info.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tvd.customer_info.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class Home_fragment2 extends Fragment {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image1, R.drawable.image2, R.drawable.image3};

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
        inflater.inflate(R.menu.menu_home2,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
