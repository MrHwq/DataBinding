package com.hwqgooo.databinding.ui.showgirl;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.databinding.ItemGirlBinding;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.utils.recyclerview.BaseRecyclerViewAdapter;
import com.hwqgooo.databinding.utils.recyclerview.ViewHolderInject;

import java.util.List;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class GirlAdapter extends BaseRecyclerViewAdapter<Girl> {
    public GirlAdapter(Context context) {
        super(context);
    }

    public GirlAdapter(Context context, List<Girl> data) {
        super(context, data);
    }

    @Override
    public ViewHolderInject<Girl> getNewHolder(LayoutInflater layoutInflater, ViewGroup parent,
                                               int viewType) {
        ItemGirlBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_girl,
                parent,
                false);
        GirlHolder holder = new GirlHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    public class GirlHolder extends ViewHolderInject<Girl> {
        ItemGirlBinding binding;

        public GirlHolder(View itemView) {
            super(itemView);
        }

        public void setBinding(ItemGirlBinding binding) {
            this.binding = binding;
        }

        @Override
        public void loadData(Girl data, int position) {
//            binding.setGirl(data);
            ViewGroup.LayoutParams params = binding.girliv.getLayoutParams();
            int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
            float scale = (float) (Math.random() + 1);
            while(scale>1.6||scale<1.1){
                scale = (float) (Math.random() + 1);
            }
            params.height = (int) (widthPixels * scale * 0.448);
            binding.girliv.setLayoutParams(params);
            binding.setVariable(BR.girl, data);
            binding.executePendingBindings();
        }
    }
}
