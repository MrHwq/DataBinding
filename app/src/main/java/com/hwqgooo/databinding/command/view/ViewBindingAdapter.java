package com.hwqgooo.databinding.command.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hwqgooo.databinding.command.ReplyCommand;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class ViewBindingAdapter {
    public static final String TAG = "ViewBindingAdapter";

    @BindingAdapter(value = {"request_width", "request_height"},
            requireAll = false)
    public static void loadImage(final View view, int width, int height) {
        if (width > 0 && height > 0) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = height;
            params.width = width;
            view.setLayoutParams(params);
        }
    }

    @BindingAdapter({"clickCommand"})
    public static void clickCommand(View view, final ReplyCommand clickCommand) {
        Log.d(TAG, "clickCommand: " + clickCommand);
        if (clickCommand == null) {
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCommand.execute();
            }
        });
    }

    /* 5.0 设置状态栏颜色 */
    @BindingAdapter({"statusbarcolor"})
    public static void setStatusBarColor(final View view, int color) {
        Log.d(TAG, "setStatusBarColor: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Context context = view.getContext();
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                Window window = activity.getWindow();
                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                // finally change the color
                window.setStatusBarColor(color);
            }
        }
    }
}
