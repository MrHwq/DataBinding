package com.hwqgooo.databinding.model;


import com.hwqgooo.databinding.model.bean.GirlData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by weiqiang on 2016/6/10.
 */
public interface IGirlService {
    @GET("data/福利/10/{page}")
    Observable<GirlData> getGirls(@Path("page") int page);
}
