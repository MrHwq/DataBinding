package com.hwqgooo.databinding.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.hwqgooo.databinding.model.CacheHttpClient;
import com.hwqgooo.databinding.model.dao.DaoService;
import com.hwqgooo.databinding.utils.ActivityStack;

import glimpse.core.Glimpse;

/**
 * Created by weiqiang on 2016/8/23.
 */
public class MyApp extends Application {
    public static final String TAG = MyApp.class.getSimpleName();
    ActivityStack activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        if (activityStack == null) {
            activityStack = new ActivityStack();
        }
        registerActivityListener();
        Glimpse.init(this);
//        LeakCanary.install(this);
    }

    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activityStack.isEmpty()) {
                    init();
                }
                activityStack.pushActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted: " + activity.getClass().getName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, "onActivityResumed: " + activity.getClass().getName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG, "onActivityPaused: " + activity.getClass().getName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, "onActivityStopped: " + activity.getClass().getName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(TAG, "onActivitySaveInstanceState: " + activity.getClass().getName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (null == activityStack || activityStack.isEmpty()) {
                    return;
                }
                if (activityStack.contains(activity)) {
                    /**
                     *  监听到 Activity销毁事件 将该Activity 从list中移除
                     */
                    activityStack.popActivity(activity);
                    if (activityStack.isEmpty()) {
                        onDestory();
                    }
                }
            }
        });
    }

    public void init() {
        CacheHttpClient.initCacheHttpClient(getApplicationContext());
        DaoService.initContext(getApplicationContext());
    }

    public void onDestory() {
        CacheHttpClient.destoryCacheHttpClient();
        DaoService.initContext(null);
    }
}
