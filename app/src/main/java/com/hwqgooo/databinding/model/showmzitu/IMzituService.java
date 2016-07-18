package com.hwqgooo.databinding.model.showmzitu;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by weiqiang on 2016/7/9.
 */
public interface IMzituService {
    @GET("{title}/page/{page}")
    Observable<String> getGirlSubject(@Path("title") String title, @Path("page") int page);
}
