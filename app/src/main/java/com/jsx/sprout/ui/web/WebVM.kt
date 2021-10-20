package com.jsx.sprout.ui.web

import androidx.databinding.ObservableField
import com.jsx.applib.base.BaseViewModel

/**
 * Author: JackPan
 * Date: 2021-10-20
 * Time: 10:39
 * Description:
 */
class WebVM : BaseViewModel() {

    /**
     * webView 进度
     */
    val progress = ObservableField<Int>()


    /**
     * 最大 进度
     */
    val maxProgress = ObservableField<Int>()

    /**
     * progress是否隐藏
     */
    val isVisible = ObservableField<Boolean>()
}