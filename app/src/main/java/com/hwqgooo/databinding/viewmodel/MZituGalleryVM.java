package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.Pair;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.CacheHttpClient;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewClassSelector;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

import org.jsoup.HttpStatusException;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;

/**
 * Created by weiqiang on 2016/8/18.
 */
public class MZituGalleryVM extends BaseGirlVM<Girl> {
    public static final String TAG = MZituGalleryVM.class.getSimpleName();
    public ObservableField<String> toast = new ObservableField<>();
    public int selectPage;
    int gallerysize;
    public ReplyCommand<Integer> onpageselect = new ReplyCommand<Integer>(new Consumer<Integer>() {
        @Override
        public void accept(Integer integer) throws Exception {
            Log.d(TAG, "onpageselect call: " + integer);
            if (selectPage == integer.intValue()) {
                return;
            }
            selectPage = integer.intValue();
            toast.set((selectPage + 1) + ",共" + gallerysize);
        }
    });

    OkHttpClient client;
    String url;
    List<Pair<String, Integer>> pairItems;
    int page = 0;
    final int LIMIT = 10;
    static MZituGalleryVM instance;
    CompositeDisposable compositeSubscription;

    private MZituGalleryVM(final String url) {
        this.url = url;
        client = CacheHttpClient.getOkHttpClient();
        compositeSubscription = new CompositeDisposable();
        itemView = ItemViewArg.of(ItemViewClassSelector.builder()
                .put(Girl.class, BR.girl, R.layout.item_girl)
                .build());
        onRefresh = new ReplyCommand(() -> {
            if (isRefreshing.get()) {
                return;
            }
            getSuburl(url);
        });
        onLoadMore = new ReplyCommand(() -> {
            if (isRefreshing.get()) {
                Log.d(TAG, "call: onLoadMore is refreshing");
                return;
            }
            if (pairItems == null || page >= pairItems.size()) {
                return;
            }
            isRefreshing.set(true);
            for (int idx = page; idx < pairItems.size() && idx < page + LIMIT; ++idx) {
                Pair<String, Integer> pair = pairItems.get(idx);
                getGirl(pair.first, pair.second);
            }
            isRefreshing.set(false);
            page += LIMIT;
        });
        factory = LayoutManagers.staggeredGrid(2, LinearLayoutManager.VERTICAL);
    }

    public static MZituGalleryVM getInstance(String url) {
        if (url == null) {
            return instance;
        }
        if (instance == null) {
            instance = new MZituGalleryVM(url);
        } else {
            if (!url.equals(instance.url)) {
                instance.url = url;
                instance.selectPage = 0;
            }
        }
        return instance;
    }

    public static void putInstance() {
        if (instance != null) {
            instance.onDestory();
            instance = null;
        }
    }

    public void onDestory() {
        compositeSubscription.clear();
    }

    public void onStop() {
        compositeSubscription.clear();
    }

    public void onRestart(Context context) {
        compositeSubscription = new CompositeDisposable();
    }

