package com.hwqgooo.databinding.viewmodel;

import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.model.showgirl.GirlDataSource;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewClassSelector;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by weiqiang on 2016/7/4.
 */
public class GirlVM extends BaseGirlVM<Girl> {
    public static final String TAG = GirlVM.class.getSimpleName();
    CompositeDisposable subscription;

    public GirlVM() {
//        Girl.request_width = context.getResources().getDisplayMetrics().widthPixels;
//        float scale = (float) (Math.random() + 1);
//        while (scale > 1.6 || scale < 1.1) {
//            scale = (float) (Math.random() + 1);
//        }
//        Girl.request_height = (int) (Girl.request_width * scale * 0.448);
        subscription = new CompositeDisposable();
        itemView = ItemViewArg.of(ItemViewClassSelector.builder()
                .put(Girl.class, BR.girl, R.layout.item_girl)
                .build());
        onRefresh = new ReplyCommand(() -> {
            if (isRefreshing.get()) {
                Log.d(TAG, "call: onRefresh is refreshing");
                return;
            }
            load(true);
        });
        onLoadMore = new ReplyCommand(() -> {
            if (isRefreshing.get()) {
                Log.d(TAG, "call: onLoadMore is refreshing");
                return;
            }
            load(false);
        });
        onItemClick = new ReplyCommand<>(integer -> {
            Log.d(TAG, "call: " + integer);
        });
        factory = LayoutManagers.staggeredGrid(2, LinearLayoutManager.VERTICAL);
//        factory = LayoutManagers.linear(LinearLayoutManager.HORIZONTAL, false);
        items = new LivePagedListBuilder<>(new DataSource.Factory<Integer, Girl>() {
            @Override
            public DataSource<Integer, Girl> create() {
                DataSource<Integer, Girl> dataSource = new GirlDataSource();
                return dataSource;
            }
        }, new PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20).build()
        ).build();
    }


    private void load(final boolean isRefresh) {
//        isRefreshing.set(true);
//        final Observable<GirlData> observable = girlService.getGirls(page);
//        GirlData fake = new GirlData();
//        List<Girl> lists = new LinkedList<>();
//        lists.add(new Girl("1", "http://img4.imgtn.bdimg.com/it/u=221420021,4143011973&fm=200&gp=0.jpg"));
//        lists.add(new Girl("2", "http://img2.imgtn.bdimg.com/it/u=956448384,367810286&fm=200&gp=0.jpg"));
//        lists.add(new Girl("3", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4185542813,2038858819&fm=200&gp=0.jpg"));
//        lists.add(new Girl("4", "http://img5.imgtn.bdimg.com/it/u=2346660794,147430089&fm=200&gp=0.jpg"));
//        lists.add(new Girl("5", "http://img4.imgtn.bdimg.com/it/u=163325580,1839796468&fm=200&gp=0.jpg"));
//        lists.add(new Girl("5", "http://img2.imgtn.bdimg.com/it/u=3120779360,3982822210&fm=200&gp=0.jpg"));
//        lists.add(new Girl("5", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=91028370,44145306&fm=200&gp=0.jpg"));

//        fake.setGirls(lists);
//        Observable<GirlData> observable = Observable.just(fake);
//        cd = observable
//                .subscribeOn(Schedulers.io())
//                .map(girlData -> girlData.getGirls())
//                .doAfterTerminate(() -> isRefreshing.set(false))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<Girl>>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, (isRefresh ? "onRefresh" : "onLoadMore") + " onCompleted: ");
//                        page++;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (e instanceof SocketTimeoutException) {
//                            Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onError: " +
//                                    "SocketTimeoutException");
//                        } else if (e instanceof UnknownHostException) {
//                            Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onError: " +
//                                    "UnknownHostException");
//                        } else {
//                            Log.d(TAG, isRefresh ? "onRefresh" : "onLoadMore" + " onError");
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(List<Girl> girlList) {
//                        try {
//                            Log.d(TAG, "onNext: " + girlList.size());
//                            for (Girl girl : girlList) {
//                                Log.d(TAG, "onNext: " + girl.getDesc());
//                            }
//                            if (isRefresh) {
//                                items.clear();
//                            }
//
//                            Messenger.getDefault().send(
//                                    girlList.get((int) (girlList.size() * Math.random())).getUrl(),
//                                    MainThemeVM.TOKEN_UPDATE_INDICATOR);
////                            int pos = items.size();
//                            items.addAll(girlList);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }
}
