package com.example.john.bignews;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.*;

import com.example.hq.usermanager.Newsabs;
import com.example.hq.usermanager.User;
import com.example.sth.net.Category;
import com.example.sth.net.NewsParam;

public class MainActivity extends AppCompatActivity {

    private Thread[] news_grabber;
    private Intent intent;
    public static int static_width, static_height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        news_grabber = new Thread[Category.getCategoryNumber()];
        for (int tid = 1; tid <= Category.getCategoryNumber(); tid++){
            final int tmp = tid;
            Thread t = new Thread() {
                public void run() {
                    while (true) Newsabs.grab(new NewsParam().setCategory(tmp));
                }
            };
            news_grabber[tid - 1] = t;
            t.start();
        }

        setContentView(R.layout.activity_main);

        static_width = getResources().getDisplayMetrics().widthPixels;
        static_height = getResources().getDisplayMetrics().heightPixels;

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        intent = new Intent(this, Login.class);

        FloatingActionButton but = (FloatingActionButton)findViewById(R.id.UserPage);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}
