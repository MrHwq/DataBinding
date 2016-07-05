package com.hwqgooo.databinding.utils.recyclerview;

import android.content.Context;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by weiqiang on 2016/6/6.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolderInject> {
    public static final String TAG = "BaseRecyclerViewAdapter";
    public final LayoutInflater mLayoutInflater;
    public final Context context;
    public ObservableList<T> mData;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public BaseRecyclerViewAdapter(Context context, ObservableList<T> data) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
        mData.addOnListChangedCallback(callback);
        if (!mData.isEmpty()) {
            notifyDataSetChanged();
        }
    }

    public void setData(ObservableList<T> mData) {
        this.mData = mData;
        mData.addOnListChangedCallback(callback);
        if (!mData.isEmpty()) {
            notifyDataSetChanged();
        }
    }

    ObservableList.OnListChangedCallback<ObservableList<T>> callback = new ObservableList
            .OnListChangedCallback<ObservableList<T>>() {
        @Override
        public void onChanged(ObservableList<T> girls) {
            Log.d(TAG, "onChanged: ");
        }

        @Override
        public void onItemRangeChanged(ObservableList<T> girls, int i, int i1) {
            notifyItemRangeChanged(i, i1);
            Log.d(TAG, "onItemRangeChanged: " + i + ",," + i1);
        }

        @Override
        public void onItemRangeInserted(ObservableList<T> girls, int i, int i1) {
            Log.d(TAG, "onItemRangeInserted: " + i + ",," + i1);
            notifyItemRangeInserted(i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableList<T> girls, int i, int i1, int i2) {
            Log.d(TAG, "onItemRangeMoved: " + i + ",," + i1);
            notifyItemMoved(i, i1);
        }

        @Override
        public void onItemRangeRemoved(ObservableList<T> girls, int i, int i1) {
            Log.d(TAG, "onItemRangeRemoved: " + i + ",," + i1);
            notifyItemRangeRemoved(i, i1);
        }
    };

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
