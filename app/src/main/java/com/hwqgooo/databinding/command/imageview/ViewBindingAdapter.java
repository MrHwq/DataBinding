package com.hwqgooo.databinding.command.imageview;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ViewBindingAdapter {
    public final static String TAG = "ImageViewBindingAdapter";

    @BindingAdapter({"uri"})
    public static void setImageUri(ImageView iv, String uri) {
        try {
            Log.d(TAG, "imageview load uri " + uri);
            if (!TextUtils.isEmpty(uri)) {
                Glide.with(iv.getContext())
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .into(iv);
            } else {
                iv.setBackgroundDrawable(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

