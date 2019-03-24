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
import com.hwqgooo.databinding.databinding.ItemGirlBinding;
import com.hwqgooo.databinding.utils.recyclerview.CommonAdapter;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.viewmodel.CartonChapterVM;

import java.util.List;
import java.util.Map;

/**
 * Created by weiqiang on 2017/1/5.
 */

public class CartonChapterActivity extends AppCompatActivity {
    final static String TAG = CartonChapterActivity.class.getSimpleName();
    FragmentGirlBinding binding;

    CartonChapterVM vm;
    int id;

    public static void launch(Context context, View childView, int id) {
        final Intent intent = new Intent(context, CartonChapterActivity.class);
        intent.putExtra("id", id);

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
        vm = ViewModelProviders.of(this).get(CartonChapterVM.class);
        vm.setId(id);
        binding.setBasegirlvm(vm);
        binding.executePendingBindings();
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
//                GirlPhotoGalleryActivity.launch(CartonChapterActivity.this, binding.getRoot(),
//                        position, vm.getItems().get(position).getDesc());
                CartonGalleryActivity.launch(CartonChapterActivity.this, binding.getRoot(),
                        id, position + 1);
            }
        });
        binding.swipeRefreshLayout.post(() -> {
            binding.swipeRefreshLayout.setProgressViewOffset(false, 0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                            getResources().getDisplayMetrics()));
            vm.items.observe(this,
                    pagedList -> {
                        System.out.println("=====" + binding.girlView.getAdapter());
                        try {
                            ((CommonAdapter) binding.girlView.getAdapter()).submitList(pagedList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
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
        finishAfterTransition();
    }
}
