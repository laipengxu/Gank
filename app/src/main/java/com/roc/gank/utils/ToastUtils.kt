package com.roc.gank.utils

import android.widget.Toast
import com.roc.gank.app.GankApplication

/**
 * Created by 赖鹏旭 on 2018/3/29.
 */
object ToastUtils {
    private var mToast: Toast? = null
    fun showToast(toast: String) {
        if (mToast == null) {
            mToast = Toast.makeText(GankApplication.mInstance, toast, Toast.LENGTH_LONG)
            mToast?.show()
        } else {
            mToast?.setText(toast)
            mToast?.show()
        }
    }
}