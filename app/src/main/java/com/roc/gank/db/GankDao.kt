package com.roc.gank.db

import android.content.ContentValues
import android.text.TextUtils
import com.roc.gank.entity.GankBean

/**
 * Created by 赖鹏旭 on 2018/1/31.
 * 干货收藏夹数据库Dao
 */
class GankDao private constructor() : DBDao<GankBean>() {

    companion object {
        private var mInstance: GankDao? = null
        fun getInstance(): GankDao {
            if (mInstance == null) {
                synchronized(GankDao::class) {
                    if (mInstance == null) {
                        mInstance = GankDao()
                    }
                }
            }
            return mInstance!!
        }
    }


    @Synchronized override fun insert(t: GankBean?): Boolean {
        val contentValues = ContentValues()
        contentValues.put(Database.TABLE_COLLECTION.ROW_INSERT_TIME, System.currentTimeMillis())
        contentValues.put(Database.TABLE_COLLECTION.ROW_AUTHOR, t?.who ?: "")
        contentValues.put(Database.TABLE_COLLECTION.ROW_DESCRIPTION, t?.desc ?: "")
        contentValues.put(Database.TABLE_COLLECTION.ROW_URL, t?.url ?: "")
        contentValues.put(Database.TABLE_COLLECTION.ROW_CREATE_TIME, t?.createdAt ?: "")
        contentValues.put(Database.TABLE_COLLECTION.ROW_UPDATE_TIME, t?.publishedAt ?: "")
        contentValues.put(Database.TABLE_COLLECTION.ROW_PREVIEW, t?.images?.get(0) ?: "")
        contentValues.put(Database.TABLE_COLLECTION.ROW_TYPE, t?.type ?: "")
        contentValues.put(Database.TABLE_COLLECTION.ROW_SOURCE, t?.source ?: "")
        contentValues.put(Database.TABLE_COLLECTION.ROW_USED, t?.used ?: true)

        // 插入
        val replace = getOpenHelper().writableDatabase?.replace(
                Database.TABLE_COLLECTION.TABLE_NAME,
                null, contentValues)
        getOpenHelper().writableDatabase?.close()
        return replace != -1L
    }

    @Synchronized override fun delete(t: GankBean?): Boolean {
        val affectedRow = getOpenHelper().writableDatabase?.delete(
                Database.TABLE_COLLECTION.TABLE_NAME,
                Database.TABLE_COLLECTION.ROW_URL + " = ?",
                arrayOf(t?.url)
        )
        getOpenHelper().writableDatabase?.close()
        return affectedRow != 0
    }

    @Synchronized override fun update(t: GankBean?) {

    }

    override fun queryAll(): List<GankBean> {
        val cursor = getOpenHelper().readableDatabase.query(Database.TABLE_COLLECTION.TABLE_NAME, null, null, null, null, null, Database.TABLE_COLLECTION.ROW_INSERT_TIME + " DESC")

        val ganks = ArrayList<GankBean>()
        while (cursor.moveToNext()) {
            val gankBean = GankBean()
            val author = cursor.getString(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_AUTHOR))
            val description = cursor.getString(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_DESCRIPTION))
            val url = cursor.getString(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_URL))
            val createTime = cursor.getString(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_CREATE_TIME))
            val updateTime = cursor.getString(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_UPDATE_TIME))
            val preview = cursor.getString(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_PREVIEW))
            val type = cursor.getString(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_TYPE))
            val source = cursor.getString(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_SOURCE))
            val used = cursor.getInt(cursor.getColumnIndex(Database.TABLE_COLLECTION.ROW_USED))
            gankBean.who = author ?: ""
            gankBean.desc = description ?: ""
            gankBean.url = url ?: ""
            gankBean.createdAt = createTime ?: ""
            gankBean.publishedAt = updateTime ?: ""
            if (!TextUtils.isEmpty(preview)) {
                val images = ArrayList<String>()
                images.add(preview)
                gankBean.images = images
            }
            gankBean.type = type ?: ""
            gankBean.source = source ?: ""
            gankBean.used = used == 1
            ganks.add(gankBean)
        }

        if (!(cursor.isClosed))
            cursor.close()
        getOpenHelper().readableDatabase?.close()

        return ganks
    }
}