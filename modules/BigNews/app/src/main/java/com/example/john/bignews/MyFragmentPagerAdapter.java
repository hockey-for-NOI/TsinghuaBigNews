package com.example.john.bignews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Context;

import com.example.hq.usermanager.User;

import java.util.ArrayList;

/**
 * Created by John on 2017/9/7.
 */

class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> titles;
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        titles = User.getFavouriteCategories();
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}