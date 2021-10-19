package com.jsx.sprout.ui.main.tab

import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel

/**
 * Author: JackPan
 * Date: 2021-10-19
 * Time: 10:51
 * Description:
 */
class TabVM : BaseViewModel() {
    private val mRepo by lazy { TabRepo() }

    private val mTabData = MutableLiveData<MutableList<TabBean>>()
    val tabData = mTabData

    fun getTab(type: Int){
        launch {
            mTabData.value = mRepo.getTab(type)
        }
    }
}