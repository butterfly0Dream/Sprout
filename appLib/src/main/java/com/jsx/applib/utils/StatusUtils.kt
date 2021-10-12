package com.jsx.applib.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.*
import androidx.annotation.ColorInt
import java.lang.Exception

/**
 * Author: JackPan
 * Date: 2021-10-11
 * Time: 10:00
 * Description:
 */
object StatusUtils {
    /**
     * Use default color [.defaultColor_21] between 5.0 and 6.0.
     */
    private const val USE_DEFAULT_COLOR = -1

    /**
     * Use color [.setUseStatusBarColor] between 5.0 and 6.0.
     */
    private const val USE_CUR_COLOR = -2

    /**
     * Default status bar color between 21(5.0) and 23(6.0).
     * If status color is white, you can set the color outermost.
     */
    private var defaultColor_21: Int = ColorUtils.parseColor("#33000000")

    /**
     * Setting the status bar color.
     * It must be more than 21(5.0) to be valid.
     *
     * @param color Status color.
     */
    fun setUseStatusBarColor(activity: Activity, @ColorInt color: Int) {
        var c = color
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            c = Color.GRAY
        }
        setUseStatusBarColor(activity, c, USE_CUR_COLOR)
    }

    /**
     * It must be more than 21(5.0) to be valid.
     * Setting the status bar color.Supper between 21 and 23.
     *
     * @param color        Status color.
     * @param surfaceColor Between 21 and 23,if surfaceColor == [.USE_DEFAULT_COLOR],the status color is defaultColor_21,
     * else if surfaceColor == [.USE_CUR_COLOR], the status color is color,
     * else the status color is surfaceColor.
     */
    fun setUseStatusBarColor(activity: Activity, @ColorInt color: Int, surfaceColor: Int) {
        activity.window.statusBarColor =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || surfaceColor == USE_CUR_COLOR)
                color
            else if (surfaceColor == USE_DEFAULT_COLOR)
                defaultColor_21
            else surfaceColor
    }

    /**
     * Setting up whether or not to invade the status bar & status bar font color
     *
     * @param isTransparent 是否沉浸 Whether or not to invade the status bar?
     * If true, will invade the status bar,
     * otherwise, fits system windows.
     * @param isBlack       状态栏字体是否为黑色。
     * Whether the status bar font is set to black?
     * If true, the status bar font will be black,
     * otherwise, it is white.
     */
    fun setSystemStatus(activity: Activity, isTransparent: Boolean, isBlack: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isBlack){
                activity.window.insetsController?.show(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }else{
                activity.window.insetsController?.hide(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }
        }else{
            setSystemStatusOld(activity, isTransparent, isBlack)
        }
    }

    private fun setSystemStatusOld(activity: Activity, isTransparent: Boolean, isBlack: Boolean) {
        var flag = 0
        if (isTransparent) {
            flag = flag or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isBlack) {
            // after 23(6.0)
            flag = flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        activity.window.decorView.systemUiVisibility = flag

        if (isTransparent) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        val contentView = activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
        val childAt = contentView.getChildAt(0)
        if (childAt != null) {
            childAt.fitsSystemWindows = isTransparent
        }

        if (OSUtils.isEMUI3_x()) {
            if (isTransparent) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            val contentView = activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            val childAt = contentView.getChildAt(0)
            if (childAt != null) {
                childAt.fitsSystemWindows = !isTransparent
            }
        }
    }

    /**
     * Get the height of the state bar by reflection.
     *
     * @return Status bar height if it is not equal to -1,
     */
    fun getStatusBarHeight(context: Context): Int {
        return getSizeByReflection(context, "status_bar_height")
    }


    /**
     * Get the height of the state bar by reflection.
     *
     * @return Status bar height if it is not equal to -1,
     */
    fun getNavigationBarHeight(context: Context): Int {
        return getSizeByReflection(context, "navigation_bar_height")
    }

    fun getSizeByReflection(context: Context, field: String?): Int {
        var size = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = clazz.getField(field)[`object`].toString().toInt()
            size = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * Set bottom navigation bar color
     */
    fun setNavigationBar(activity: Activity, @ColorInt color: Int) {
        activity.window.navigationBarColor = color
    }
}