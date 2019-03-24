package com.hwqgooo.databinding.model.showgirl;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.hwqgooo.databinding.model.CacheHttpClient;
import com.hwqgooo.databinding.model.bean.Girl;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalGirlDataSource extends PageKeyedDataSource<Integer, Girl> {
    private final String baseUrl = "http://gank.io/api/";
    private Retrofit mRetrofit;
    private IGirlService girlService;

    public LocalGirlDataSource() {
        super();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(CacheHttpClient.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        girlService = mRetrofit.create(IGirlService.class);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Girl> callback) {
//        Disposable disposable = girlService.getGirls(1).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(girlData -> {
//                    System.out.println("size:::" + girlData.getGirls().size());
//                });
//                        callback.onResult(girlData.getGirls(), null, 1));
        callback.onResult(getGirls(0), 0, 0);
    }

    List<Girl> getGirls(int page) {
        List<Girl> lists = new LinkedList<>();
        int size = 7;
        lists.add(new Girl(String.valueOf(page * size), "http://img4.imgtn.bdimg" +
                ".com/it/u=221420021," +
                "4143011973&fm=200&gp=0.jpg"));
        lists.add(new Girl(String.valueOf(page * size + 1), "http://img2.imgtn.bdimg" +
                ".com/it/u=956448384,367810286&fm=200&gp=0.jpg"));
        lists.add(new Girl(String.valueOf(page * size + 2), "https://ss2.bdstatic" +
                ".com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4185542813,2038858819&fm=200&gp=0.jpg"));
        lists.add(new Girl(String.valueOf(page * size + 3), "http://img5.imgtn.bdimg" +
                ".com/it/u=2346660794,147430089&fm=200&gp=0.jpg"));
        lists.add(new Girl(String.valueOf(page * size + 4), "http://img4.imgtn.bdimg" +
                ".com/it/u=163325580," +
                "1839796468&fm=200&gp=0.jpg"));
        lists.add(new Girl(String.valueOf(page * size + 5), "http://img2.imgtn.bdimg" +
                ".com/it/u=3120779360," +
                "3982822210&fm=200&gp=0.jpg"));
        lists.add(new Girl(String.valueOf(page * size + 6), "https://ss0.bdstatic" +
                ".com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u" +
                "=91028370,44145306&fm=200&gp=0.jpg"));
        return lists;
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Girl> callback) {
        Disposable disposable = girlService.getGirls(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlData ->
                        callback.onResult(girlData.getGirls(), params.key - 1));
        System.out.println("=========before:" + (params.key - 1));
//        callback.onResult(getGirls(params.key - 1), params.key - 1);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Girl> callback) {
        Disposable disposable = girlService.getGirls(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlData ->
                        callback.onResult(girlData.getGirls(), params.key + 1));
        System.out.println("=========after:" + (params.key + 1));
//        callback.onResult(getGirls(params.key + 1), params.key > 6 ? null : params.key + 1);
    }
}
