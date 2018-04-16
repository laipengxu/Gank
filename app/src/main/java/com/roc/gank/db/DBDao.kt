package com.roc.gank.db

/**
 * Created by 赖鹏旭 on 2018/1/31.
 */
abstract class DBDao<T : Any> {

    protected var mHelper: DBOpenHelper? = null

    protected fun getOpenHelper(): DBOpenHelper {
        if (mHelper == null) {
            synchronized(DBDao::class) {
                if (mHelper == null) {
                    mHelper = DBOpenHelper()
                }
            }
        }
        return mHelper!!
    }

    abstract fun insert(t: T?) : Boolean
    abstract fun delete(t: T?) : Boolean
    abstract fun update(t: T?)
    abstract fun queryAll(): List<T>
}