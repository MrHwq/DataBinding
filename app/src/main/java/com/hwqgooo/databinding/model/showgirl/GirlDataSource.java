package com.hwqgooo.databinding.model.showgirl;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.hwqgooo.databinding.model.CacheHttpClient;
import com.hwqgooo.databinding.model.bean.Girl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GirlDataSource extends PageKeyedDataSource<Integer, Girl> {
    private final String baseUrl = "http://gank.io/api/";
    private Retrofit mRetrofit;
    private IGirlService girlService;

    public GirlDataSource() {
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
        Disposable disposable = girlService.getGirls(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlData -> {
                    callback.onResult(girlData.getGirls(), null, 2);
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Girl> callback) {
        Disposable disposable = girlService.getGirls(params.key).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlData ->
                        callback.onResult(girlData.getGirls(), params.key - 1));
        System.out.println("=========before:" + (params.key - 1));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Girl> callback) {
        Disposable disposable = girlService.getGirls(params.key).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlData -> {
                    List<Girl> list = girlData.getGirls();
                    if (list != null && !list.isEmpty()) {
                        callback.onResult(girlData.getGirls(), params.key + 1);
                    } else {
                        callback.onResult(null, null);
                    }
                });
        System.out.println("=========after:" + (params.key + 1));
    }
}
