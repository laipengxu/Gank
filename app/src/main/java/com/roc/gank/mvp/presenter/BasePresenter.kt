package com.roc.gank.mvp.presenter

import com.roc.gank.mvp.model.BaseModel
import com.roc.gank.mvp.view.BaseView

/**
 * Created by 赖鹏旭 on 2018/1/24.
 * 视图数据交互层 基类
 */
abstract class BasePresenter<V : BaseView, M : BaseModel> {

    var mView: V? = null
    var mModel: M? = null

    fun onCreate(view: V) {
        this.mView = view
        this.mModel = initModel()
        this.mModel?.onCreate()
    }

    fun onStart() {
        this.mModel?.onStart()
    }

    fun onResume() {
        this.mModel?.onResume()
    }

    fun onPause() {
        this.mModel?.onPause()
    }

    fun onStop() {
        this.mModel?.onStop()
    }

    fun onDestroy() {
        mView = null
        this.mModel?.onDestroy()
    }

    abstract fun initModel(): M
}