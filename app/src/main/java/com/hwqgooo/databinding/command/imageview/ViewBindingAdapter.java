package com.hwqgooo.databinding.command.imageview;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.FaceDetector;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hwqgooo.databinding.command.ReplyCommand;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    @BindingAdapter(value = {"uri_face"},
            requireAll = false)
    public static void loadFaceCenterImage(final ImageView imageView, String uri_face) {
        Glide.with(imageView.getContext())
                .load(uri_face)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super
                            Bitmap> glideAnimation) {
                        Observable.just(resource)
                                .map(new Func1<Bitmap, Bitmap>() {
                                    @Override
                                    public Bitmap call(Bitmap bitmap) {
                                        return bitmap.copy(Bitmap.Config.RGB_565, true);
                                    }
                                })
                                .map(new Func1<Bitmap, Bitmap>() {
                                    @Override
                                    public Bitmap call(Bitmap bitmap) {
                                        try {
                                            Log.d("hwqhwq", "" + imageView.getWidth());
                                            Log.d("hwqhwq", "" + imageView.getHeight());
                                            FaceDetector faceDetector = new FaceDetector(bitmap
                                                    .getWidth(), bitmap.getHeight(), 1);
                                            FaceDetector.Face[] face = new FaceDetector.Face[1];
                                            Log.d(TAG, "call: go find face");
                                            if (faceDetector.findFaces(bitmap, face) > 0) {
                                                Log.d(TAG, "call: find face one");
                                                FaceDetector.Face f = face[0];
                                                PointF midPoint = new PointF();
                                                float dis = f.eyesDistance();
                                                f.getMidPoint(midPoint);
                                                int dd = (int) (dis);
                                                Point eyeLeft = new Point((int) (midPoint.x - dis /
                                                        2), (int) midPoint.y);
                                                Point eyeRight = new Point((int) (midPoint.x + dis /
                                                        2), (int) midPoint.y);
                                                Rect faceRect = new Rect((int) (midPoint.x - dd),
                                                        (int) (midPoint.y - dd), (int) (midPoint.x +
                                                        dd), (int) (midPoint.y + dd));
                                                Bitmap.Config config =
                                                        bitmap.getConfig() != null ? bitmap
                                                                .getConfig

                                                                        () : Bitmap.Config
                                                                .ARGB_8888;
                                                Bitmap result = Bitmap.createBitmap(bitmap
                                                                .getWidth()
                                                        , bitmap.getHeight(), config);
                                                Canvas canvas = new Canvas(result);
                                                Log.d(TAG, "call: " + faceRect.flattenToString());
//                                            canvas.drawBitmap(bitmap, null, , null);
                                                canvas.drawBitmap(bitmap, 0, 0, null);
                                                bitmap.recycle();
                                                Paint p = new Paint();
                                                p.setAntiAlias(true);
                                                p.setStrokeWidth(8);
                                                p.setStyle(Paint.Style.STROKE);
                                                p.setColor(Color.GREEN);
                                                canvas.drawCircle(eyeLeft.x, eyeLeft.y, 20, p);
                                                canvas.drawCircle(eyeRight.x, eyeRight.y, 20, p);
                                                canvas.drawRect(faceRect, p);
                                                return result;
                                            }
                                            return bitmap;
                                        } catch (Exception e) {
                                            return bitmap;
                                        }
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Bitmap>() {
                                    @Override
                                    public void call(Bitmap bitmap) {
                                        imageView.setImageBitmap(bitmap);
                                    }
                                });
//                        imageView.setImageBitmap(resource);
                    }
                });
    }

    @BindingAdapter(value = {"uri",
            "errorImage",
            "onSuccessCommand",
            "onFailureCommand"},
            requireAll = false)
    public static void loadUrlImage(final ImageView imageView, String uri,
                                    @DrawableRes final int errorImage,
                                    final ReplyCommand<Drawable> onSuccessCommand,
                                    final ReplyCommand onFailureCommand) {
        if (TextUtils.isEmpty(uri)) {
            imageView.setImageDrawable(null);
            return;
        }
        try {
            Glide.with(imageView.getContext())
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            imageView.setImageResource(errorImage);
                            if (onFailureCommand != null) {
                                onFailureCommand.execute();
                            }
                            Log.d(TAG, "onLoadFailed: ");
                        }

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                                GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(resource);
                            if (onSuccessCommand != null) {
                                onSuccessCommand.execute(resource);
                            }
                        }
                    });
        } catch (IllegalStateException e) {
            Log.d(TAG, "loadUrlImage: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

