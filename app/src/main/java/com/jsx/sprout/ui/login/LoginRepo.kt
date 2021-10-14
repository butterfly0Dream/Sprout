package com.jsx.sprout.ui.login

import com.jsx.applib.base.BaseRepository
import com.jsx.applib.utils.SPUtils
import com.jsx.sprout.constants.SPConstants
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-13
 * Time: 15:23
 * Description:
 */
class LoginRepo : BaseRepository() {

    suspend fun login(username: String, password: String) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .login(username, password)
            .data()
            .apply {
                //登陆成功保存用户信息，并发送消息
                SPUtils.setObject(SPConstants.USER_INFO,this)
                //更改登陆状态
                SPUtils.setBoolean(SPConstants.LOGIN,true)
            }
    }
}