    //获取girl列表
    void getSuburl(final String url) {
        isRefreshing.set(true);
//        compositeSubscription.add(
//                Observable.create(
//                        new Observable.OnSubscribe<Integer>() {
//                            @Override
//                            public void call(Subscriber<? super Integer> subscriber) {
//                                try {
//                                    Request request = new Request.Builder().url(url).build();
//                                    Call call = client.newCall(request);
//                                    Response response = call.execute();
//                                    if (!response.isSuccessful()) {
//                                        throw new Exception("okhttp execute fail");
//                                    }
//                                    Document document = Jsoup.parse(response.body().string());
////                    Document document = Jsoup.connect(pair.first)
////                            .userAgent("Mozilla")
////                            .timeout(8000)
////                            .get();
//                                    Elements elements = document.select("div[class=pagenavi]");
////                    Log.d(TAG, "call: " + elements.html());
//                                    Elements suburl = elements.select("a");
//                                    if (suburl.size() - 2 >= 0) {
//                                        Element e = suburl.get(suburl.size() - 2);
//                                        subscriber.onNext(Integer.valueOf(e.text()));
//                                        subscriber.onCompleted();
//                                    }
//                                } catch (Exception e) {
//                                    subscriber.onError(e);
//                                }
//                            }
//                        })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .map(new Func1<Integer, Integer>() {
//                            @Override
//                            public Integer call(Integer integer) {
//                                gallerysize = integer.intValue();
//                                toast.set((selectPage + 1) + ",共" + integer);
//                                return integer;
//                            }
//                        })
//                        .subscribeOn(Schedulers.io())
//                        .map(new Func1<Integer, List<Pair<String, Integer>>>() {
//                            @Override
//                            public List<Pair<String, Integer>> call(Integer integer) {
//                                int all = integer.intValue();
//                                List<Pair<String, Integer>> urls = new LinkedList<Pair<String,
//                                        Integer>>();
//                                urls.add(new Pair(url, 0));
//                                for (int idx = 2; idx <= all; ++idx) {
//                                    urls.add(new Pair(url + "/" + Integer.toString(idx), idx - 1));
//                                }
//                                return urls;
//                            }
//                        })
//                        .subscribe(new Subscriber<List<Pair<String, Integer>>>() {
//                            @Override
//                            public void onCompleted() {
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                error(e);
//                            }
//
//                            @Override
//                            public void onNext(List<Pair<String, Integer>> pairs) {
//                                pairItems = pairs;
//                                for (int idx = 0; idx < pairs.size() && idx < LIMIT; ++idx) {
//                                    Pair<String, Integer> pair = pairs.get(idx);
//                                    getGirl(pair.first, pair.second);
//                                }
//                                page = LIMIT;
//                            }
//                        }));
//                        .flatMap(new Func1<List<Pair<String, Integer>>,
//                                Observable<Pair<String, Integer>>>() {
//                            @Override
//                            public Observable<Pair<String, Integer>> call(
//                                    List<Pair<String, Integer>> strings) {
//                                return Observable.from(strings);
//                            }
//                        })
//                        .subscribe(
//                                new Subscriber<Pair<String, Integer>>() {
//                                    @Override
//                                    public void onCompleted() {
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        error(e);
//                                    }
//
//                                    @Override
//                                    public void onNext(Pair<String, Integer> pair) {
//                                        getGirl(pair.first, pair.second);
//                                    }
//                                }
//                        ));
    }

    void getGirl(final String url, final int idx) {
//        compositeSubscription.add(Observable.create(
//                new Observable.OnSubscribe<GalleryGirl>() {
//                    @Override
//                    public void call(Subscriber<? super GalleryGirl> subscriber) {
//                        try {
//                            Request request = new Request.Builder().url(url).build();
//                            Call call = client.newCall(request);
//                            Response response = call.execute();
//                            if (!response.isSuccessful()) {
//                                throw new Exception("okhttp execute fail, " + url);
//                            }
//                            Document document = Jsoup.parse(response.body().string());
////                            Document document = Jsoup.connect(url)
////                                    .userAgent("Mozilla")
////                                    .timeout(8000)
////                                    .get();
//                            Elements elements = document.select("div[class=main-image]");
////                            Log.d(TAG, "call: " + elements.html());
//                            Elements suburl = elements.select("img");
////                            Log.d(TAG, "call: " + suburl.attr("src"));
////                            Log.d(TAG, "call: " + suburl.attr("alt"));
//                            subscriber.onNext(
//                                    new GalleryGirl(
////                                            new Girl(suburl.attr("alt"), suburl.attr("src")),
//                                            new Girl("", suburl.attr("src")),
//                                            idx));
//                            subscriber.onCompleted();
//                        } catch (Exception e) {
//                            subscriber.onError(e);
//                        }
//                    }
//                })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<GalleryGirl>() {
//                            @Override
//                            public void onCompleted() {
//                                isRefreshing.set(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                error(e);
//                            }
//
//                            @Override
//                            public void onNext(GalleryGirl galleryGirl) {
//                                items.add(galleryGirl.girl);
//                            }
//                        })
//        );
    }

    class GalleryGirl {
        Girl girl;
        int index;

        public GalleryGirl(Girl girl, int index) {
            this.girl = girl;
            this.index = index;
        }

        @Override
        public String toString() {
            return girl.toString();
        }
    }

    public void error(Throwable e) {
        if (e instanceof MalformedURLException) {
            Log.d(TAG, "getSuburl onError MalformedURLException");
        } else if (e instanceof UnknownHostException) {
            Log.d(TAG, "getSuburl onError: UnknownHostException");
        } else if (e instanceof IllegalArgumentException) {
            Log.d(TAG, "getSuburl onError: IllegalArgumentException");
        } else if (e instanceof SocketTimeoutException) {
            Log.d(TAG, "getSuburl onError: SocketTimeoutException");
        } else if (e instanceof HttpStatusException) {
            Log.d(TAG, "getSuburl onError: HttpStatusException");
        } else {
            e.printStackTrace();
        }
    }
}
