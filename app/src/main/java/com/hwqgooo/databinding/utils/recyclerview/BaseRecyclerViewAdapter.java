package com.hwqgooo.databinding.utils.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by weiqiang on 2016/6/6.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolderInject> {
    public final LayoutInflater mLayoutInflater;
    public final Context context;
    public List<T> mData;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public BaseRecyclerViewAdapter(Context context, List<T> data) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
    }

    public void setData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderInject<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return getNewHolder(mLayoutInflater, parent, viewType);
    }

    public abstract ViewHolderInject<T> getNewHolder(LayoutInflater layoutInflater, ViewGroup
            parent, int viewType);

    @Override
    public void onBindViewHolder(ViewHolderInject holder, int position) {
        holder.loadData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}
