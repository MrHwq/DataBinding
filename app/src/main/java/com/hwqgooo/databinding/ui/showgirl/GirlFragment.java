package com.hwqgooo.databinding.ui.showgirl;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.FragmentGirlBinding;
import com.hwqgooo.databinding.ui.fragment.BaseFragment;
import com.hwqgooo.databinding.ui.showgirlphoto.GirlPhotoActivity;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.viewmodel.GirlVM;

import java.util.List;
import java.util.Map;

/**
 * Created by weiqiang on 2016/7/2.
 */
public class GirlFragment extends BaseFragment {
    final static String TAG = "GirlFragment";
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
        binding.setVariable(BR.basegirlvm, girlVm);
        binding.executePendingBindings();
//        binding.setGirlvm(girlVm);
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

    private void setRecyclerView() {
        binding.girlView.addOnItemTouchListener(new OnRcvClickListener(binding.girlView) {
            @Override
            public void onItemClick(View childView, int position) {
//                GirlPhotoFragment fragment = new GirlPhotoFragment();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("girl", girlVm.getGirls().get(position));
//                fragment.setArguments(bundle);
//                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                fragment.show(fm, "fragment_girl_photo");
                final Intent intent = new Intent(context, GirlPhotoActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("girl", girlVm.getGirls().get(position));

                Log.d(TAG, "onItemClick: " + girlVm.getGirls().get(position).getDesc());
                final ActivityOptionsCompat options;

                if (Build.VERSION.SDK_INT >= 21) {
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(), childView, girlVm.getGirls().get(position).getDesc());
                } else {
                    options = ActivityOptionsCompat.makeScaleUpAnimation(
                            childView, 0, 0, childView.getWidth(), childView.getHeight());
                }

                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        girlVm.onDestory();
    }

    @Override
    public void onViewDisappear() {
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
        girlVm.onRefresh.execute();
    }

    @Override
    public String getTitle() {
        return "Girl";
    }

    @Override
    public void setTitle(String title) {
    }
}
