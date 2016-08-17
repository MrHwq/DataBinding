package com.hwqgooo.databinding.ui.showgirl;

import android.support.annotation.NonNull;

import com.hwqgooo.databinding.model.bean.Girl;

import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

/**
 * Created by weiqiang on 2016/7/6.
 * 主要是为了自定义修改ImageView的尺寸，不然可不需要实现这个类
 */
public class GirlAdapter extends BindingRecyclerViewAdapter<Girl> {
    public static final String TAG = "GirlAdapter";

    public GirlAdapter(@NonNull ItemViewArg<Girl> arg) {
        super(arg);
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
