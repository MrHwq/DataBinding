package com.hwqgooo.databinding.utils.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by weiqiang on 2016/7/6.
 */
public abstract class OnRcvClickListener implements
        RecyclerView.OnItemTouchListener {
    private GestureDetector mGestureDetector;

    public OnRcvClickListener(final RecyclerView recyclerView) {
        mGestureDetector = new GestureDetector(recyclerView.getContext(), new GestureDetector
                .SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    onItemClick((int) recyclerView.getChildLayoutPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // 长888按
                // 根据findChildViewUnder(float x, float y)来算出哪个item被选择了
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                // 有item被选则且监听器不为空触发长按事件
                if (childView != null) {
                    onItemLongClick(recyclerView.getChildLayoutPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public abstract void onItemClick(int position);

    public void onItemLongClick(int position) {
    }
}
