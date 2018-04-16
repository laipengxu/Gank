package com.roc.gank.db

/**
 * Created by 赖鹏旭 on 2018/1/31.
 */
interface Database {

    companion object {
        val NAME: String = "RocGank.db"
    }

    /**
     * 收藏夹表
     */
    interface TABLE_COLLECTION {
        companion object {
            val TABLE_NAME: String? = "collection"
            val ROW_INSERT_TIME: String = "insert_time"
            val ROW_DESCRIPTION: String = "description"
            val ROW_CREATE_TIME: String = "create_time"
            val ROW_UPDATE_TIME: String = "update_time"
            val ROW_SOURCE: String = "source"
            val ROW_TYPE: String = "type"
            val ROW_URL: String = "url"
            val ROW_USED: String = "used"
            val ROW_AUTHOR: String = "author"
            val ROW_PREVIEW: String = "preview"
        }
    }

}