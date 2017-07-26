package com.hwqgooo.databinding.model;

import android.content.Context;

import com.hwqgooo.databinding.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by weiqiang on 2016/8/1.
 */
public class CacheHttpClient {
    public static final int CACHE_TIME_LONG = 60 * 60 * 24 * 7;
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected(context)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected(context)) {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder().header("Cache-Control", "public, " +
                        "only-if-cached, max-stale=" + CACHE_TIME_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };
    private RetryIntercepter retryIntercepter = new RetryIntercepter(3);

    private OkHttpClient mOkHttpClient;
    private Cache cache;
    static CacheHttpClient client;
    static Context context;

    private CacheHttpClient() {
        initOkHttpClient();
    }

    public static OkHttpClient getOkHttpClient() {
        if (client == null) {
            client = new CacheHttpClient();
        }
        return client.mOkHttpClient;
    }

    public static void initCacheHttpClient(Context context) {
        CacheHttpClient.context = context;
    }

    public static void destoryCacheHttpClient() {
        CacheHttpClient.client = null;
        CacheHttpClient.context = null;
    }

    /**
     * 初始化OKHttpClient
     */
    private void initOkHttpClient() {

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (CacheHttpClient.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    cache = new Cache(new File(context.getCacheDir(),
                            "HttpCache"), 1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(retryIntercepter)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
//                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }
}
