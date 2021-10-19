package com.jsx.sprout.ui.main.tab

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-19
 * Time: 10:25
 * Description:
 */
class TabRepo : BaseRepository() {

    suspend fun getTab(type: Int) = withIO {
        when (type) {
            Constants.TAB_PROJECT -> RetrofitManager.getApiService(ApiService::class.java).getProjectTabList().data()
            else -> RetrofitManager.getApiService(ApiService::class.java).getAccountTabList().data()
        }
    }

}