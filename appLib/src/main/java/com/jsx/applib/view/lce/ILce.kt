package com.jsx.applib.view.lce

import android.view.View
import com.jsx.applib.BaseApp
import com.jsx.applib.R

/**
 * Author: JackPan
 * Date: 2021-11-05
 * Time: 11:44
 * Description:
 */
interface ILce {
    fun showLoading()

    fun loadFinished()

    /**
     * 当Activity中的加载内容服务器返回失败，通过此方法显示提示界面给用户。
     *
     * @param tip
     * 界面中的提示信息
     */
    fun showLoadErrorView(tip: String = BaseApp.getContext().getString(R.string.tip_load_failed))

    /**
     * 当Activity中的内容因为网络原因无法显示的时候，通过此方法显示提示界面给用户。
     *
     * @param listener
     * 重新加载点击事件回调
     */
    fun showBadNetworkView(listener: View.OnClickListener)

    /**
     * 当Activity中没有任何内容的时候，通过此方法显示提示界面给用户。
     * @param tip
     * 界面中的提示信息
     */
    fun showNoContentView(tip: String)
}