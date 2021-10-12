package com.jsx.applib.utils

import android.graphics.Color
import android.text.TextUtils
import androidx.core.content.ContextCompat
import com.jsx.applib.BaseApp
import java.lang.Exception

/**
 * Author: JackPan
 * Date: 2021-10-11
 * Time: 10:12
 * Description: 颜色处理工具类
 */
object ColorUtils {
    /**
     * 解析颜色
     *
     * @param colorS
     * @param defaultColor
     * @return
     */
    fun parseColor(colorS: String, defaultColor: Int): Int {
        var colorStr = colorS
        return if (TextUtils.isEmpty(colorStr)) {
            defaultColor
        } else try {
            if (!colorStr.startsWith("#")) {
                colorStr = "#$colorStr"
            }
            Color.parseColor(colorStr)
        } catch (e: Exception) {
            defaultColor
        }
    }

    fun parseColor(colorS: String): Int {
        var colorStr = colorS
        return if (TextUtils.isEmpty(colorStr)) {
            0
        } else try {
            if (!colorStr.startsWith("#")) {
                colorStr = "#$colorStr"
            }
            Color.parseColor(colorStr)
        } catch (e: Exception) {
            0
        }
    }

    /**
     * 解析颜色
     *
     * @param color
     * @return
     */
    fun parseColor(color: Int): Int {
        return ContextCompat.getColor(BaseApp.getContext(), color)
    }

    /**
     * 设置html字体色值
     *
     * @param text
     * @param color
     * @return
     */
    fun setTextColor(text: String, color: String): String? {
        return "<font color=#$color>$text</font>"
    }
}