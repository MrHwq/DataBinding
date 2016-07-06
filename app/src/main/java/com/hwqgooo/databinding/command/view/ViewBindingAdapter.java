package com.hwqgooo.databinding.command.view;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;

import com.hwqgooo.databinding.command.ReplyCommand;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class ViewBindingAdapter {
    public static final String TAG = "ViewBindingAdapter";

    @BindingAdapter({"clickCommand"})
    public static void clickCommand(View view, final ReplyCommand clickCommand) {
        Log.d(TAG, "clickCommand: ");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCommand != null) {
                    clickCommand.execute();
                }
            }
        });
    }
}
