package com.hwqgooo.jetpack.utils.recyclerview;

import android.support.annotation.IntDef;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A collection of factories to create RecyclerView LayoutManagers so that you can easily set them
 * in your layout.
 */
public class ItemDecorations {
    protected ItemDecorations() {
    }

    public static ItemDecorationFactory divider() {
        return recyclerView -> new DividerItemDecoration(recyclerView.getContext(), RecyclerView.VERTICAL);
    }

    public static ItemDecorationFactory divider(@Orientation final int orientation) {
        return recyclerView -> new DividerItemDecoration(recyclerView.getContext(), orientation);
    }

    public interface ItemDecorationFactory {
        RecyclerView.ItemDecoration create(RecyclerView recyclerView);
    }

    @IntDef({LinearLayoutManager.HORIZONTAL, LinearLayoutManager.VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }
}
