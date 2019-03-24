package com.hwqgooo.databinding.model.showmzitu;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by weiqiang on 2016/7/9.
 */
public interface IMzituService {
    @GET("{title}/page/{page}")
    Observable<String> getGirlSubject(@Path("title") String title, @Path("page") int page);
}
