package com.jsx.applib

import android.app.Application
import android.content.Context

/**
 * Author: JackPan
 * Date: 2021-10-09
 * Time: 14:22
 * Description:
 */
open class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    companion object {
        private lateinit var baseApplication: BaseApp

        fun getContext(): Context {
            return baseApplication
        }
    }
}