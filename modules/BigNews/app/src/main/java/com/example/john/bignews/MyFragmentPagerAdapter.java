package com.example.john.bignews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Context;
/**
 * Created by John on 2017/9/7.
 */

class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public int COUNT = 0;
    private String[] titles;
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        COUNT = 7;
        titles = new String[]{"Tab1", "Tab2", "Tab3", "Tab4", "Tab5", "SB", "CNM"};
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}