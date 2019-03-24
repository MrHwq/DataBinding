package com.hwqgooo.databinding.viewmodel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.Pair;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.CacheHttpClient;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.model.bean.GirlGallery;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewClassSelector;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import okhttp3.OkHttpClient;

/**
 * Created by weiqiang on 2016/7/9.
 */
public class MzituVM extends BaseGirlVM<Girl> {
    final String baseUrl = "http://www.mzitu.com/";
    public String TAG = MzituVM.class.getSimpleName();
    CompositeDisposable compositeSubscription;
    String title;
    int page;
    OkHttpClient client;
    Context context;
    List<Pair<String, String>> subUrl;
    public List<GirlGallery> galleries = new LinkedList<>();

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

    private MzituVM(Context context, String title) {
        this.context = context;
        TAG = "MzituVM" + title;
        Log.d(TAG, "MzituVM: " + title);
        this.title = title;
        compositeSubscription = new CompositeDisposable();
        itemView = ItemViewArg.of(ItemViewClassSelector.builder()
                .put(Girl.class, BR.girl, R.layout.item_girl)
                .build());
        onRefresh = new ReplyCommand(new Action() {
            @Override
            public void run() throws Exception {
                if (isRefreshing.get()) {
                    Log.d(TAG, "call: onRefresh is refreshing");
                    return;
                }
                page = 1;
                Log.d(TAG, "call: onRefresh " + page);
                load(true);
            }
        });
        onLoadMore = new ReplyCommand(new Action() {
            @Override
            public void run() throws Exception {
                if (isRefreshing.get()) {
                    Log.d(TAG, "call: onLoadMore is refreshing");
                    return;
                }
                Log.d(TAG, "call: onLoadMore " + page);
                load(false);
            }
        });
        onItemClick = new ReplyCommand<>(integer -> {
            Log.d(TAG, "call: " + integer);
//            DaoService.getInstance().setRead(items.get(integer).getUrl())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(galleryORM -> {
//                    });
            ;
        });
        client = CacheHttpClient.getOkHttpClient();
        factory = LayoutManagers.staggeredGrid(2, LinearLayoutManager.VERTICAL);
    }


//    @Override
//    public void onStart() {
//        if (items.isEmpty()) {
//            load(true);
//        }
//    }

//    @Override
//    public void onDestory() {
//        compositeSubscription.clear();
//        items.clear();
//        vms.remove(this);
//    }
//
//    @Override
//    public void onStop() {
//        compositeSubscription.clear();
//    }
//
//    @Override
//    public void onRestart(Context context) {
//    }

