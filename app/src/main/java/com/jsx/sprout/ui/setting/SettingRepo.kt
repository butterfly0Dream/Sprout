package com.jsx.sprout.ui.setting

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager
import com.jsx.sprout.utils.CacheUtil

/**
 * Author: JackPan
 * Date: 2021-10-20
 * Time: 14:01
 * Description:
 */
class SettingRepo : BaseRepository() {

    /**
     * 退出登陆
     */
    suspend fun logout() = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .logout()
            .data(Any::class.java)
            .apply {
                CacheUtil.resetUser()
            }
    }
}