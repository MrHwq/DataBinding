package com.hwqgooo.databinding.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by weiqiang on 2016/7/6.
 */
public abstract class BaseFragment extends Fragment {
    private boolean isViewFirstAppear = true;

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

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onViewAppear();
            return;
        }
        onViewDisappear();
    }

    @Override
    public void onPause() {
        super.onPause();
        onViewDisappear();
    }

    public void onViewAppear() {
        if (this.isViewFirstAppear) {
            onViewFirstAppear();
            this.isViewFirstAppear = false;
        }
    }

    public abstract void onViewDisappear();

    public abstract void onViewFirstAppear();
}
