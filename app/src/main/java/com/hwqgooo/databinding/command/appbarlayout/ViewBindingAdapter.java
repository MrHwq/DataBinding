package com.hwqgooo.databinding.command.appbarlayout;

import android.databinding.BindingAdapter;
import android.support.design.widget.AppBarLayout;
import android.util.Log;

/**
 * Created by weiqiang on 2016/7/5.
 */
public class ViewBindingAdapter {
    public static final String TAG = "AppbarBindingAdapter";

    @BindingAdapter("addOnOffsetChangedListener")
    public static void initToolbar(AppBarLayout appBarLayout, AppBarLayout
            .OnOffsetChangedListener listener) {
        Log.d(TAG, "addOnOffsetChangedListener: ");
        appBarLayout.addOnOffsetChangedListener(listener);
    }
}
