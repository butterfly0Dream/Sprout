package com.jsx.applib.utils

import android.util.Log

/**
 * Author: JackPan
 * Date: 2021-09-30
 * Time: 16:31
 * Description:
 */
object LogUtil {
    private const val TAG = "SPROUT_LOG"

    private const val VERBOSE = 2
    private const val DEBUG = 3
    private const val INFO = 4
    private const val WARN = 5
    private const val ERROR = 6
    private const val ASSERT = 7

    private const val LOG_LEVEL = VERBOSE

    fun v(tag: String = TAG, msg: String){
        if (LOG_LEVEL <= VERBOSE){
            Log.v(tag, msg)
        }
    }

    fun d(tag: String = TAG, msg: String){
        if (LOG_LEVEL <= DEBUG){
            Log.d(tag, msg)
        }
    }

    fun i(tag: String = TAG, msg: String){
        if (LOG_LEVEL <= INFO){
            Log.i(tag, msg)
        }
    }

    fun w(tag: String = TAG, msg: String){
        if (LOG_LEVEL <= WARN){
            Log.w(tag, msg)
        }
    }

    fun e(tag: String = TAG, msg: String){
        if (LOG_LEVEL <= ERROR){
            Log.e(tag, msg)
        }
    }

}