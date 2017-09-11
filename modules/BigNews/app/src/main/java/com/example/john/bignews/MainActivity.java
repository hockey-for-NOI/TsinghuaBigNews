package com.example.john.bignews;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.*;

import com.example.hq.usermanager.User;
import com.github.clans.fab.FloatingActionButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainInstance;
    private FloatingActionButton fabInfo, fabLogin, fabRegister;
    private Intent intentLogin, intentRegister;
    public static int static_width, static_height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        fabInfo = (FloatingActionButton) findViewById(R.id.UserPageInfo);
        fabInfo.setLabelText(User.getUser().getName());
        fabLogin = (FloatingActionButton) findViewById(R.id.UserPageLogin);
        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentLogin);
            }
        });
        fabRegister = (FloatingActionButton) findViewById(R.id.UserPageRegister);
        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentRegister);
            }
        });
    }
    public void refreshUser()
    {
        fabInfo.setLabelText(User.getUser().getName());
    }
}
