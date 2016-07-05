package com.hwqgooo.databinding.command.imageview;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ViewBindingAdapter {
    @BindingAdapter({"uri"})
    public static void setImageUri(ImageView iv, String uri) {
        Log.d("hwqhwq", uri + " load uri");
        if (!TextUtils.isEmpty(uri)) {
            Glide.with(iv.getContext()).load(uri).fitCenter().into(iv);
        } else {
            iv.setBackgroundDrawable(null);
        }
    }
}

