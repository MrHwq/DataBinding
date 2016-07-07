package com.hwqgooo.databinding.bindingcollectionadapter.factories;

import android.support.v7.widget.RecyclerView;

import com.hwqgooo.databinding.bindingcollectionadapter.BindingRecyclerViewAdapter;
import com.hwqgooo.databinding.bindingcollectionadapter.ItemViewArg;


public interface BindingRecyclerViewAdapterFactory {
    BindingRecyclerViewAdapterFactory DEFAULT = new BindingRecyclerViewAdapterFactory() {
        @Override
        public <T> BindingRecyclerViewAdapter<T> create(RecyclerView recyclerView, ItemViewArg<T>
                arg) {
            return new BindingRecyclerViewAdapter<>(arg);
        }
    };

    <T> BindingRecyclerViewAdapter<T> create(RecyclerView recyclerView, ItemViewArg<T> arg);
}
