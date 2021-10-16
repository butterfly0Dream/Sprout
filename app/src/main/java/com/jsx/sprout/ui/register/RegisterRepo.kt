package com.jsx.sprout.ui.register

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-16
 * Time: 09:30
 * Description:
 */
class RegisterRepo : BaseRepository() {

    suspend fun register(username: String, password: String, rePassword: String) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .register(username, password, rePassword)
            .data()
    }

}