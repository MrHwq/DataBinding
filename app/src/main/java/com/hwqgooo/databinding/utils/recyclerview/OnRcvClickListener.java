package com.hwqgooo.databinding.utils.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by weiqiang on 2016/7/6.
 */
public abstract class OnRcvClickListener<T extends ViewDataBinding>
        implements RecyclerView.OnItemTouchListener {
    private GestureDetector mGestureDetector;

    public abstract void onItemClick(T binding, int position) throws Exception;

    public void onItemLongClick(T binding, int position) {
    }

    @Override
    public boolean onInterceptTouchEvent(final RecyclerView rv, MotionEvent e) {
        if (mGestureDetector == null) {
            mGestureDetector = new MyGestureDetector(rv);
        }
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(final RecyclerView rv, MotionEvent e) {
        if (mGestureDetector == null) {
            mGestureDetector = new MyGestureDetector(rv);
        }
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    class MyGestureDetector extends android.view.GestureDetector {
        public MyGestureDetector(final RecyclerView rv) {
            super(rv.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView == null) {
                        return false;
                    }
                    try {
                        onItemClick((T) DataBindingUtil.getBinding(childView),
                                rv.getChildLayoutPosition(childView));
                        return true;
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        return false;
                    }
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    // 根据findChildViewUnder(float x, float y)来算出哪个item被选择了
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    // 有item被选则且监听器不为空触发长按事件
                    if (childView == null) {
                        return;
                    }
                    try {
                        onItemLongClick((T) DataBindingUtil.getBinding(childView),
                                rv.getChildLayoutPosition(childView));
                    } catch (Exception exception) {
                        throw exception;
                    }
                }
            });
        }
    }
}
