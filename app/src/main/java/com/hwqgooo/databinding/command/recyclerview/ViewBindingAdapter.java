package com.hwqgooo.databinding.command.recyclerview;

import android.databinding.BindingAdapter;
import android.databinding.ViewDataBinding;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.utils.recyclerview.CommonAdapter;
import com.hwqgooo.databinding.utils.recyclerview.CommonAdapterFactory;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvScrollListener;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

/**
 * Created by weiqiang on 2016/7/5.
 */
public class ViewBindingAdapter {
    public static final String TAG = "RcvViewBindingAdapter";

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemView", "adapter", "diff", "viewHolder"}, requireAll = false)
    public static <T> void setAdapter(RecyclerView recyclerView,
                                      ItemViewArg<T> arg,
                                      CommonAdapterFactory factory,
                                      DiffUtil.ItemCallback diff,
                                      CommonAdapter.ViewHolderFactory viewHolderFactory) {
        if (arg == null) {
            return;
//            throw new IllegalArgumentException("itemView must not be null");
        }
        if (factory == null) {
            factory = CommonAdapterFactory.DEFAULT;
        }
        CommonAdapter<T> adapter = (CommonAdapter<T>) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = factory.create(recyclerView, arg, diff);
            adapter.setViewHolderFactory(viewHolderFactory);
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers
            .LayoutManagerFactory layoutManagerFactory) {
        if (recyclerView != null && layoutManagerFactory != null) {
            recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
        }
    }

    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(RecyclerView recyclerView,
                                         final ReplyCommand onLoadMoreCommand) {
        Log.d(TAG, "onLoadMoreCommand: " + recyclerView);
        if (onLoadMoreCommand == null) {
            return;
        }
        recyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                onLoadMoreCommand.execute();
            }

            @Override
            public int setRestItem() {
                return 6;
            }
        });
    }

    @BindingAdapter(value = {"onItemClickCommand"}, requireAll = false)
    public static void onItemClickCommand(final RecyclerView recyclerView,
                                          final ReplyCommand<Integer> onItemClickCommand) {
        Log.d(TAG, "onItemClickCommand: " + recyclerView);
        if (onItemClickCommand == null) {
            return;
        }
        recyclerView.addOnItemTouchListener(new OnRcvClickListener() {
            @Override
            public void onItemClick(ViewDataBinding binding, int position) throws Exception {
                onItemClickCommand.execute(position);
            }
        });
    }
}