    Observable<List<GirlGallery>> getGalleriesFromUrl() {
        return null;
//        return Observable
//                .create(new Observable.de<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> subscriber) {
//                        try {
//                            String url;
//                            if (page == 1) {
//                                url = baseUrl + title;
//                            } else {
//                                url = baseUrl + title + "/page/" + page;
//                            }
//                            Log.d(TAG, "call: " + url);
//                            Request request = new Request.Builder().url(url).build();
//                            Call call = client.newCall(request);
//                            Response response = call.execute();
//                            if (!response.isSuccessful()) {
//                                throw new Exception("okhttp execute fail");
//                            }
//                            Document document = Jsoup.parse(response.body().string());
//                            Elements elements = document.select("div[class=main-content]");
//                            subscriber.onNext(elements.html());
//                            subscriber.onCompleted();
//                        } catch (Exception e) {
//                            subscriber.onError(e);
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .map(new Func1<String, List<GirlGallery>>() {
//                    @Override
//                    public List<GirlGallery> call(String s) {
//                        return getSubjectUrl(s);
//                    }
//                });
    }

//    Func2 resetRead = new Func2<List<GalleryORM>, List<GirlGallery>, List<GirlGallery>>() {
//        @Override
//        public List<GirlGallery> call(List<GalleryORM> galleryORMs,
//                                      List<GirlGallery> girlGalleries) {
//            for (GalleryORM galleryORM : galleryORMs) {
//                for (GirlGallery gallery : girlGalleries) {
//                    if (galleryORM.getGalleryUrl().equals(gallery.dataOriginal)) {
//                        gallery.isRead = true;
//                    }
//                }
//            }
//            return girlGalleries;
//        }
//    };

    private void load(final boolean isRefresh) {
        compositeSubscription.clear();
        isRefreshing.set(true);
        Log.d(TAG, "load: " + title + "..." + page);

//        compositeSubscription.add(
//                Observable.zip(DaoService.getInstance().getCovers(), getGalleriesFromUrl(),
//                        resetRead)
//                        .subscribeOn(Schedulers.io())
//                        .map(new Func1<List<GirlGallery>, List<Girl>>() {
//                            @Override
//                            public List<Girl> call(List<GirlGallery> girlGalleries) {
//                                if (isRefresh) {
//                                    galleries.clear();
//                                }
//                                galleries.addAll(girlGalleries);
//                                List<Girl> girlList = new LinkedList<Girl>();
//                                for (GirlGallery girlGallery : girlGalleries) {
//                                    girlList.add(new Girl(girlGallery.desc,
//                                            girlGallery.dataOriginal, girlGallery.isRead));
//                                }
//                                return girlList;
//                            }
//                        })
//                        .doAfterTerminate(new Action0() {
//                            @Override
//                            public void call() {
//                                isRefreshing.set(false);
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<List<Girl>>() {
//                            @Override
//                            public void onCompleted() {
//                                ++page;
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                error(e);
//                            }
//
//                            @Override
//                            public void onNext(List<Girl> girlList) {
//                                if (isRefresh) {
//                                    items.clear();
//                                }
//                                items.addAll(girlList);
//                            }
//                        }));
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<List<Pair<String, String>>, List<Pair<String, String>>>() {
//                    @Override
//                    public List<Pair<String, String>> call(List<Pair<String, String>> pairs) {
//                        if (isRefresh) {
//                            items.clear();
//                        }
//                        return pairs;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .flatMap(new Func1<List<Pair<String, String>>, Observable<Pair<String,
// String>>>() {
//                    @Override
//                    public Observable<Pair<String, String>> call(List<Pair<String, String>>
// pairs) {
//                        if (pairs.isEmpty()) {
//                            return Observable.error(new Throwable("load pairs is null"));
//                        }
//                        Log.d(TAG, "load pair call: " + pairs.size());
//                        if (pairs.size() > 1) {
//                            subUrl = pairs.subList(1, pairs.size());
//                        }
//                        Log.d(TAG, "load suburl call: " + subUrl.size());
//                        return Observable.just(pairs.get(0));
//                    }
//                })
//                .filter(new Func1<Pair<String, String>, Boolean>() {
//                    @Override
//                    public Boolean call(Pair<String, String> stringStringPair) {
//                        if (stringStringPair.first.startsWith("http")) {
//                            return Boolean.TRUE;
//                        }
//                        Log.d(TAG, "load call: " + stringStringPair.first);
//                        return Boolean.FALSE;
//                    }
//                })
//                .doOnError(new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        isRefreshing.set(false);
//                    }
//                })
//                .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        ++page;
//                    }
//                })
//                .subscribe(new Subscriber<Pair<String, String>>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        error(e);
//                    }
//
//                    @Override
//                    public void onNext(Pair<String, String> pair) {
//                        Log.d(TAG, "onNext: " + pair.first + "..." + pair.second);
//                        getSuburl(pair);
//                    }
//                }));
    }

    List<GirlGallery> getSubjectUrl(final String tophtml) {
        Document doc = Jsoup.parse(tophtml);
        Elements table = doc.select("ul[id=pins]");
        if (table.size() == 0) {
            return null;
        }
        List<GirlGallery> subjectUrl = new LinkedList<>();
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
                Elements img = href.select("img");
//                Log.d(TAG, "getSubjectUrl: " + img.outerHtml());
//                Log.d(TAG, "getSubjectUrl: " + img.attr("src"));
//                Log.d(TAG, "getSubjectUrl: " + img.attr("data-original"));
                subjectUrl.add(new GirlGallery(sube.text(), img.attr("data-original"),
                        sube.attr("href")));
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
}
