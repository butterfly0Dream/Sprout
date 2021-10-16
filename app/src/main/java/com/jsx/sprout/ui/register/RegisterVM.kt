package com.jsx.sprout.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel

/**
 * Author: JackPan
 * Date: 2021-10-16
 * Time: 09:30
 * Description:
 */
class RegisterVM : BaseViewModel() {

    private val mRepo by lazy { RegisterRepo() }

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
     * 二次确认密码
     */
    val rePassword = ObservableField<String>().apply {
        set("")
    }

    /**
     * 二次密码是否可见
     */
    val rePassIsVisibility = ObservableField<Boolean>().apply {
        set(false)
    }

    /**
     * 注册
     */
    val registerLiveData = MutableLiveData<Any>()
    fun register() {
        launch {
            registerLiveData.value = mRepo.register(username.get()!!, password.get()!!, rePassword.get()!!)
        }
    }
}