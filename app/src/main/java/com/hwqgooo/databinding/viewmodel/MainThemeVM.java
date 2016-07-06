package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.message.Messenger;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by weiqiang on 2016/7/3.
 */
public class MainThemeVM extends BaseObservable implements IToolbarState {
    public final static String TAG = "MainThemeVM";
    public static final String TOKEN_UPDATE_INDICATOR = "TOKEN_MainThemeVM";
    private Context context;
    private int selectColor;
    private int unselectColor;
    private int toolbalState = -1;
    private String toolbarTitle;
    public String toolbarImage;
    private String[] appbarName;

    static MainThemeVM instance;

    private MainThemeVM(Context context) {
        this.context = context;
        selectColor = context.getResources().getColor(R.color.colorPrimary);
        unselectColor = context.getResources().getColor(R.color.colorAccent);
        appbarName = context.getResources().getStringArray(R.array.appbarname);
        toolbarTitle = appbarName[EXPANDED];
        Messenger.getDefault().register(context,
                TOKEN_UPDATE_INDICATOR,
                String.class,
                new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call: " + TOKEN_UPDATE_INDICATOR + "==>" + s);
                        setToolbarImage(s);
                    }
                });
    }

    public void onDestory() {
        Messenger.getDefault().unregister(context);
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
            setToolbarTitle(appbarName[EXPANDED]);
        } else if (state == COLLAPSED) {
            setToolbarTitle(appbarName[COLLAPSED]);
        } else if (state == INTERNEDIATE) {
            setToolbarTitle(appbarName[INTERNEDIATE]);
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
