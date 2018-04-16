package com.roc.gank.mvp.model

import com.roc.gank.db.GankDao
import com.roc.gank.entity.GankBean
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by 赖鹏旭 on 2018/1/31.
 * 收藏夹界面数据层
 */
class CollectionModel : BaseModel() {

    fun queryAllGank(listener: OnRequestDataListener) {
        mDisposable?.add(
                Observable
                        .create(ObservableOnSubscribe<List<GankBean>> { emitter ->
                            val allCollectGank = GankDao.getInstance().queryAll()
                            emitter.onNext(allCollectGank)
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith<DisposableObserver<List<GankBean>>>(object : DisposableObserver<List<GankBean>>() {
                            override fun onNext(ganks: List<GankBean>?) {
                                listener.onRequestSuccess(ganks)
                            }

                            override fun onError(throwable: Throwable?) {
                                throwable?.printStackTrace()
                                listener.onRequestError(throwable)
                            }

                            override fun onComplete() {
                            }

                        })
        )
    }

    fun deleteGank(gankBean: GankBean, listener: OnDeleteGankListener) {
        mDisposable?.add(
                Observable
                        .create(ObservableOnSubscribe<Boolean> { emtter ->
                            val isDelete = GankDao.getInstance().delete(gankBean)
                            emtter.onNext(isDelete)
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith<DisposableObserver<Boolean>>(object : DisposableObserver<Boolean>() {
                            override fun onNext(isDelete: Boolean?) {
                                if (isDelete ?: false) {
                                    listener.onDeleteGankSuccess()
                                }else{
                                    listener.onDeleteGankError()
                                }
                            }

                            override fun onError(throwable: Throwable?) {
                                listener.onDeleteGankError()
                            }

                            override fun onComplete() {
                            }
                        })
        )
    }

    interface OnDeleteGankListener {
        fun onDeleteGankSuccess()
        fun onDeleteGankError()
    }

    interface OnRequestDataListener {
        fun onRequestSuccess(ganks: List<GankBean>?)
        fun onRequestError(throwable: Throwable?)
    }
}