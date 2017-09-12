package com.example.john.bignews;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.hq.usermanager.Newsabs;
import com.example.hq.usermanager.User;
import com.example.sth.net.Category;
import com.github.clans.fab.FloatingActionButton;
import com.example.sth.net.NewsParam;

public class MainActivity extends AppCompatActivity {

    private Thread[] news_grabber;
    public static MainActivity mainInstance;
    private FloatingActionButton fabInfo, fabLogin, fabLogout, fabRegister, fabCategory, fabFavourite;
    private Intent intentLogin, intentRegister, intentCategory;
    public static int static_width, static_height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        news_grabber = new Thread[Category.getCategoryNumber()];
        for (int tid = 1; tid <= Category.getCategoryNumber(); tid++){
            final int tmp = tid;
            Thread t = new Thread() {
                public void run() {
                    while (true)
                    {
                        for (int page=1; page<=500; page++) {
                            Newsabs.grab(new NewsParam().setCategory(tmp));
                            Newsabs.grab(new NewsParam().setCategory(tmp).setPageNo(page).setPageSize(500));
                            try {
                                sleep(100);
                            } catch (Exception e) {}
                        }
                    }
                }
            };
            news_grabber[tid - 1] = t;
            t.start();
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
    public void refreshUser()
    {
        fabInfo.setLabelText(User.getUser().getName());
    }
}
