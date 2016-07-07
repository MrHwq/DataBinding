package com.hwqgooo.databinding.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.FragmentGirlPhotoBinding;
import com.hwqgooo.databinding.model.bean.Girl;

/**
 * Created by weiqiang on 2016/7/6.
 */
public class GirlPhotoFragment extends DialogFragment {
    final static String TAG = "GirlPhotoFragment";
    Girl girl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        girl = bundle.getParcelable("girl");
        FragmentGirlPhotoBinding binding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_girl_photo, container, false);
        binding.setGirl(girl);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
