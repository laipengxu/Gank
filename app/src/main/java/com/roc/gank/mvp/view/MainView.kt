package com.roc.gank.mvp.view

import com.roc.gank.entity.GankPage

/**
 * Created by 赖鹏旭 on 2018/1/25.
 */
interface MainView : BaseView{
    fun setupView(page: GankPage)
    fun showRequestDataFail(throwable: String)
    fun updateView(page: GankPage)
    fun showSaveGankSuccess()
    fun showSaveGankError()
}