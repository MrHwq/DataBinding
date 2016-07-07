package com.hwqgooo.databinding.bindingcollectionadapter.factories;

import android.widget.AdapterView;

import com.hwqgooo.databinding.bindingcollectionadapter.BindingListViewAdapter;
import com.hwqgooo.databinding.bindingcollectionadapter.ItemViewArg;


public interface BindingAdapterViewFactory {
    BindingAdapterViewFactory DEFAULT = new BindingAdapterViewFactory() {
        @Override
        public <T> BindingListViewAdapter<T> create(AdapterView adapterView, ItemViewArg<T> arg) {
            return new BindingListViewAdapter<>(arg);
        }
    };

    <T> BindingListViewAdapter<T> create(AdapterView adapterView, ItemViewArg<T> arg);
}
