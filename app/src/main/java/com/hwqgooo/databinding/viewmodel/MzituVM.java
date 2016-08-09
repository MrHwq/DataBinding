package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.bindingcollectionadapter.ItemView;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.CacheInterceptor;
import com.hwqgooo.databinding.model.bean.Girl;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by weiqiang on 2016/7/9.
 */
public class MzituVM extends BaseGirlVM {
    final String baseUrl = "http://www.mzitu.com/";
    public String TAG = "MzituVM";
    CompositeSubscription compositeSubscription;
    String title;
    int page;
    OkHttpClient client;
    Context context;
    static MzituVM girlVM;
    static List<MzituVM> vms = new ArrayList<>();

    public static MzituVM getInstance(Context context, String title) {
        for (MzituVM vm : vms) {
            if (vm.title.equals(title)) {
                return vm;
            }
        }
        MzituVM vm = new MzituVM(context, title);
        vms.add(vm);
        return vm;
    }

    public OkHttpClient provideOkHttpClient(Context context) {
        Cache cache = new Cache(new File(context.getCacheDir(),
                "HttpCache"), 1024 * 1024 * 100);
        OkHttpClient newClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        return newClient;
    }

    private MzituVM(Context context, String title) {
        this.context = context;
        TAG = "MzituVM" + title;
        Log.d(TAG, "MzituVM: " + title);
        this.title = title;
        compositeSubscription = new CompositeSubscription();
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
                Log.d(TAG, "call: " + girls.get(integer).getDesc());
                Log.d(TAG, "call: " + girls.get(integer).getUrl());
            }
        });
        client = provideOkHttpClient(context);
    }


    @Override
    public void onStart() {
        if (girls.isEmpty()) {
            load(true);
        }
    }

    @Override
    public void onDestory() {
        compositeSubscription.unsubscribe();
        girls.clear();
        vms.remove(this);
    }

    @Override
    public void onStop() {
        compositeSubscription.unsubscribe();
    }

    @Override
    public void onRestart(Context context) {
        client = provideOkHttpClient(context);
    }

    private void load(final boolean isRefresh) {
        compositeSubscription.unsubscribe();
        compositeSubscription = new CompositeSubscription();
        isRefreshing.set(true);
        Log.d(TAG, "load: " + title + "..." + page);
        compositeSubscription.add(
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String url = null;
                        try {
                            if (page == 1) {
                                url = baseUrl + title;
                            } else {
                                url = baseUrl + title + "/page/" + page;
                            }
                            Log.d(TAG, "call: " + url);
                            Request request = new Request.Builder().url(url).build();
                            Call call = client.newCall(request);
                            Response response = call.execute();
                            if (!response.isSuccessful()) {
                                throw new Exception("okhttp execute fail");
                            }

//                            Document document = Jsoup.connect(url)
//                                    .userAgent("Mozilla")
//                                    .timeout(8000)
//                                    .get();
                            Document document = Jsoup.parse(response.body().string());
                            Elements elements = document.select
                                    ("div[class=main-content]");
                            subscriber.onNext(elements.html());
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<String, List<Pair<String, String>>>() {
                            @Override
                            public List<Pair<String, String>> call(String s) {
                                return getSubjectUrl(s);
                            }
                        })
                        .flatMap(new Func1<List<Pair<String, String>>,
                                Observable<Pair<String, String>>>() {
                            @Override
                            public Observable<Pair<String, String>> call(
                                    List<Pair<String, String>> pairs) {
                                return Observable.from(pairs);
                            }
                        })
                        .filter(new Func1<Pair<String, String>, Boolean>() {
                            @Override
                            public Boolean call(Pair<String, String> stringStringPair) {
                                if (stringStringPair.first.startsWith("http")) {
                                    return Boolean.TRUE;
                                }
                                Log.d(TAG, "load call: " + stringStringPair.first);
                                return Boolean.FALSE;
                            }
                        })
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                isRefreshing.set(false);
                            }
                        })
                        .doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                ++page;
                            }
                        })
                        .subscribe(new Subscriber<Pair<String, String>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                error(e);
                            }

                            @Override
                            public void onNext(Pair<String, String> pair) {
                                getSuburl(pair);
                            }
                        }));
    }

    List<Pair<String, String>> getSubjectUrl(final String tophtml) {
        Document doc = Jsoup.parse(tophtml);
        Elements table = doc.select("ul[id=pins]");
        if (table.size() == 0) {
            return null;
        }
        List<Pair<String, String>> subjectUrl = new LinkedList<>();
        for (Element e : table) {
            for (Element child : e.children()) {
                Elements href = child.select("a");
                if (href.size() == 0) {
                    continue;
                }
                Elements subhref = href.select("a");
                Element sube = subhref.last();
//                Log.d(TAG, "getSubjectUrl: " + subhref.outerHtml());
//                Log.d(TAG, "call: " + sube.attr("href"));
//                Log.d(TAG, "call: " + sube.text());
                subjectUrl.add(new Pair<String, String>(sube.attr("href"), sube.text()));
            }
        }

        return subjectUrl;
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

    void getSuburl(final Pair<String, String> pair) {
        compositeSubscription
                .add(Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                try {
                                    Request request = new Request.Builder().url(pair.first).build();
                                    Call call = client.newCall(request);
                                    Response response = call.execute();
                                    if (!response.isSuccessful()) {
                                        throw new Exception("okhttp execute fail");
                                    }
                                    Document document = Jsoup.parse(response.body().string());

//                                    Document document = Jsoup.connect(pair.first)
//                                            .userAgent("Mozilla")
//                                            .timeout(8000)
//                                            .get();
                                    Elements elements = document.select
                                            ("div[class=pagenavi]");
//                                    Log.d(TAG, "call: " + elements.html());
                                    Elements suburl = elements.select("a");
                                    if (suburl.size() - 2 >= 0) {
                                        Element e = suburl.get(suburl.size() - 2);
                                        subscriber.onNext(Integer.valueOf(e.text()));
                                        subscriber.onCompleted();
                                    }
                                } catch (Exception e) {
                                    subscriber.onError(e);
                                }
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .map(new Func1<Integer, List<String>>() {
                                    @Override
                                    public List<String> call(Integer integer) {
                                        List<String> urls = new LinkedList<String>();
                                        int all = integer.intValue();
                                        urls.add(pair.first);
                                        for (int idx = 2; idx <= all; ++idx) {
                                            urls.add(pair.first + "/" + Integer.toString(idx));
                                        }
                                        return urls;
                                    }
                                })
                                .flatMap(new Func1<List<String>, Observable<String>>() {
                                    @Override
                                    public Observable<String> call(List<String> strings) {
                                        return Observable.from(strings);
                                    }
                                })
                                .doOnError(new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        isRefreshing.set(false);
                                    }
                                })
                                .subscribe(new Subscriber<String>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        error(e);
                                    }

                                    @Override
                                    public void onNext(String string) {
                                        getGirl(string);
                                    }
                                })
                );
    }

    void getGirl(final String url) {
        compositeSubscription
                .add(Observable.create(new Observable.OnSubscribe<Girl>() {
                            @Override
                            public void call(Subscriber<? super Girl> subscriber) {
                                try {
                                    Request request = new Request.Builder().url(url).build();
                                    Call call = client.newCall(request);
                                    Response response = call.execute();
                                    if (!response.isSuccessful()) {
                                        throw new Exception("okhttp execute fail");
                                    }
                                    Document document = Jsoup.parse(response.body().string());

//                                    Document document = Jsoup.connect(url)
//                                            .userAgent("Mozilla")
//                                            .timeout(8000)
//                                            .get();
                                    Elements elements = document.select
                                            ("div[class=main-image]");
//                                    Log.d(TAG, "call: " + elements.html());
                                    Elements suburl = elements.select("img");
//                                    Log.d(TAG, "call: " + suburl.attr("src"));
//                                    Log.d(TAG, "call: " + suburl.attr("alt"));
                                    subscriber.onNext(new Girl(suburl.attr("alt"), suburl
                                            .attr
                                                    ("src")));
                                    subscriber.onCompleted();
                                } catch (Exception e) {
                                    subscriber.onError(e);
                                }
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .doOnTerminate(new Action0() {
                                    @Override
                                    public void call() {
                                        isRefreshing.set(false);
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Girl>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        error(e);
                                    }

                                    @Override
                                    public void onNext(Girl girl) {
                                        girls.add(girl);
                                    }
                                })
                );
    }
}
