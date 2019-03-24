package com.hwqgooo.databinding.ui.showgirl;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.utils.recyclerview.CommonAdapter;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.databinding.viewmodel.GirlVM;

/**
 * Created by weiqiang on 2016/7/6.
 * 主要是为了自定义修改ImageView的尺寸，不然可不需要实现这个类
 */
public class GirlAdapter extends CommonAdapter<Girl> {
    public static final String TAG = GirlAdapter.class.getName();

    public static DiffUtil.ItemCallback DIFF_CALLBACK = new DiffUtil.ItemCallback<Girl>() {
        @Override
        public boolean areItemsTheSame(@NonNull Girl oldBean, @NonNull Girl newBean) {
            return TextUtils.equals(oldBean.getUrl(), newBean.getUrl());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Girl oldBean, @NonNull Girl newBean) {
            return TextUtils.equals(oldBean.getUrl(), newBean.getUrl());
        }
    };

    public GirlAdapter(@NonNull ItemViewArg<Girl> arg) {
        super(arg, GirlVM.diff);
    }

//    @Override
//    public ViewDataBinding onCreateBinding(LayoutInflater inflater, @LayoutRes int layoutId,
//                                           ViewGroup viewGroup) {
//        ItemGirlBinding binding = (ItemGirlBinding) super.onCreateBinding(inflater, layoutId,
//                viewGroup);
//        ViewGroup.LayoutParams params = binding.girliv.getLayoutParams();
//        int widthPixels = binding.girliv.getContext().getResources().getDisplayMetrics()
//                .widthPixels;
//        float scale = (float) (Math.random() + 1);
//        while (scale > 1.6 || scale < 1.1) {
//            scale = (float) (Math.random() + 1);
//        }
//        params.height = (int) (widthPixels * scale * 0.448);
//        binding.girliv.setLayoutParams(params);
//        return binding;
//    }
}
