package com.hwqgooo.databinding.bindingcollectionadapter.itemviews;


import com.hwqgooo.databinding.bindingcollectionadapter.BaseItemViewSelector;
import com.hwqgooo.databinding.bindingcollectionadapter.ItemView;

/**
 * An {@link me.tatarka.bindingcollectionadapter.ItemViewSelector} that selects item views by
 * delegating to each item. Items must implement {@link ItemViewModel}. <b>Important note:</b> If
 * you plan to use this in a {@link android.widget.ListView}, you <em>must</em> subclass this and
 * implement {@link ItemViewSelector#viewTypeCount()} to return the number of different types of
 * views possible in your list.
 */
public class ItemViewModelSelector<T extends ItemViewModel> extends BaseItemViewSelector<T> {
    @Override
    public void select(ItemView itemView, int position, T item) {
        item.itemView(itemView);
    }
}
