package com.hwqgooo.databinding.command.view;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hwqgooo.databinding.command.ReplyCommand;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class ViewBindingAdapter {
    public static final String TAG = "ViewBindingAdapter";

    @BindingAdapter(value = {"request_width", "request_height"},
            requireAll = false)
    public static void loadImage(final View imageView, int width, int height) {
        if (width > 0 && height > 0) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.height = height;
            params.width = width;
            imageView.setLayoutParams(params);
        }
    }

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
