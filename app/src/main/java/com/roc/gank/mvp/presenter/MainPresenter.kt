package com.roc.gank.mvp.presenter

import com.roc.gank.entity.GankBean
import com.roc.gank.entity.GankPage
import com.roc.gank.mvp.model.MainModel
import com.roc.gank.mvp.view.MainView

/**
 * Created by 赖鹏旭 on 2018/1/25.
 * 主界面 视图与数据交互层
 */
class MainPresenter : BasePresenter<MainView, MainModel>() {

    var mCurrentPageCount:Int = 1

    override fun initModel(): MainModel {
        return MainModel()
    }

    /**
     * 初始化数据
     */
    fun initData() {
        mCurrentPageCount = 1
        mModel?.requestData(mCurrentPageCount, object : MainModel.OnRequestDataListener{
            override fun onRequestDataSuccess(page: GankPage) {
                mView?.setupView(page)
            }

            override fun onRequestDataError(throwable: Throwable) {
                mView?.showRequestDataFail(throwable.toString())
            }
        })
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData() {
        mCurrentPageCount++
        mModel?.requestData(mCurrentPageCount, object : MainModel.OnRequestDataListener{
            override fun onRequestDataSuccess(page: GankPage) {
                mView?.updateView(page)
            }

            override fun onRequestDataError(throwable: Throwable) {
                mView?.showRequestDataFail(throwable.toString())
            }
        })
    }

    /**
     * 保存干货到收藏夹
     */
    fun saveGank(gankBean: GankBean?) {
        mModel?.saveGankToDB(gankBean,object : MainModel.OnInsertCollectionListener{
            override fun onInsertCollectionSuccess() {
                mView?.showSaveGankSuccess()
            }

            override fun onInsertCollectionError() {
                mView?.showSaveGankError()
            }
        })
    }
}