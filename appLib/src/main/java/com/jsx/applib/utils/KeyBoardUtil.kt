package com.jsx.applib.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Author: JackPan
 * Date: 2021-10-14
 * Time: 14:29
 * Description:
 */
object KeyBoardUtil {
    /**
     * 打开软键盘
     */
    fun openKeyboard(mEditText: EditText?, mContext: Context) {
        val imm =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyboard(mEditText: EditText, mContext: Context) {
        val imm =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }
}