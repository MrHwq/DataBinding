package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.util.Log;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.message.Messenger;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by weiqiang on 2016/7/3.
 */
public class MainThemeVM extends BaseObservable implements IToolbarState {
    public final static String TAG = MainThemeVM.class.getSimpleName();
    public static final String TOKEN_UPDATE_INDICATOR = "TOKEN_MainThemeVM";
    static MainThemeVM instance;
    public final ReplyCommand onClick = new ReplyCommand(() -> {
        Log.d(TAG, "call: imageview onClick");
    });
    public String toolbarImage;
    private Context context;
    private int selectColor;
    public final ReplyCommand<Drawable> onSuccess = new ReplyCommand<Drawable>(new Consumer<Drawable>() {
        @Override
        public void accept(Drawable bitmap) throws Exception {
            Observable.just(bitmap)
                    .map(new Function<Drawable, Palette.Swatch>() {
                        @Override
                        public Palette.Swatch apply(Drawable bitmap) throws Exception {
//                            Palette palette = Palette.from(bitmap).generate();
//                            if (palette != null && palette.getMutedSwatch() != null) {
//                                return palette.getMutedSwatch();
//                            }
                            return null;
                        }
                    })
                    .subscribe(swatch -> {
                        if (swatch != null) {
                            setSelectColor(swatch.getRgb());
                        }
                    });
        }
    });
    private int unselectColor;
    private int toolbalState = -1;
    private String toolbarTitle;
    private String[] appbarName;

    private MainThemeVM(Context context) {
        this.context = context;
        selectColor = ContextCompat.getColor(context, R.color.colorPrimary);
        unselectColor = ContextCompat.getColor(context, R.color.colorAccent);
        appbarName = context.getResources().getStringArray(R.array.appbarname);
        toolbarTitle = appbarName[EXPANDED];
//        Messenger.getDefault().register(context,
//                TOKEN_UPDATE_INDICATOR,
//                String.class,
//                new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.d(TAG, "call: " + TOKEN_UPDATE_INDICATOR + "==>" + s);
//                        setToolbarImage(s);
//                    }
//                });
    }

    public static MainThemeVM getInstance(Context context) {
        if (instance == null) {
            instance = new MainThemeVM(context);
        }
        return instance;
    }

    public void onDestory() {
        Messenger.getDefault().unregister(context);
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

    public void setToolbarImage(String toolbarImage) {
        this.toolbarImage = toolbarImage;
        notifyPropertyChanged(BR.toolbarImage);
        Log.d(TAG, "setToolbarImage: " + toolbarImage);
    }

    @Override
    public int getToolbarState() {
        return toolbalState;
    }


}
