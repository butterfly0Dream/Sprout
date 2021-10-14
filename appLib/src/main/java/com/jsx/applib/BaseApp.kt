package com.jsx.applib

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Author: JackPan
 * Date: 2021-10-09
 * Time: 14:22
 * Description:
 */
open class BaseApp : Application(), ViewModelStoreOwner {
    private lateinit var mViewModelStore:ViewModelStore

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        mViewModelStore = ViewModelStore()
    }

    companion object {
        private lateinit var baseApplication: BaseApp

        fun getContext(): Context {
            return baseApplication
        }
    }

    override fun getViewModelStore() = mViewModelStore
}