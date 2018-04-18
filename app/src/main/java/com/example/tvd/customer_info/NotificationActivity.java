package com.example.tvd.customer_info;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tvd.customer_info.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NotificationActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    private Toolbar toolbar;
    // ArrayList for person names
    ArrayList personNames = new ArrayList<>(Arrays.asList("Notification 1", "Notification 2", "Notification 3"));
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.image1, R.drawable.image2,R.drawable.image3));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Notifications");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // init SwipeRefreshLayout and ListView
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.indigo, R.color.lime);

        // get the reference of RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        NotificationAdapter customAdapter = new NotificationAdapter(NotificationActivity.this, personNames, personImages);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        // implement setOnRefreshListener event on SwipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        shuffleItems();
                    }
                }, 3000L);
            }
        });
    }


    public void shuffleItems() {
        // shuffle the ArrayList's items and set the adapter
        Collections.shuffle(personNames, new Random(System.currentTimeMillis()));
        Collections.shuffle(personImages, new Random(System.currentTimeMillis()));
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        NotificationAdapter customAdapter = new NotificationAdapter(NotificationActivity.this, personNames, personImages);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    }

