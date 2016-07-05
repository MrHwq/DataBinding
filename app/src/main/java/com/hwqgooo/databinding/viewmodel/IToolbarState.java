package com.hwqgooo.databinding.viewmodel;

/**
 * Created by weiqiang on 2016/7/4.
 */
public interface IToolbarState {
    int EXPANDED = 0;
    int COLLAPSED = 1;
    int INTERNEDIATE = 2;

    void setToolbarState(int state);

    int getToolbarState();
}
