package com.hwqgooo.databinding.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.ActivityMainBinding;
import com.hwqgooo.databinding.ui.carton.CartonFragment;
import com.hwqgooo.databinding.ui.showgirl.GirlFragment;
import com.hwqgooo.databinding.ui.showmzitu.MzituFragment;
import com.hwqgooo.databinding.viewmodel.IToolbarState;
import com.hwqgooo.databinding.viewmodel.MainThemeVM;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static String TAG = MainActivity.class.getSimpleName();
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
        binding.navView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener());
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
        lists.add(new CartonFragment());
        String[] subUrls = {"xinggan", "japan", /*"taiwan",*/ "mm"};
        for (int i = 0; i < subUrls.length; ++i) {
            Bundle bundle = new Bundle();
            bundle.putString("url", subUrls[i]);
            MzituFragment fragment = new MzituFragment();
            fragment.setArguments(bundle);
            lists.add(fragment);
        }

        String[] titles = {"妹子", "卡通", "性感", "日本", /*"台湾",*/ "清纯"};
        TabsPagerAdapter tabs = new TabsPagerAdapter(getSupportFragmentManager(), lists, titles);
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
            IToolbarState state = vm;
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

    class OnNavigationItemSelectedListener implements NavigationView
            .OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
//            selectFragment(item.getItemId());
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }
}
