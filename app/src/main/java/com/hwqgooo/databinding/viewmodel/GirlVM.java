package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.message.Messenger;
import com.hwqgooo.databinding.model.CacheHttpClient;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.model.bean.GirlData;
import com.hwqgooo.databinding.model.showgirl.IGirlService;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.LayoutManagers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class GirlVM extends BaseGirlVM {
    public static final String TAG = GirlVM.class.getSimpleName();
    final String baseUrl = "http://gank.io/api/";
    Retrofit mRetrofit;
    IGirlService girlService;
    Subscription subscription;
    int page;
    Context context;
    static GirlVM girlVM;

    public static GirlVM getInstance(Context context) {
        if (girlVM == null) {
            girlVM = new GirlVM(context);
        }
        return girlVM;
    }

    private GirlVM(Context context) {
        this.context = context;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(CacheHttpClient.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        girlService = mRetrofit.create(IGirlService.class);
//        Girl.request_width = context.getResources().getDisplayMetrics().widthPixels;
//        float scale = (float) (Math.random() + 1);
//        while (scale > 1.6 || scale < 1.1) {
//            scale = (float) (Math.random() + 1);
//        }
//        Girl.request_height = (int) (Girl.request_width * scale * 0.448);
        itemView = ItemView.of(BR.girl, R.layout.item_girl);
        onRefresh = new ReplyCommand(new Action0() {
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
        onLoadMore = new ReplyCommand(new Action0() {
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
        onItemClick = new ReplyCommand<Integer>(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "call: " + integer);
                Log.d(TAG, "call: " + items.get(integer).getDesc());
                Log.d(TAG, "call: " + items.get(integer).getUrl());
            }
        });
        factory = LayoutManagers.staggeredGrid(2, LinearLayoutManager.VERTICAL);
//        factory = LayoutManagers.linear(LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public void onStart() {
        if (items.isEmpty()) {
            onRefresh.execute();
        }
    }

    @Override
    public void onDestory() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
        items.clear();
        girlVM = null;
    }

    @Override
    public void onStop() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    @Override
    public void onRestart(Context context) {
//        if (this.context == context) {
//            return;
//        }
//        mRetrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .client(CacheHttpClient.getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        girlService = mRetrofit.create(IGirlService.class);
    }

    private void load(final boolean isRefresh) {
        Log.d(TAG, "load:" + isRefresh + " ,page:" + page);
        isRefreshing.set(true);
//        final Observable<GirlData> observable = girlService.getGirls(page);
        GirlData fake = new GirlData();
        List<Girl> lists = new LinkedList<>();
//        lists.add(new Girl("1", "http://img4.imgtn.bdimg.com/it/u=221420021,4143011973&fm=200&gp=0.jpg"));
        lists.add(new Girl("2", "http://img2.imgtn.bdimg.com/it/u=956448384,367810286&fm=200&gp=0.jpg"));
        lists.add(new Girl("3", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4185542813,2038858819&fm=200&gp=0.jpg"));
        lists.add(new Girl("4", "http://img5.imgtn.bdimg.com/it/u=2346660794,147430089&fm=200&gp=0.jpg"));
        lists.add(new Girl("5", "http://img4.imgtn.bdimg.com/it/u=163325580,1839796468&fm=200&gp=0.jpg"));
        lists.add(new Girl("5", "http://img2.imgtn.bdimg.com/it/u=3120779360,3982822210&fm=200&gp=0.jpg"));
        lists.add(new Girl("5", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=91028370,44145306&fm=200&gp=0.jpg"));

        fake.setGirls(lists);
        Observable<GirlData> observable = Observable.just(fake);
        subscription = observable
                .subscribeOn(Schedulers.io())
                .map(girlData -> girlData.getGirls())
                .doAfterTerminate(() -> isRefreshing.set(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Girl>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, (isRefresh ? "onRefresh" : "onLoadMore") + " onCompleted: ");
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
                            for (Girl girl : girlList) {
                                Log.d(TAG, "onNext: " + girl.getDesc());
                            }
                            if (isRefresh) {
                                items.clear();
                            }

                            Messenger.getDefault().send(
                                    girlList.get((int) (girlList.size() * Math.random())).getUrl(),
                                    MainThemeVM.TOKEN_UPDATE_INDICATOR);
//                            int pos = items.size();
                            items.addAll(girlList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
