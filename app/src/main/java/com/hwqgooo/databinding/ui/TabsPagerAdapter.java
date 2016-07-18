package com.hwqgooo.databinding.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.hwqgooo.databinding.ui.fragment.BaseFragment;

import java.util.List;

/**
 * Created by weiqiang on 2016/6/11.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public final static String TAG = "TabsPagerAdapter";
    List<Fragment> lists;

    public TabsPagerAdapter(FragmentManager fm, List<Fragment> lists) {
        super(fm);
        this.lists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: " + position);
        return lists != null ? lists.get(position) : null;
    }

    @Override
    public int getCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            BaseFragment fragment = (BaseFragment) lists.get(position);
            return fragment.getTitle();
        } catch (Exception e) {
            e.printStackTrace();
            return super.getPageTitle(position);
        }
    }
}
