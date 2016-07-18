package com.hwqgooo.databinding.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.ActivityMainBinding;
import com.hwqgooo.databinding.ui.showgirl.GirlFragment;
import com.hwqgooo.databinding.viewmodel.IToolbarState;
import com.hwqgooo.databinding.viewmodel.MainThemeVM;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MainActivity";
    ActivityMainBinding binding;
    MainThemeVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(exitTransition());
            getWindow().setSharedElementReenterTransition(reenterTransition());
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = MainThemeVM.getInstance(getApplicationContext());
        binding.setMainthemevm(vm);
        binding.setOnOffsetChangedListener(new OnMyOffsetChangedListener());
        initTab();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainThemeVM.getInstance(this).onDestory();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Transition exitTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new BounceInterpolator());
        bounds.setDuration(2000);
        Log.d(TAG, "exitTransition: ");

        return bounds;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Transition reenterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new OvershootInterpolator());
        bounds.setDuration(2000);
        Log.d(TAG, "reenterTransition: ");

        return bounds;
    }

    void initTab() {
        final List<Fragment> lists = new LinkedList<>();
        lists.add(new GirlFragment());
//        String[] titles = {"xinggan", "japan", "taiwan", "mm"};
//        for (String title : titles) {
//            Bundle bundle = new Bundle();
//            String mziTitle = title;
//            Log.d(TAG, "initTab: " + title + ".." + mziTitle);
//            bundle.putString("title", mziTitle);
//            MzituFragment fragment = new MzituFragment();
//            fragment.setArguments(bundle);
//            fragment.setTitle(title);
//            lists.add(fragment);
//        }

        TabsPagerAdapter tabs = new TabsPagerAdapter(getSupportFragmentManager(), lists);
        binding.viewpager.setAdapter(tabs);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        Log.d(TAG, "onActivityReenter: ");
    }

    /* 传递vm applayout折叠展开状态*/
    class OnMyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            IToolbarState state = (IToolbarState) vm;
            if (verticalOffset == 0) {
                if (state.getToolbarState() != IToolbarState.EXPANDED) {
                    state.setToolbarState(IToolbarState.EXPANDED);
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state.getToolbarState() != IToolbarState.COLLAPSED) {
                    state.setToolbarState(IToolbarState.COLLAPSED);
                }
            } else {
                if (state.getToolbarState() != IToolbarState.INTERNEDIATE) {
                    if (state.getToolbarState() != IToolbarState.COLLAPSED) {
                        //折叠变成隐藏
                    }
                    state.setToolbarState(IToolbarState.INTERNEDIATE);
                }
            }
        }
    }
}
