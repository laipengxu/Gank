package com.roc.gank.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.roc.gank.app.GankApplication

/**
 * Created by 赖鹏旭 on 2018/1/31.
 * 数据库Helper
 */
class DBOpenHelper : SQLiteOpenHelper(GankApplication.mInstance, Database.NAME, null, 1) {

    companion object {
        // 收藏夹建表SQL
        private val CREATE_TABLE_COLLECTION: String = "create table if not exists " +
                Database.TABLE_COLLECTION.TABLE_NAME +
                " (" + Database.TABLE_COLLECTION.ROW_INSERT_TIME + " varchar(20), " +
                Database.TABLE_COLLECTION.ROW_AUTHOR + " varchar(20), " +
                Database.TABLE_COLLECTION.ROW_DESCRIPTION + " varchar(20), " +
                Database.TABLE_COLLECTION.ROW_URL + " varchar(20) primary key, " +
                Database.TABLE_COLLECTION.ROW_CREATE_TIME + " varchar(20), " +
                Database.TABLE_COLLECTION.ROW_UPDATE_TIME + " varchar(20), " +
                Database.TABLE_COLLECTION.ROW_PREVIEW + " varchar(20), " +
                Database.TABLE_COLLECTION.ROW_TYPE + " varchar(20), " +
                Database.TABLE_COLLECTION.ROW_SOURCE + " varchar(20), " +
                Database.TABLE_COLLECTION.ROW_USED + " varchar(20));"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_COLLECTION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}