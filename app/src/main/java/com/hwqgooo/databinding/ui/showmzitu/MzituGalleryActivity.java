package com.hwqgooo.databinding.ui.showmzitu;

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
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.FragmentGirlBinding;
import com.hwqgooo.databinding.databinding.ItemGirlBinding;
import com.hwqgooo.databinding.model.bean.GirlGallery;
import com.hwqgooo.databinding.ui.showgirlphoto.GirlPhotoGalleryActivity;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.viewmodel.MZituGalleryVM;

import java.util.List;
import java.util.Map;

/**
 * Created by weiqiang on 2017/1/5.
 */

public class MzituGalleryActivity extends AppCompatActivity {
    final static String TAG = MzituGalleryActivity.class.getSimpleName();
    FragmentGirlBinding binding;

    MZituGalleryVM vm;
    String url;

    public static void launch(Context context, View childView, GirlGallery gallery) {
        final Intent intent = new Intent(context, MzituGalleryActivity.class);
        intent.putExtra("url", gallery.href);

        Log.d(TAG, "onItemClick: " + gallery.desc);
        final ActivityOptionsCompat options;

        if (Build.VERSION.SDK_INT >= 21) {
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) context, childView, gallery.desc);
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
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_girl);
        url = getIntent().getStringExtra("url");
        if (vm == null) {
            vm = MZituGalleryVM.getInstance(url);
        }
        binding.setBasegirlvm(vm);
        binding.swipeRefreshLayout.setEnabled(false);
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
        binding.girlView.addOnItemTouchListener(new OnRcvClickListener<ItemGirlBinding>() {
            @Override
            public void onItemClick(ItemGirlBinding binding, int position) {
                GirlPhotoGalleryActivity.launch(MzituGalleryActivity.this, binding.getRoot(),
                        position, vm.getItems().get(position).getDesc());
            }
        });
        binding.swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                binding.swipeRefreshLayout.setProgressViewOffset(false, 0,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                                getResources().getDisplayMetrics()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.onStart();
        if (vm.selectPage != 0) {
            binding.girlView.scrollToPosition(vm.selectPage);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vm.putInstance();
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
