package com.roc.gank.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.roc.gank.mvp.presenter.BasePresenter
import com.roc.gank.mvp.view.BaseView

/**
 * Created by 赖鹏旭 on 2018/1/24.
 * Activity基类
 */
abstract class BaseActivity<V : BaseView, P : BasePresenter<V, *>> : AppCompatActivity(){

    var mPresenter: P? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter()
        mPresenter?.onCreate(this as V)
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

    abstract fun initPresenter(): P
}