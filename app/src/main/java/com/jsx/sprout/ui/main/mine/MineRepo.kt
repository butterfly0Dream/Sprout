package com.jsx.sprout.ui.main.mine

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-12
 * Time: 15:10
 * Description:
 */
class MineRepo : BaseRepository() {

    suspend fun getInternal() = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .getScore()
            .data()
    }
}