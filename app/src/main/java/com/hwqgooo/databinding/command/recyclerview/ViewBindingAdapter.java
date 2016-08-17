package com.hwqgooo.databinding.command.recyclerview;

import android.databinding.BindingAdapter;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvClickListener;
import com.hwqgooo.databinding.utils.recyclerview.OnRcvScrollListener;

/**
 * Created by weiqiang on 2016/7/5.
 */
public class ViewBindingAdapter {
    public static final String TAG = "RcvViewBindingAdapter";


//    @BindingAdapter({"itemView", "viewModels"})
//    public static void addViews(ViewGroup viewGroup,
//                                final ItemView itemView,
//                                final ObservableList<ViewModel> viewModelList) {
//        if (viewModelList != null && !viewModelList.isEmpty()) {
//            viewGroup.removeAllViews();
//            for (ViewModel viewModel : viewModelList) {
//                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup
//                                .getContext()),
//                        itemView.layoutRes(), viewGroup, true);
//                binding.setVariable(itemView.bindingVariable(), viewModel);
//            }
//        }
//    }

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
            public void onItemClick(ViewDataBinding binding, int position) {
                onItemClickCommand.execute(position);
            }
        });
    }
}
