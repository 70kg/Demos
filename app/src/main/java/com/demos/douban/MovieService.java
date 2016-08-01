package com.demos.douban;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Mr_Wrong on 16/3/17.
 */
public interface MovieService {
    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<HttpResult<List<SubjectsEntity>>> getTopMovie1(@Query("start") int start, @Query("count") int count);
}
