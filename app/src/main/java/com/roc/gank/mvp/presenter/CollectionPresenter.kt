package com.roc.gank.mvp.presenter

import com.roc.gank.entity.GankBean
import com.roc.gank.mvp.model.CollectionModel
import com.roc.gank.mvp.view.CollectionView

/**
 * Created by 赖鹏旭 on 2018/1/31.
 * 收藏夹 视图与界面交互层
 */
class CollectionPresenter : BasePresenter<CollectionView, CollectionModel>() {

    override fun initModel(): CollectionModel {
        return CollectionModel()
    }

    fun initData() {
        mView?.showLoading()
        mModel?.queryAllGank(object : CollectionModel.OnRequestDataListener{
            override fun onRequestSuccess(ganks: List<GankBean>?) {
                mView?.hideLoading()
                mView?.showCollectedGanks(ganks)
            }

            override fun onRequestError(throwable: Throwable?) {
                mView?.hideLoading()
                mView?.showNotCollectedGanks(throwable)
            }
        })
    }

    fun delete(gankBean: GankBean) {
        mModel?.deleteGank(gankBean,object : CollectionModel.OnDeleteGankListener{
            override fun onDeleteGankSuccess() {
                mView?.showDeleteSuccess()
            }

            override fun onDeleteGankError() {
                mView?.showDeleteError()
            }
        })
    }
}