package com.jsx.sprout.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel
import com.jsx.applib.base.BaseVmFragment

/**
 * Author: JackPan
 * Date: 2021-10-13
 * Time: 15:23
 * Description:
 */
class LoginVM : BaseViewModel() {

    private val repo by lazy { LoginRepo() }

    /**
     * 用户名
     */
    val username = ObservableField<String>().apply {
        set("")
    }

    /**
     * 密码
     */
    val password = ObservableField<String>().apply {
        set("")
    }

    /**
     * 密码是否可见
     */
    val passIsVisibility = ObservableField<Boolean>().apply {
        set(false)
    }

    /**
     * 登陆
     */
    val loginLiveData = MutableLiveData<UserBean>()
    fun login(){
        launch {
            loginLiveData.value = repo.login(username.get()!!, password.get()!!)
        }
    }

}