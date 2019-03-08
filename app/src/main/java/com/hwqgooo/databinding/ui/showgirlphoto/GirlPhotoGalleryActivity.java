package com.hwqgooo.databinding.ui.showgirlphoto;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.PagerGalleryBinding;
import com.hwqgooo.databinding.viewmodel.MZituGalleryVM;

import java.util.List;
import java.util.Map;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by weiqiang on 2016/7/9.
 */
public class GirlPhotoGalleryActivity extends AppCompatActivity {
    final static String TAG = GirlPhotoGalleryActivity.class.getSimpleName();
    PagerGalleryBinding binding;

    MZituGalleryVM vm;
    int index;

    public static void launch(Context context, View childView, int position, String desc) {
        final Intent intent = new Intent(context, GirlPhotoGalleryActivity.class);
        intent.putExtra("index", position);

        final ActivityOptionsCompat options;

        if (Build.VERSION.SDK_INT >= 21) {
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) context, childView, desc);
        } else {
            options = ActivityOptionsCompat.makeScaleUpAnimation(
                    childView, 0, 0, childView.getWidth(), childView.getHeight());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    //Activity.supportFinishAfterTransition() method instead of Activity.finish()
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(enterTransition());
            getWindow().setSharedElementReturnTransition(returnTransition());
        }
        binding = DataBindingUtil.setContentView(this, R.layout.pager_gallery);
        index = getIntent().getIntExtra("index", 0);
        if (vm == null) {
            vm = MZituGalleryVM.getInstance(null);
        }
        binding.setItem(vm);
        binding.setItemView(ItemView.of(BR.girl, R.layout.activity_girl_photo));
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View>
                    sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements,
                        sharedElementSnapshots);
                Log.d(TAG, "onSharedElementStart: ");
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View>
                    sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements,
                        sharedElementSnapshots);
                Log.d(TAG, "onSharedElementEnd: ");
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.d(TAG, "onMapSharedElements: ");
            }
        });
        binding.viewpager.post(new Runnable() {
            @Override
            public void run() {
                binding.viewpager.setCurrentItem(index, false);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
//        bounds.setDuration(2000);
        Log.d(TAG, "enterTransition: ");

        return bounds;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Transition returnTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new DecelerateInterpolator());
//        bounds.setDuration(2000);
        Log.d(TAG, "returnTransition: ");

        return bounds;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
    }
}
