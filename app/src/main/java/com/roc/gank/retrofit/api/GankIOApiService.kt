package com.roc.gank.retrofit.api

import com.roc.gank.entity.GankPage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by 赖鹏旭 on 2018/1/26.
 * 干货集中营api
 */
interface GankIOApiService {

    companion object {
        val BASE_URL: String = "http://gank.io/api/"
        val TYPE_ANDROID: String = "Android"
    }

    @GET("data/{type}/10/{pageNum}")
    fun getPage(@Path("type") type: String, @Path("pageNum") pageNumber: Int): Observable<GankPage>
}