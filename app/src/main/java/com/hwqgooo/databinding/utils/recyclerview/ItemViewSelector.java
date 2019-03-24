package com.hwqgooo.jetpack.utils.recyclerview;

public interface ItemViewSelector<T> {
    /**
     * Called on each item in the collection, allowing you to modify the given {@link ItemView}.
     * Note that you should not do complex processing in this method as it's called many times.
     */
    void select(ItemView itemView, int position, T item);

    /**
     * Return the number of <em>different</em> layouts that you will be displaying. This is only
     * required for using in a {@link android.widget.ListView}.
     */
    int viewTypeCount();
}
