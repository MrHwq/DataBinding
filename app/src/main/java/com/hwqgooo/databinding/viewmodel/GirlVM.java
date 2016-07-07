package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.util.Log;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.bindingcollectionadapter.ItemView;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.message.Messenger;
import com.hwqgooo.databinding.model.IGirlService;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.model.bean.GirlData;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
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
    public final ItemView itemView = ItemView.of(BR.girl, R.layout.item_girl);

    final String baseUrl = "http://gank.io/api/";
    Retrofit mRetrofit;
    IGirlService girlService;
    CompositeSubscription compositeSubscription;


    public GirlVM(Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        girlService = mRetrofit.create(IGirlService.class);
        compositeSubscription = new CompositeSubscription();
//        Girl.request_width = context.getResources().getDisplayMetrics().widthPixels;
//        float scale = (float) (Math.random() + 1);
//        while (scale > 1.6 || scale < 1.1) {
//            scale = (float) (Math.random() + 1);
//        }
//        Girl.request_height = (int) (Girl.request_width * scale * 0.448);
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
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        isRefreshing.set(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Girl>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onCompleted: ");
                        page++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onError: " +
                                    "SocketTimeoutException");
                        } else if (e instanceof UnknownHostException) {
                            Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onError: " +
                                    "UnknownHostException");
                        } else {
                            Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onError");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(List<Girl> girlList) {
                        try {
                            Log.d(TAG, "onNext: " + girlList.size());
                            if (isRefresh) {
                                girls.clear();
                            }

                            Messenger.getDefault().send(
                                    girlList.get((int) (girlList.size() * Math.random())).getUrl(),
                                    MainThemeVM.TOKEN_UPDATE_INDICATOR);
                            int pos = girls.size();
                            girls.addAll(girlList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    public final ReplyCommand<Integer> onItemClick = new ReplyCommand<Integer>(new Action1<Integer>() {
        @Override
        public void call(Integer integer) {
            Log.d(TAG, "call: " + integer);
            Log.d(TAG, "call: " + girls.get(integer).getDesc());
            Log.d(TAG, "call: " + girls.get(integer).getUrl());
        }
    });
}
