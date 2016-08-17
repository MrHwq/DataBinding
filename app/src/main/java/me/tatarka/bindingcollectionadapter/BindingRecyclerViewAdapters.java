package me.tatarka.bindingcollectionadapter;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.tatarka.bindingcollectionadapter.factories.BindingRecyclerViewAdapterFactory;


/**
 * @see {@link BindingCollectionAdapters}
 */
public class BindingRecyclerViewAdapters {
    public static final String TAG = "BindingRcvAdapters";

    // RecyclerView
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemView", "items", "adapter", "itemIds", "viewHolder"},
            requireAll = false)
    public static <T> void setAdapter(RecyclerView recyclerView,
                                      ItemViewArg<T> arg,
                                      List<T> items,
                                      BindingRecyclerViewAdapterFactory factory,
                                      BindingRecyclerViewAdapter.ItemIds<T> itemIds,
                                      BindingRecyclerViewAdapter.ViewHolderFactory
                                              viewHolderFactory) {
        if (arg == null) {
            return;
//            throw new IllegalArgumentException("itemView must not be null");
        }
        if (factory == null) {
            factory = BindingRecyclerViewAdapterFactory.DEFAULT;
        }
        BindingRecyclerViewAdapter<T> adapter = (BindingRecyclerViewAdapter<T>) recyclerView
                .getAdapter();
        if (adapter == null) {
            adapter = factory.create(recyclerView, arg);
            adapter.setItems(items);
            adapter.setItemIds(itemIds);
            adapter.setViewHolderFactory(viewHolderFactory);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setItems(items);
        }
    }

//    @SuppressWarnings("unchecked")
//    @BindingAdapter(value = {"itemView", "items", "adapter", "itemIds"}, requireAll = false)
//    public static <T> void setAdapter(RecyclerView recyclerView,
//                                      ItemView arg,
//                                      List<T> items,
//                                      BindingRecyclerViewAdapterFactory factory,
//                                      BindingRecyclerViewAdapter.ItemIds<T> itemIds) {
//        Log.d(TAG, "setAdapter: itemview " + arg);
//        Log.d(TAG, "setAdapter: items " + items);
//        if (arg == null) {
//            return;
//            throw new IllegalArgumentException("itemView must not be null");
//        }
//        if (factory == null) {
//            factory = BindingRecyclerViewAdapterFactory.DEFAULT;
//        }
//        BindingRecyclerViewAdapter<T> adapter = (BindingRecyclerViewAdapter<T>) recyclerView
//                .getAdapter();
//        if (adapter == null) {
//            ItemViewArg<T> itemView = ItemViewArg.of(arg);
//            adapter = factory.create(recyclerView, itemView);
//            adapter.setItems(items);
//            adapter.setItemIds(itemIds);
//            recyclerView.setAdapter(adapter);
//        } else {
//            adapter.setItems(items);
//        }
//    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers
            .LayoutManagerFactory layoutManagerFactory) {
        if (recyclerView != null && layoutManagerFactory != null) {
            recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
        }
    }

    @BindingConversion
    public static BindingRecyclerViewAdapterFactory toRecyclerViewAdapterFactory(final String
                                                                                         className) {
        return new BindingRecyclerViewAdapterFactory() {
            @Override
            public <T> BindingRecyclerViewAdapter<T> create
                    (RecyclerView recyclerView, ItemViewArg<T> arg) {
                return Utils.createClass(className, arg);
            }
        };
    }
}
