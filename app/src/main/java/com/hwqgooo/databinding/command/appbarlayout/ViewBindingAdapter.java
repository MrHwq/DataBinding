package com.hwqgooo.databinding.command.appbarlayout;

import android.databinding.BindingAdapter;
import android.support.design.widget.AppBarLayout;

/**
 * Created by weiqiang on 2016/7/5.
 */
public class ViewBindingAdapter {
    @BindingAdapter("addOnOffsetChangedListener")
    public static void initToolbar(AppBarLayout appBarLayout, AppBarLayout
            .OnOffsetChangedListener listener) {
        appBarLayout.addOnOffsetChangedListener(listener);
    }
}
