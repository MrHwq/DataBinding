package com.hwqgooo.databinding.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

/**
 * Created by weiqiang on 2016/7/9.
 */
public abstract class BaseGirlVM<T> extends ViewModel {
    public static DiffUtil.ItemCallback diff = new DiffUtil.ItemCallback<Girl>() {
        @Override
        public boolean areItemsTheSame(@NonNull Girl oldBean, @NonNull Girl newBean) {
            return TextUtils.equals(oldBean.getUrl(), newBean.getUrl());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Girl oldBean, @NonNull Girl newBean) {
            return TextUtils.equals(oldBean.getDesc(), newBean.getDesc());
        }
    };
    public ObservableBoolean isRefreshing = new ObservableBoolean(false);
    public LiveData<PagedList<T>> items;
    public ItemViewArg itemView;
    public ReplyCommand onRefresh;
    public ReplyCommand onLoadMore;
    public ReplyCommand<Integer> onItemClick;
    public LayoutManagers.LayoutManagerFactory factory;

    public LiveData<PagedList<T>> getItems() {
        return items;
    }
}
