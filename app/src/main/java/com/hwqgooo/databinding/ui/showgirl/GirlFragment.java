package com.hwqgooo.databinding.ui.showgirl;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.bumptech.glide.Glide;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.FragmentGirlBinding;
import com.hwqgooo.databinding.databinding.ItemGirlBinding;
import com.hwqgooo.databinding.ui.fragment.BaseFragment;
import com.hwqgooo.databinding.utils.recyclerview.CommonAdapter;
import com.hwqgooo.databinding.utils.recyclerview.FirstItemSnapHelper;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.viewmodel.GirlVM;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

import java.util.List;
import java.util.Map;


/**
 * Created by weiqiang on 2016/7/2.
 */
public class GirlFragment extends BaseFragment {
    final static String TAG = GirlFragment.class.getSimpleName();
    FragmentGirlBinding binding;
    Context context;
    GirlVM girlVm;
    SnapHelper snapHelper = new FirstItemSnapHelper();

    RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int scrollState) {
            super.onScrollStateChanged(recyclerView, scrollState);
            switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                // 当ListView处于滑动状态时，停止加载图片，保证操作界面流畅
                Glide.with(getContext()).pauseRequests();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // 当ListView处于静止状态时，继续加载图片
                Glide.with(getContext()).resumeRequests();
                break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_girl, container, false);
        girlVm = ViewModelProviders.of(getActivity()).get(GirlVM.class);
        binding.setBasegirlvm(girlVm);
        girlVm.items.observe(this,
                girls -> {
                    ((CommonAdapter) binding.girlView.getAdapter()).submitList(girls);
                });
        binding.girlView.addOnScrollListener(listener);
        setSwipeRefreshLayout();
        setRecyclerView();

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View>
                    sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements,
                        sharedElementSnapshots);
//                Toast.makeText(context, "onSharedElementStart: ", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onSharedElementStart: ");
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View>
                    sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements,
                        sharedElementSnapshots);
//                Toast.makeText(context, "onSharedElementEnd: ", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onSharedElementEnd: ");
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                Toast.makeText(context, "onMapSharedElements: ", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onMapSharedElements: ");
            }
        });
        return binding.getRoot();
    }

    private void setSwipeRefreshLayout() {
        //设置进度条颜色
        binding.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
    }

    boolean isGrid = true;

    private void setRecyclerView() {
        binding.girlView.addOnItemTouchListener(new OnRcvClickListener<ItemGirlBinding>() {
            @Override
            public void onItemClick(ItemGirlBinding itemGirlBinding, int position) {
//                GirlPhotoFragment fragment = new GirlPhotoFragment(context, girlVm.getItems()
// .get(position));
//                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                fragment.show(fm, "fragment_girl_photo");
//                GirlPhotoActivity.launch(context, binding.girliv, position,
//                        girlVm.getItems().get(position));
                Log.d(TAG, "click-------------------------");
                RecyclerView.Adapter adapter = binding.girlView.getAdapter();
//                binding.girlView.setAdapter(null);
//                binding.girlView.setLayoutManager(null);
                RecyclerView.LayoutManager layoutManager = binding.girlView.getLayoutManager();
                if (isGrid) {
                    isGrid = false;
                    Log.d(TAG, "grid to liner");
                    snapHelper.attachToRecyclerView(binding.girlView);
                    girlVm.factory = LayoutManagers.linear(LinearLayoutManager.HORIZONTAL, false);
                } else {
                    isGrid = true;
                    Log.d(TAG, "liner to grid");
                    snapHelper.attachToRecyclerView(null);
                    girlVm.factory = LayoutManagers.staggeredGrid(2, LinearLayoutManager.VERTICAL);
                }

//                binding.girlView.setAdapter(adapter);
                binding.girlView.setLayoutManager(girlVm.factory.create(binding.girlView));
//                adapter.notifyDataSetChanged();
            }
        });
//        snapHelper.attachToRecyclerView(binding.girlView);
    }


    @Override
    public void onViewDisappear() {
    }

    boolean insave = false;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("insave", true);
        insave = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        insave = false;
    }


    @Override
    public void onViewFirstAppear() {
        Log.d(TAG, "onViewFirstAppear: ");
        //设置首次运行进度条刷新
        binding.swipeRefreshLayout.setProgressViewOffset(false,
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.girlView.removeOnScrollListener(listener);
    }
}
