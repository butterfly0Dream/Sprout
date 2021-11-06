package com.jsx.sprout.ui.rank

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 15:20
 * Description:
 */
class RankRepo : BaseRepository() {
    private var page = 1

    suspend fun getRank() = withIO {
        page = 1
        RetrofitManager.getApiService(ApiService::class.java)
            .getRank(page)
            .data()
            .datas
            ?.toMutableList()
    }

    suspend fun loadMore() = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getRank(page)
            .data()
            .datas
            ?.toMutableList()
    }
}