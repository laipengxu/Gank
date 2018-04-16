package com.roc.gank.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.ViewConfiguration

/**
 * Created by 赖鹏旭 on 2018/1/25.
 * 底部虚拟按键工具类
 */
object NavigationBottomUtils {

    //获取虚拟按键的高度
    fun getNavigationBarHeight(context: Context): Int {
        var result = 0
        if (hasNavBar(context)) {
            val res = context.resources
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    /**
     * 检查是否存在虚拟按键栏
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun hasNavBar(context: Context): Boolean {
        val res = context.resources
        val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
        if (resourceId != 0) {
            var hasNav = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride = navBarOverride
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            return hasNav
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey()
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     */
    private val navBarOverride: String
        get() {
            var sNavBarOverride: String? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    val c = Class.forName("android.os.SystemProperties")
                    val m = c.getDeclaredMethod("get", String::class.java)
                    m.isAccessible = true
                    sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
                } catch (e: Throwable) {
                }

            }
            return sNavBarOverride ?: "1"
        }
}
