package com.roc.gank.mvp.model

import com.roc.gank.db.GankDao
import com.roc.gank.entity.GankBean
import com.roc.gank.entity.GankPage
import com.roc.gank.retrofit.ApiManager
import com.roc.gank.retrofit.api.GankIOApiService
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by 赖鹏旭 on 2018/1/25.
 * 主界面数据层
 */
class MainModel : BaseModel() {

    /**
     * 请求数据
     */
    fun requestData(pageNum: Int, listener: OnRequestDataListener) {
        mDisposable?.add(
                ApiManager
                        .instance
                        .requestGankPage(GankIOApiService.TYPE_ANDROID, pageNum)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith<DisposableObserver<GankPage>>(object : DisposableObserver<GankPage>() {
                            override fun onNext(page: GankPage?) {
                                listener.onRequestDataSuccess(page!!)
                            }

                            override fun onError(throwable: Throwable?) {
                                listener.onRequestDataError(throwable!!)
                            }

                            override fun onComplete() {
                            }
                        })
        )
    }

    /**
     * 保存干货到数据库
     */
    fun saveGankToDB(gankBean: GankBean?, listener: OnInsertCollectionListener) {
        mDisposable?.add(
                Observable
                        .create(ObservableOnSubscribe<Boolean> { emitter ->
                            val isInserted = GankDao.getInstance().insert(gankBean)
                            emitter.onNext(isInserted)
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith<DisposableObserver<Boolean>>(object : DisposableObserver<Boolean>() {
                            override fun onNext(isInserted: Boolean?) {
                                if (isInserted ?: false) {
                                    listener.onInsertCollectionSuccess()
                                } else {
                                    listener.onInsertCollectionError()
                                }
                            }

                            override fun onError(throwable: Throwable?) {
                                throwable?.printStackTrace()
                                listener.onInsertCollectionError()
                            }

                            override fun onComplete() {
                            }
                        })
        )
    }

    interface OnInsertCollectionListener {
        fun onInsertCollectionSuccess()
        fun onInsertCollectionError()
    }

    interface OnRequestDataListener {
        fun onRequestDataSuccess(page: GankPage)
        fun onRequestDataError(throwable: Throwable)
    }

}
