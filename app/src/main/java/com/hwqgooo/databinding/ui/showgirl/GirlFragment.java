package com.hwqgooo.databinding.ui.showgirl;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.FragmentGirlBinding;
import com.hwqgooo.databinding.viewmodel.GirlVM;

/**
 * Created by weiqiang on 2016/7/2.
 */
public class GirlFragment extends Fragment {
    FragmentGirlBinding binding;
    Context context;
    GirlVM girlVm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        girlVm = new GirlVM(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_girl,
                container, false);
        binding.setGirlvm(girlVm);
        setRecylerView();
        setSwipeRefreshLayout();
        return binding.getRoot();
    }

    private void setRecylerView() {
        GirlAdapter girlAdapter = new GirlAdapter(context, girlVm.getGirls());
        girlVm.setAdapter(girlAdapter);
        binding.girlview.setAdapter(girlAdapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        binding.girlview.setLayoutManager(gridLayoutManager);
//        binding.girllistview.addOnItemTouchListener(new BaseRecyclerViewListener<GirlAdapter
//                .GirlHolder>
//                (binding.girllistview) {
//            @Override
//            public void onItemClick(GirlAdapter.GirlHolder holder, int position) {
//                Log.d("hwqhwq", "onItemClick: " + position);
//            }
//        });
    }

    private void setSwipeRefreshLayout() {
        //设置首次运行进度条刷新
        binding.swipeRefreshLayout.setProgressViewOffset(false,
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics()));
        //设置进度条颜色
        binding.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
    }

    boolean isload = false;

    @Override
    public void onResume() {
        super.onResume();
        if (!isload) {
            isload = true;
            girlVm.onRefresh.execute();
        }
    }
}