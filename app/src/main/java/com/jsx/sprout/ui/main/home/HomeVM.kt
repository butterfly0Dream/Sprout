package com.jsx.sprout.ui.main.home

import com.jsx.applib.base.BaseViewModel

/**
 * Author: JackPan
 * Date: 2021-10-12
 * Time: 15:06
 * Description:
 */
class HomeVM : BaseViewModel() {
    private val repo by lazy { HomeRepo() }
}