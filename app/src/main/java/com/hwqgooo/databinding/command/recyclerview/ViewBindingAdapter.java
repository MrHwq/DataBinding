package com.hwqgooo.databinding.command.recyclerview;

import android.databinding.BindingAdapter;
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
    public static void onLoadMoreCommand(RecyclerView recyclerView, final ReplyCommand
            onLoadMoreCommand) {
        Log.d(TAG, "onLoadMoreCommand: ");
        recyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                if (onLoadMoreCommand != null) {
                    onLoadMoreCommand.execute();
                }
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

        recyclerView.addOnItemTouchListener(new OnRcvClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                if (onItemClickCommand != null) {
                    onItemClickCommand.execute(position);
                }
            }
        });
    }
}
