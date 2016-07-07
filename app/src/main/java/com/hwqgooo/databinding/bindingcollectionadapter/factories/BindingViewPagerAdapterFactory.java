package com.hwqgooo.databinding.bindingcollectionadapter.factories;

import android.support.v4.view.ViewPager;

import com.hwqgooo.databinding.bindingcollectionadapter.BindingViewPagerAdapter;
import com.hwqgooo.databinding.bindingcollectionadapter.ItemViewArg;

public interface BindingViewPagerAdapterFactory {
    BindingViewPagerAdapterFactory DEFAULT = new BindingViewPagerAdapterFactory() {
        @Override
        public <T> BindingViewPagerAdapter<T> create(ViewPager viewPager, ItemViewArg<T> arg) {
            return new BindingViewPagerAdapter<>(arg);
        }
    };

    <T> BindingViewPagerAdapter<T> create(ViewPager viewPager, ItemViewArg<T> arg);
}
