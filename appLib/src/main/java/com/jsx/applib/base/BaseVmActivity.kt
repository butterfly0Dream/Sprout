package com.jsx.applib.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jsx.applib.BaseApp
import com.jsx.applib.utils.StatusUtils

/**
 * Author: JackPan
 * Date: 2021-10-11
 * Time: 09:31
 * Description: 基础 mvvm activity
 */
abstract class BaseVmActivity : AppCompatActivity() {
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLayoutId()?.let { setContentView(it) }
        setSystemInvadeBlack()
        setStatusColor()
        initViewModel()
        observe()
        init(savedInstanceState)
    }

    /**
     * 通过baseApp获取viewModel，跟随application生命周期
     */
    protected fun <T : ViewModel?> getApplicationViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(BaseApp.getContext() as BaseApp)
        }
        return mApplicationProvider!!.get(modelClass)
    }

    /**
     * 通过activity获取viewModel，跟随activity生命周期
     */
    protected fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T? {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider?.get(modelClass)
    }

    /**
     * 设置状态栏背景颜色
     */
    open fun setStatusColor() {
        // 设置了沉浸式状态栏（FLAG_TRANSLUCENT_STATUS）后，setStatusBarColor实际上是无效的
//        StatusUtils.setUseStatusBarColor(this, ColorUtils.parseColor("#00ffffff"))
    }

    /**
     * 沉浸式状态
     */
    open fun setSystemInvadeBlack() {
        //第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtils.setSystemStatus(this, true, true)
    }

    /**
     * 初始化viewModel
     * 之所以没有设计为抽象，是因为部分简单activity可能不需要viewModel
     * observe同理
     */
    open fun initViewModel() {
    }

    /**
     * 注册观察者
     */
    open fun observe() {

    }

    /**
     * activity入口
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 获取layout布局
     */
    abstract fun getLayoutId(): Int?
}