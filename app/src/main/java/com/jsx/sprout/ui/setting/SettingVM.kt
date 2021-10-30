package com.jsx.sprout.ui.setting

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel

/**
 * Author: JackPan
 * Date: 2021-10-20
 * Time: 14:05
 * Description:
 */
class SettingVM : BaseViewModel() {

    /**
     * 语言子项是否隐藏
     */
    val languageItemVisible = ObservableField<Boolean>()

    private val mRepo by lazy { SettingRepo() }

    val logoutLiveData = MutableLiveData<Any>()

    fun logout(){
        launch {
            logoutLiveData.value =  mRepo.logout()
        }
    }
}