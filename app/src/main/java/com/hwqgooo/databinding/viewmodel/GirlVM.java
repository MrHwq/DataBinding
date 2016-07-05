package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.util.Log;

import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.IGirlService;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.model.bean.GirlData;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class GirlVM {
    public static final String TAG = "GirlVM";
    private ObservableList<Girl> girls = new ObservableArrayList<>();
    public ObservableBoolean isRefreshing = new ObservableBoolean(false);

    final String baseUrl = "http://gank.io/api/";
    Retrofit mRetrofit;
    IGirlService girlService;
    CompositeSubscription compositeSubscription;

    int count = 0;
    Context context;

    public GirlVM(Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        girlService = mRetrofit.create(IGirlService.class);
        compositeSubscription = new CompositeSubscription();
        this.context = context;
    }

    public void onDestory() {
        compositeSubscription.unsubscribe();
    }

    private void load(final boolean isRefresh) {
        isRefreshing.set(true);
        final Observable<GirlData> observable = girlService.getGirls(page);
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .map(new Func1<GirlData, List<Girl>>() {
                    @Override
                    public List<Girl> call(GirlData girlData) {
                        return girlData.getGirls();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Girl>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onCompleted: ");
                        isRefreshing.set(false);
                        page++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onError: " +
                                    "SocketTimeoutException");
                        } else {
                            e.printStackTrace();
                        }
                        isRefreshing.set(false);
                    }

                    @Override
                    public void onNext(List<Girl> girlList) {
                        if (isRefresh) {
                            girls.clear();
                        }

                        MainThemeVM.getInstance(context)
                                .setToolbarImage(girlList.get((int) (girlList.size() * Math
                                        .random()))
                                        .getUrl());
                        int pos = girls.size();
                        girls.addAll(girlList);
                    }
                }));
    }

    public final ReplyCommand onLoadMore = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            if (isRefreshing.get()) {
                Log.d(TAG, "call: onLoadMore is refreshing");
                return;
            }
            Log.d(TAG, "call: onLoadMore " + page);
            load(false);
        }
    });

    public ObservableList<Girl> getGirls() {
        return girls;
    }

    int page;
    public final ReplyCommand onRefresh = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            if (isRefreshing.get()) {
                Log.d(TAG, "call: onRefresh is refreshing");
                return;
            }
            page = 1;
            Log.d(TAG, "call: onRefresh " + page);
            load(true);
        }
    });
}
