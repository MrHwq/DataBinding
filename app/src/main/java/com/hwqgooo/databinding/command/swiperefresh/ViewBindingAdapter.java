package com.hwqgooo.databinding.command.swiperefresh;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.hwqgooo.databinding.command.ReplyCommand;

/**
 * Created by weiqiang on 2016/7/5.
 */
public class ViewBindingAdapter {
    public static final String TAG = "SwipeViewBindingAdapter";

    @BindingAdapter({"isRefresh"})
    public static void setRefresh(SwipeRefreshLayout swipeRefreshLayout, boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
        Log.d(TAG, "setRefresh: " + isRefreshing);
    }

    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ReplyCommand
            onRefreshCommand) {
        Log.d(TAG, "onRefreshCommand: " + swipeRefreshLayout);
        if (onRefreshCommand == null) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshCommand.execute();
            }
        });
    }
}
