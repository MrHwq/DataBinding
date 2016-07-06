package com.hwqgooo.databinding.ui;

import android.support.v4.app.Fragment;

/**
 * Created by weiqiang on 2016/7/6.
 */
public abstract class BaseFragment extends Fragment {
    private boolean isLayoutUpdating = false;
    private boolean isViewFirstAppear = true;

    public abstract void onViewDisappear();

    public abstract void onViewFirstAppear();

    protected boolean isLayoutUpdating() {
        return this.isLayoutUpdating;
    }

    @Override
    public void onPause() {
        super.onPause();
        onViewDisappear();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onViewAppear();
            return;
        }
        onViewDisappear();
    }

    public void onViewAppear() {
        if (this.isViewFirstAppear) {
            onViewFirstAppear();
            this.isViewFirstAppear = false;
        }
    }


    public void setLayoutUpdating(boolean paramBoolean) {
        this.isLayoutUpdating = paramBoolean;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            if (isVisibleToUser) {
                onViewAppear();
            }
        } else {
            return;
        }
        onViewDisappear();
    }
}
