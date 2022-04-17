package com.jsx.applib.base

import android.view.View
import android.widget.FrameLayout
import com.jsx.applib.R
import com.jsx.applib.common.TAG
import com.jsx.applib.common.dip2px
import com.jsx.applib.utils.LogUtil
import com.jsx.applib.view.LoadingView
import com.jsx.applib.view.lce.DefaultLceImpl
import com.jsx.applib.view.lce.ILce

/**
 * Author: JackPan
 * Date: 2021-11-05
 * Time: 13:55
 * Description:
 */
abstract class BaseLceActivity : BaseVmActivity(), ILce {

    /**
     * Activity中显示加载等待的控件。
     */
    private var mLoading: LoadingView? = null

    /**
     * Activity中由于服务器异常导致加载失败显示的布局。
     */
    private var mLoadErrorView: View? = null

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private var mBadNetworkView: View? = null

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private var mNoContentView: View? = null

    private var mDefaultLce: ILce? = null

    override fun setContentView(view: View?) {
        super.setContentView(view)
        setupViews()
    }

    protected open fun setupViews() {
        val view = View.inflate(this, R.layout.layout_lce, null)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(
            0,
            dip2px(this, 70f),
            0,
            0
        )
        addContentView(view, params)
        mLoading = view.findViewById(R.id.loading)
        mNoContentView = view.findViewById(R.id.noContentView)
        mBadNetworkView = view.findViewById(R.id.badNetworkView)
        mLoadErrorView = view.findViewById(R.id.loadErrorView)
        if (mLoading == null) {
            LogUtil.e(TAG, "loading is null")
        }
        if (mBadNetworkView == null) {
            LogUtil.e(TAG, "badNetworkView is null")
        }
        if (mLoadErrorView == null) {
            LogUtil.e(TAG, "loadErrorView is null")
        }
        mDefaultLce = DefaultLceImpl(
            view,
            mLoading,
            mLoadErrorView,
            mBadNetworkView,
            mNoContentView
        )
        loadFinished()
    }

    override fun showLoading() {
        mDefaultLce?.showLoading()
    }

    override fun loadFinished() {
        mDefaultLce?.loadFinished()
    }

    override fun showLoadErrorView(tip: String) {
        mDefaultLce?.showLoadErrorView(tip)
    }

    override fun showBadNetworkView(listener: View.OnClickListener) {
        mDefaultLce?.showBadNetworkView(listener)
    }

    override fun showNoContentView(tip: String) {
        mDefaultLce?.showNoContentView(tip)
    }

}