package com.jsx.applib.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

/**
 * Author: JackPan
 * Date: 2021-10-12
 * Time: 14:49
 * Description:
 */
abstract class LazyVmFragment<BD:ViewDataBinding> : BaseVmFragment<BD>() {
    private var mLoaded = false

    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!mLoaded && !isHidden) {
            lazyInit()
            mLoaded = true
        }
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mLoaded = false
    }

    abstract fun lazyInit()
}