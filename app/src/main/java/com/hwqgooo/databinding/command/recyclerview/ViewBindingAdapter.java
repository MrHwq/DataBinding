package com.hwqgooo.databinding.command.recyclerview;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvScrollListener;

/**
 * Created by weiqiang on 2016/7/5.
 */
public class ViewBindingAdapter {
    public static final String TAG = "RcvViewBindingAdapter";

    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(RecyclerView recyclerView, final ReplyCommand
            onLoadMoreCommand) {
        Log.d(TAG, "onLoadMoreCommand: ");
        recyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                if (onLoadMoreCommand != null) {
                    onLoadMoreCommand.execute();
                }
            }

            @Override
            public int setRestItem() {
                return 6;
            }
        });
    }
}
