package com.hwqgooo.databinding.ui.showgirlphoto;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.ActivityGirlPhotoBinding;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.viewmodel.MainThemeVM;

import java.util.List;
import java.util.Map;

/**
 * Created by weiqiang on 2016/7/9.
 */
public class GirlPhotoActivity extends AppCompatActivity {
    final static String TAG = "GirlPhotoActivity";
    ActivityGirlPhotoBinding binding;
    Girl girl;
    MainThemeVM vm;
    int index;

    //Activity.supportFinishAfterTransition() method instead of Activity.finish()
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inside your activity (if you did not enable transitions in your theme)
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
// set an enter transition
//        getWindow().setEnterTransition(new Explode());
// set an exit transition
//        getWindow().setExitTransition(new Explode());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(enterTransition());
            getWindow().setSharedElementReturnTransition(returnTransition());
        }
        vm = MainThemeVM.getInstance(getApplicationContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_girl_photo);
        binding.setMainthemevm(vm);
        Bundle bundle = getIntent().getExtras();
        index = bundle.getInt("index");
        girl = bundle.getParcelable("girl");
        binding.setGirl(girl);
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
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(2000);
        Log.d(TAG, "enterTransition: ");

        return bounds;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Transition returnTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new DecelerateInterpolator());
        bounds.setDuration(2000);
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
