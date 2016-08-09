package com.hwqgooo.databinding.ui.showmzitu;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hwqgooo.databinding.viewmodel.MzituVM;

/**
 * Created by weiqiang on 2016/7/9.
 */
public class MzituFragment extends BaseFragment {
    String TAG = "MzituFragment";
    FragmentGirlBinding binding;
    Context context;
    MzituVM mzituVM;
    String title = "Mzitu";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_girl, container, false);
        Bundle argument = getArguments();
        title = argument.getString("title");
        TAG = TAG + title;
        mzituVM = MzituVM.getInstance(context, title);
        Log.d(TAG, "onCreateView: " + title);
        binding.setVariable(BR.basegirlvm, mzituVM);
        binding.executePendingBindings();
//        binding.setGirlvm(girlVm);
        setSwipeRefreshLayout();
        setRecyclerView();

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
//                bundle.putParcelable("girl", mzituVM.getGirls().get(position));
//                fragment.setArguments(bundle);
//                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                fragment.show(fm, "fragment_girl_photo");
                View iv = childView.findViewById(R.id.girliv);
                if (iv == null) {
                    iv = childView;
                }
                GirlPhotoActivity.launch(context, iv, position,
                        mzituVM.getGirls().get(position));
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: " + title);
        context = null;
        if (mzituVM != null) {
            if (!insave) {
                mzituVM.onDestory();
                mzituVM = null;
            } else {
                mzituVM.onStop();
            }
        }
    }

    boolean insave = false;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: " + outState + ".." + insave);
        outState.putBoolean("insave", true);
        insave = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        insave = false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + savedInstanceState + ".." + insave);
        if (savedInstanceState != null && savedInstanceState.getBoolean("insave")) {
            Log.d(TAG, "onActivityCreated: " + insave);
            mzituVM.onRestart(context);
        }
        insave = false;
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
        mzituVM.onStart();
    }

    @Override
    public String getTitle() {
        Log.d(TAG, "getTitle: " + title);
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
