package com.roc.gank.retrofit

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * 配置 OKHttp
 * 1. 超时设定 已配置
 * 2. 网络缓存 cache 未配置
 * 3. 持久 cookie 未配置
 * 4. 拦截器 未配置
 */

open class BaseHttpManager {

    val httpClient: OkHttpClient

    init {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.connectTimeout(10, TimeUnit.SECONDS)
        httpClient = builder.build()
    }

}
