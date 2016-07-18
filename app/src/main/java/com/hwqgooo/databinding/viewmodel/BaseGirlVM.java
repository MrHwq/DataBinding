package com.hwqgooo.databinding.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.hwqgooo.databinding.bindingcollectionadapter.ItemView;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.bean.Girl;

/**
 * Created by weiqiang on 2016/7/9.
 */
public abstract class BaseGirlVM {
    public ObservableList<Girl> girls = new ObservableArrayList<>();
    public ObservableBoolean isRefreshing = new ObservableBoolean(false);
    public ItemView itemView;
    public ReplyCommand onRefresh;
    public ReplyCommand onLoadMore;
    public ReplyCommand<Integer> onItemClick;

    public abstract void onDestory();

    public ObservableList<Girl> getGirls() {
        return girls;
    }
}
