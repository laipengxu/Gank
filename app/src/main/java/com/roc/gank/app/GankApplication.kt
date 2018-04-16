package com.roc.gank.app

import android.app.Application



/**
 * Created by 赖鹏旭 on 2018/1/24.
 * Gank Application
 */
class GankApplication : Application() {

    companion object {
        var mInstance: GankApplication? = null
        fun getInstance(): GankApplication? {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}