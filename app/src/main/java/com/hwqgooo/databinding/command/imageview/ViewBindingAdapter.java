package com.hwqgooo.databinding.command.imageview;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ViewBindingAdapter {
    public final static String TAG = "ImageViewBindingAdapter";

//    @BindingAdapter({"uri"})
//    public static void setImageUri(ImageView iv, String uri) {
//        try {
//            Log.d(TAG, "imageview load uri " + uri);
//            if (!TextUtils.isEmpty(uri)) {
//                Glide.with(iv.getContext())
//                        .load(uri)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .fitCenter()
//                        .into(iv);
//            } else {
//                iv.setBackgroundDrawable(null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @BindingAdapter(value = {"uri", "placeholderImageRes"},
            requireAll = false)
    public static void loadImage(final ImageView imageView, String uri,
                                 @DrawableRes int placeholderImageRes) {
        if (placeholderImageRes > 0) {
            imageView.setImageResource(placeholderImageRes);
        }
        if (!TextUtils.isEmpty(uri)) {
            Glide.with(imageView.getContext())
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(imageView);
        }
    }
}

