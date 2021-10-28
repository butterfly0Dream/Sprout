package com.jsx.applib.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jsx.applib.R

/**
 * Author: JackPan
 * Date: 2021-10-28
 * Time: 10:03
 * Description:
 */
class LoadingTip @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private lateinit var mEmpty: LinearLayout
    private lateinit var mLoadingView: LoadingView
    private lateinit var mInternetError: LinearLayout

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.loading_tip, this)
        mEmpty = view.findViewById(R.id.llEmpty)
        mLoadingView = view.findViewById(R.id.indicatorView)
        mInternetError = view.findViewById(R.id.llInternetError)
        visibility = View.GONE
    }

    /**
     * 设置网络重连点击事件
     */
    fun setReloadListener(reload:(View)->Unit){
        mInternetError.setOnClickListener {
            reload.invoke(it)
        }
    }

    /**
     * 显示空白页
     */
    fun showEmpty() {
        visibility = View.VISIBLE
        mEmpty.visibility = View.VISIBLE
        mLoadingView.visibility = View.GONE
        mLoadingView.hide()
        mInternetError.visibility = View.GONE
    }

    /**
     * 显示网络错误
     */
    fun showInternetError() {
        visibility = View.VISIBLE
        mInternetError.visibility = View.VISIBLE
        mEmpty.visibility = View.GONE
        mLoadingView.visibility = View.GONE
        mLoadingView.hide()
    }

    /**
     * 加载
     */
    fun loading() {
        visibility = View.VISIBLE
        mLoadingView.visibility = View.VISIBLE
        mLoadingView.show()
        mInternetError.visibility = View.GONE
        mEmpty.visibility = View.GONE

    }

    /**
     * 隐藏loadingTip
     */
    fun dismiss() {
        mLoadingView.hide()
        visibility = View.GONE
    }
}