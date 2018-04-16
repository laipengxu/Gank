package com.roc.gank.retrofit

import com.roc.gank.entity.GankPage
import com.roc.gank.retrofit.api.GankIOApiService
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络接口管理者
 */

class ApiManager private constructor() : BaseHttpManager() {

    var mGankIOApiService: GankIOApiService? = null

    init {
        mGankIOApiService = createApiService(GankIOApiService::class.java, GankIOApiService.BASE_URL)
    }

    /**
     * 创建 Api 接口

     * @param apiClass 要创建的 Api 接口
     * *
     * @param baseUrl  接口地址
     */
    private fun <T> createApiService(apiClass: Class<T>, baseUrl: String): T {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(apiClass)
    }

    companion object {
        private var sInstance: ApiManager? = null

        val instance: ApiManager
            get() {
                if (sInstance == null) {
                    synchronized(ApiManager::class.java) {
                        if (sInstance == null) {
                            sInstance = ApiManager()
                        }
                    }
                }
                return sInstance!!
            }
    }

    fun requestGankPage(type: String, pageNum: Int): Observable<GankPage> {
        return mGankIOApiService?.getPage(type, pageNum)!!
    }
}
