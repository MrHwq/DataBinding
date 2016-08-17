package me.tatarka.bindingcollectionadapter.factories;

import android.support.v4.view.ViewPager;

import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

public interface BindingViewPagerAdapterFactory {
    BindingViewPagerAdapterFactory DEFAULT = new BindingViewPagerAdapterFactory() {
        @Override
        public <T> BindingViewPagerAdapter<T> create(ViewPager viewPager, ItemViewArg<T> arg) {
            return new BindingViewPagerAdapter<>(arg);
        }
    };

    <T> BindingViewPagerAdapter<T> create(ViewPager viewPager, ItemViewArg<T> arg);
}
