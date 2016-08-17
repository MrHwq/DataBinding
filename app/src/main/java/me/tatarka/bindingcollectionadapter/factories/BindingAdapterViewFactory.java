package me.tatarka.bindingcollectionadapter.factories;

import android.widget.AdapterView;

import me.tatarka.bindingcollectionadapter.BindingListViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;


public interface BindingAdapterViewFactory {
    BindingAdapterViewFactory DEFAULT = new BindingAdapterViewFactory() {
        @Override
        public <T> BindingListViewAdapter<T> create(AdapterView adapterView, ItemViewArg<T> arg) {
            return new BindingListViewAdapter<>(arg);
        }
    };

    <T> BindingListViewAdapter<T> create(AdapterView adapterView, ItemViewArg<T> arg);
}
