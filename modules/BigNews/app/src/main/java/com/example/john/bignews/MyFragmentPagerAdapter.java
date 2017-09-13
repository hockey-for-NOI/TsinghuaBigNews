package com.example.john.bignews;

import android.graphics.pdf.PdfDocument;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
    ArrayList<Fragment> fragmentList;
    FragmentManager fragmentManager;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        fragmentList = new ArrayList<Fragment>();
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

    public void setFragments()
    {
        titles = User.getFavouriteCategories();
        titles.add(0, "推荐");
        fragmentList.clear();
        for (String i : titles) fragmentList.add(PageFragment.newInstance(i));
        notifyDataSetChanged();
    }
}