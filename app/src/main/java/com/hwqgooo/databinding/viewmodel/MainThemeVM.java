package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;

import rx.functions.Action0;

/**
 * Created by weiqiang on 2016/7/3.
 */
public class MainThemeVM extends BaseObservable implements IToolbarState {
    public final static String TAG = "MainThemeVM";
    private int selectColor;
    private int unselectColor;
    private String toolbarTitle;
    public String toolbarImage;
    private int toolbalState = -1;

    static MainThemeVM instance;

    private MainThemeVM(Context context) {
        selectColor = context.getResources().getColor(R.color.colorPrimary);
        unselectColor = context.getResources().getColor(R.color.colorAccent);
        toolbarTitle = "完全展开";
    }

    public static MainThemeVM getInstance(Context context) {
        if (instance == null) {
            instance = new MainThemeVM(context);
        }
        return instance;
    }

    @Bindable
    public int getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
        notifyPropertyChanged(BR.selectColor);
    }

    @Bindable
    public int getUnselectColor() {
        return unselectColor;
    }

    public void setUnselectColor(int unselectColor) {
        this.unselectColor = unselectColor;
        notifyPropertyChanged(BR.unselectColor);
    }

    @Bindable
    public String getToolbarTitle() {
        return toolbarTitle;
    }

    private void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
        notifyPropertyChanged(BR.toolbarTitle);
    }

    @Bindable
    public String getToolbarImage() {
        return toolbarImage;
    }

    public void setToolbarImage(String toolbarImage) {
        this.toolbarImage = toolbarImage;
        notifyPropertyChanged(BR.toolbarImage);
        Log.d(TAG, "setToolbarImage: " + toolbarImage);
    }

    @Override
    public void setToolbarState(int state) {
        this.toolbalState = state;
        if (state == EXPANDED) {
            setToolbarTitle("完全展开");
        } else if (state == COLLAPSED) {
            setToolbarTitle("完全收缩");
        } else if (state == INTERNEDIATE) {
            setToolbarTitle("中间态");
        } else {
            setToolbarTitle("未知" + state);
        }
    }

    @Override
    public int getToolbarState() {
        return toolbalState;
    }

    public final ReplyCommand onClick = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            Log.d(TAG, "call: imageview onClick");
        }
    });
}
