package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
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

    //第一次运行准备数据
    public abstract void onStart();

    //彻底销毁vm
    public abstract void onDestory();

    //暂停vm的一切活动
    public abstract void onStop();

    //重置vm与context有关的变量
    public abstract void onRestart(Context context);

    public ObservableList<Girl> getGirls() {
        return girls;
    }
}
