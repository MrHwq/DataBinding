package com.hwqgooo.databinding.ui.carton;

import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
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
import com.hwqgooo.databinding.databinding.ItemCartonCoverBinding;
import com.hwqgooo.databinding.utils.recyclerview.CommonAdapter;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.viewmodel.CartonGalleryVM;

import java.util.List;
import java.util.Map;

/**
 * Created by weiqiang on 2017/1/5.
 */

public class CartonGalleryActivity extends AppCompatActivity {
    final static String TAG = CartonGalleryActivity.class.getSimpleName();
    FragmentGirlBinding binding;

    CartonGalleryVM vm;
    int id;
    int chapter;

    public static void launch(Context context, View childView, int id, int chapter) {
        final Intent intent = new Intent(context, CartonGalleryActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("chapter", chapter);
        final ActivityOptionsCompat options;
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) context, childView, "id");
        context.startActivity(intent, options.toBundle());
    }

    //Activity.supportFinishAfterTransition() method instead of Activity.finish()
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSharedElementEnterTransition(enterTransition());
        getWindow().setSharedElementReturnTransition(returnTransition());

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_girl);
        id = getIntent().getIntExtra("id", 1);
        chapter = getIntent().getIntExtra("chapter", 1);
        vm = ViewModelProviders.of(this).get(CartonGalleryVM.class);
        vm.setGallery(id, chapter);
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
        binding.girlView.addOnItemTouchListener(new OnRcvClickListener<ItemCartonCoverBinding>() {
            @Override
            public void onItemClick(ItemCartonCoverBinding binding, int position) {
//                GirlPhotoGalleryActivity.launch(CartonChapterActivity.this, binding.getRoot(),
//                        position, vm.getItems().get(position).getDesc());
            }
        });
        binding.swipeRefreshLayout.post(() -> {
            binding.swipeRefreshLayout.setProgressViewOffset(false, 0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                            getResources().getDisplayMetrics()));
            vm.items.observe(this,
                    strings -> {
                        ((CommonAdapter) binding.girlView.getAdapter()).submitList(strings);
                    });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        vm.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        finishAfterTransition();
    }
}
