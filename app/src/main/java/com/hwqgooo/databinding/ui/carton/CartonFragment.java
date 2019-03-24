package com.hwqgooo.databinding.ui.carton;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.FragmentGirlBinding;
import com.hwqgooo.databinding.databinding.ItemGirlBinding;
import com.hwqgooo.databinding.ui.fragment.BaseFragment;
import com.hwqgooo.databinding.utils.recyclerview.CommonAdapter;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.viewmodel.CartonAllVM;

/**
 * Created by weiqiang on 2016/7/9.
 */
public class CartonFragment extends BaseFragment {
    final String TAG = getClass().getSimpleName();
    FragmentGirlBinding binding;
    Context context;
    CartonAllVM cartonAllVM;
    boolean insave = false;

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
        cartonAllVM = ViewModelProviders.of(getActivity()).get(CartonAllVM.class);
        binding.setBasegirlvm(cartonAllVM);
        cartonAllVM.items.observe(this,
                pagedList -> {
                    System.out.println("=====" + binding.girlView.getAdapter());
                    ((CommonAdapter) binding.girlView.getAdapter()).submitList(pagedList);
                });
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
        binding.girlView.addOnItemTouchListener(
                new OnRcvClickListener<ItemGirlBinding>() {
                    @Override
                    public void onItemClick(ItemGirlBinding binding, int position) {
//                GirlPhotoFragment fragment = new GirlPhotoFragment();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("girl", mzituVM.getItems().get(position));
//                fragment.setArguments(bundle);
//                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                fragment.show(fm, "fragment_girl_photo");
                        CartonChapterActivity.launch(context, binding.girliv, position + 1);
//                        MzituGalleryActivity.launch(context, binding.girliv,
//                                mzituVM.galleries.get(position));
//                        GirlPhotoActivity.launch(context, binding.girliv, position,
//                                mzituVM.getItems().get(position));
                    }
                }

        );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        context = null;

    }

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
//            cartonAllVM.onRestart(context);
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
    }
}
