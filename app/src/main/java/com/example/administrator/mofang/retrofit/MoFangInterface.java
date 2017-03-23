package com.example.administrator.mofang.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/6.
 */

public interface MoFangInterface {




    /**
     * 获取相关赛事
     *
     * @param year
     * @param page
     * @return
     */
    @GET("/competition")
    Observable<String> getMatch(@Query("year") String year,
                                @Query("page") int page,
                                @Query("lang") String language);


    /**
     * 搜索选手
     *
     * @param region 地区
     * @param name   名字
     * @return
     */
    @GET("/results/person?gender=all")
    Observable<String> getPerson(@Query("region") String region,
                                 @Query("name") String name,
                                 @Query("page") int page,
                                 @Query("lang") String language);

    /**
     * @param region 地区
     * @param event  n阶
     * @param type   single或者averge
     * @return
     */
    @GET("results/rankings?gender=all")
    Observable<String> getRank(@Query("region") String region,
                               @Query("event") String event,
                               @Query("type") String type,
                               @Query("page") int page,
                               @Query("lang") String language);



    /**
     * 抓取公式
     */
    @GET("http://www.cubezone.be/zbf2lprintpage.html")
    Observable<String> getCase();

}
