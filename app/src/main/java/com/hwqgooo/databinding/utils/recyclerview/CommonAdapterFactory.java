package com.hwqgooo.databinding.utils.recyclerview;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

public interface CommonAdapterFactory {
    CommonAdapterFactory DEFAULT = new CommonAdapterFactory() {
        @Override
        public <T> CommonAdapter<T> create(RecyclerView recyclerView,
                                           ItemViewArg<T> arg,
                                           DiffUtil.ItemCallback<T> diffCallback) {
            return new CommonAdapter<>(arg, diffCallback);
        }
    };

    <T> CommonAdapter<T> create(RecyclerView recyclerView,
                                ItemViewArg<T> arg,
                                DiffUtil.ItemCallback<T> diffCallback);
}
