package com.roc.gank.mvp.view

import com.roc.gank.entity.GankBean

/**
 * Created by 赖鹏旭 on 2018/1/31.
 */
interface CollectionView:BaseView {
    fun showCollectedGanks(ganks: List<GankBean>?)
    fun showNotCollectedGanks(throwable: Throwable?)
    fun showDeleteSuccess()
    fun showDeleteError()
}