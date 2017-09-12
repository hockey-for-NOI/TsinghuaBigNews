package com.example.john.bignews;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;

import com.example.hq.usermanager.Newsabs;
import com.example.hq.usermanager.User;
import com.example.sth.net.Category;
import com.github.clans.fab.FloatingActionButton;
import com.example.sth.net.NewsParam;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Thread[] news_grabber;
    public static MainActivity mainInstance;
    private FloatingActionButton fabInfo, fabLogin, fabLogout, fabRegister, fabCategory, fabFavourite;
    private Intent intentLogin, intentRegister, intentCategory;
    public static int static_width, static_height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i=1; i<=Category.getCategoryNumber(); i++) {
            final int pid = i;
            new Thread() {
                @Override
                public void run() {
                    Newsabs.grab(new NewsParam().setCategory(pid)
                            .setPageNo(1).setPageSize(20));
                }
            }.start();
        }

        setContentView(R.layout.activity_main);

        mainInstance = this;
        static_width = getResources().getDisplayMetrics().widthPixels;
        static_height = getResources().getDisplayMetrics().heightPixels;

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        intentLogin = new Intent(this, Login.class);
        intentRegister = new Intent(this, Register.class);
        intentCategory = new Intent(this, CategoryView.class);

        fabInfo = (FloatingActionButton) findViewById(R.id.UserPageInfo);
        fabInfo.setLabelText(User.getUser().getName());
        fabLogin = (FloatingActionButton) findViewById(R.id.UserPageLogin);
        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentLogin);
            }
        });
        fabLogout = (FloatingActionButton) findViewById(R.id.UserPageLogout);
        if (User.isGuest()) fabLogout.setEnabled(false);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    User.logout();
                    refresh();
                }
                catch(Exception e){}
            }
        });
        fabRegister = (FloatingActionButton) findViewById(R.id.UserPageRegister);
        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentRegister);
            }
        });
        fabCategory = (FloatingActionButton) findViewById(R.id.UserPageCategory);
        fabCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCategory);
            }
        });
    }
    public void refresh()
    {
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        finish();
    }
}
