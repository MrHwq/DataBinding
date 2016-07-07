package com.hwqgooo.databinding.ui.showgirl;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.FragmentGirlBinding;
import com.hwqgooo.databinding.ui.fragment.BaseFragment;
import com.hwqgooo.databinding.ui.fragment.GirlPhotoFragment;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.viewmodel.GirlVM;

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
        binding.setVariable(BR.girlvm, girlVm);
        binding.executePendingBindings();
//        binding.setGirlvm(girlVm);
        setSwipeRefreshLayout();
        setRecyclerView();
        return binding.getRoot();
    }

    private void setRecyclerView() {
        binding.girlView.addOnItemTouchListener(new OnRcvClickListener(binding.girlView) {
            @Override
            public void onItemClick(int position) {
                GirlPhotoFragment fragment = new GirlPhotoFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("girl", girlVm.getGirls().get(position));
                fragment.setArguments(bundle);
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fragment.show(fm, "fragment_girl_photo");
            }
        });
    }

    private void setSwipeRefreshLayout() {
        //设置首次运行进度条刷新
//        binding.swipeRefreshLayout.setProgressViewOffset(false,
//                0,
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                        24,
//                        getResources().getDisplayMetrics()));
        //设置进度条颜色
        binding.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
    }

    @Override
    public void onViewDisappear() {

    }

    @Override
    public void onViewFirstAppear() {
        Log.d(TAG, "onViewFirstAppear: ");
        girlVm.onRefresh.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        girlVm.onDestory();
    }
}
