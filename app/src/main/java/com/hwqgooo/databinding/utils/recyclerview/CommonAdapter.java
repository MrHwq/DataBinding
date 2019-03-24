package com.hwqgooo.databinding.utils.recyclerview;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hwqgooo.jetpack.utils.recyclerview.ItemView;

import java.util.List;

public class CommonAdapter<T> extends PagedListAdapter<T, RecyclerView.ViewHolder> {
    private static final Object DATA_INVALIDATION = new Object();
    private final ItemViewArg<T> itemViewArg;
    private LayoutInflater inflater;
    private ViewHolderFactory viewHolderFactory;

    public CommonAdapter(@NonNull ItemViewArg<T> arg, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.itemViewArg = arg;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return onCreateViewHolder(binding);

    }

    private RecyclerView.ViewHolder onCreateViewHolder(ViewDataBinding binding) {
        if (viewHolderFactory != null) {
            return viewHolderFactory.createViewHolder(binding);
        } else {
            return new BindingViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        T item = getItem(position);
        ViewDataBinding bind = DataBindingUtil.getBinding(holder.itemView);
        onBindBinding(bind, itemViewArg.bindingVariable(), itemViewArg.layoutRes(), position, item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position,
                                 @NonNull List<Object> payloads) {
        if (isForDataBinding(payloads)) {
            ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
            binding.executePendingBindings();
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    private boolean isForDataBinding(List<Object> payloads) {
        if (payloads == null || payloads.size() == 0) {
            return false;
        }
        for (int i = 0; i < payloads.size(); i++) {
            Object obj = payloads.get(i);
            if (obj != DATA_INVALIDATION) {
                return false;
            }
        }
        return true;
    }

    private void onBindBinding(ViewDataBinding binding, int bindingVariable,
                               @LayoutRes int layoutRes, int position, T item) {
        if (bindingVariable != ItemView.BINDING_VARIABLE_NONE) {
            boolean result = binding.setVariable(bindingVariable, item);
            binding.executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        itemViewArg.select(position, getItem(position));
        return itemViewArg.layoutRes();
    }

    public void setViewHolderFactory(@Nullable ViewHolderFactory factory) {
        viewHolderFactory = factory;
    }

    public interface ViewHolderFactory {
        RecyclerView.ViewHolder createViewHolder(ViewDataBinding binding);
    }

    private static class BindingViewHolder extends RecyclerView.ViewHolder {
        BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }
    }
}
