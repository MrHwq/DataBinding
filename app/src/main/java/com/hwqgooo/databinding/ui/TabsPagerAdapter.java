package com.hwqgooo.databinding.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by weiqiang on 2016/6/11.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public final static String TAG = TabsPagerAdapter.class.getSimpleName();
    List<Fragment> lists;
    String[] titles;

    public TabsPagerAdapter(FragmentManager fm, List<Fragment> lists, String[] titles) {
        super(fm);
        this.lists = lists;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return lists != null ? lists.get(position) : null;
    }

    @Override
    public int getCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
