package com.roc.gank.mvp.model

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by 赖鹏旭 on 2018/1/24.
 * 数据层 基类
 */
abstract class BaseModel {

    protected var mDisposable: CompositeDisposable? = null

    fun onCreate() {
        mDisposable = CompositeDisposable()
    }

    fun onStart() {

    }

    fun onResume() {

    }

    fun onPause() {

    }

    fun onStop() {

    }

    fun onDestroy(){
        mDisposable?.clear()
        mDisposable?.dispose()
        mDisposable = null
    }
}