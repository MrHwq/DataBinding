package com.hwqgooo.databinding.utils.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;

import com.hwqgooo.jetpack.utils.recyclerview.ItemView;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewSelector;

public class ItemViewClassSelector<T> implements ItemViewSelector<T> {

    private final SimpleArrayMap<Class<? extends T>, ItemView> itemViewMap;

    ItemViewClassSelector(SimpleArrayMap<Class<? extends T>, ItemView> itemViewMap) {
        this.itemViewMap = itemViewMap;
    }

    /**
     * Returns a new builder to construct an {@code ItemViewClassSelector} instance.
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    @Override
    public void select(com.hwqgooo.jetpack.utils.recyclerview.ItemView itemView, int position, T item) {
        ItemView itemItemView = itemViewMap.get(item.getClass());
        if (itemItemView != null) {
            itemView.set(itemItemView.bindingVariable(), itemItemView.layoutRes());
        } else {
            throw new IllegalArgumentException("Missing class for item " + item);
        }
    }

    @Override
    public int viewTypeCount() {
        return itemViewMap.size();
    }

    public static class Builder<T> {
        private final SimpleArrayMap<Class<? extends T>, ItemView> itemViewMap = new
                SimpleArrayMap<>();

        Builder() {
        }

        public Builder<T> put(@NonNull Class<? extends T> itemClass, int bindingVariable,
                              @LayoutRes int layoutRes) {
            itemViewMap.put(itemClass, com.hwqgooo.jetpack.utils.recyclerview.ItemView.of(bindingVariable, layoutRes));
            return this;
        }

        public Builder<T> put(@NonNull Class<? extends T> itemClass, @NonNull ItemView itemView) {
            itemViewMap.put(itemClass, itemView);
            return this;
        }

        public ItemViewClassSelector<T> build() {
            return new ItemViewClassSelector<>(itemViewMap);
        }
    }
}
