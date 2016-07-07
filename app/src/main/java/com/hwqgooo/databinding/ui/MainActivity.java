package com.hwqgooo.databinding.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.ActivityMainBinding;
import com.hwqgooo.databinding.ui.showgirl.GirlFragment;
import com.hwqgooo.databinding.viewmodel.IToolbarState;
import com.hwqgooo.databinding.viewmodel.MainThemeVM;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainThemeVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.getResources().getColor(R.color.colorPrimary);
        vm = MainThemeVM.getInstance(this);
        binding.setMainthemevm(vm);
        binding.setOnOffsetChangedListener(new OnMyOffsetChangedListener());
        initTab();
    }

    void initTab() {
        final List<Fragment> lists = new LinkedList<>();
        lists.add(new GirlFragment());

        TabsPagerAdapter tabs = new TabsPagerAdapter(getSupportFragmentManager(), lists);
        binding.viewpager.setAdapter(tabs);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainThemeVM.getInstance(this).onDestory();
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
