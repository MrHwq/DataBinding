package com.hwqgooo.databinding.command.appbarlayout;

import android.databinding.BindingAdapter;
import android.support.design.widget.AppBarLayout;
import android.util.Log;

import com.hwqgooo.databinding.command.ReplyCommand;

/**
 * Created by weiqiang on 2016/7/5.
 */
public class ViewBindingAdapter {
    public static final String TAG = "AppbarBindingAdapter";


    @BindingAdapter(value = {"onExpanded", "onCollapsed", "onInternediate"},
            requireAll = false)
    public static void initToolbar(final AppBarLayout appBarLayout,
                                   final ReplyCommand onExpanded,
                                   final ReplyCommand onCollapsed,
                                   final ReplyCommand onInternediate) {
        Log.d(TAG, "addOnOffsetChangedListener: ");
        if (onExpanded == null && onCollapsed == null && onInternediate == null) {
            return;
        }
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener(onExpanded,
                onCollapsed, onInternediate));
    }

    static class MyOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
        final static int EXPANDED = 0;
        final static int COLLAPSED = 1;
        final static int INTERNEDIATE = 2;
        int state = EXPANDED;
        final ReplyCommand onExpanded;
        final ReplyCommand onCollapsed;
        final ReplyCommand onInternediate;

        public MyOnOffsetChangedListener(final ReplyCommand onExpanded,
                                         final ReplyCommand onCollapsed,
                                         final ReplyCommand onInternediate) {
            this.onExpanded = onExpanded;
            this.onCollapsed = onCollapsed;
            this.onInternediate = onInternediate;
        }

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (verticalOffset == 0) {
                if (state != EXPANDED) {
                    state = EXPANDED;
                    if (onExpanded != null) {
                        onExpanded.execute();
                    }
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != COLLAPSED) {
                    state = COLLAPSED;
                    if (onCollapsed != null) {
                        onCollapsed.execute();
                    }
                }
            } else {
                if (state != INTERNEDIATE) {
                    if (state != COLLAPSED) {
                        //
                    }
                    state = INTERNEDIATE;
                    if (onInternediate != null) {
                        onInternediate.execute();
                    }
                }
            }
        }
    }
}
