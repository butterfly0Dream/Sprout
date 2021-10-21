package com.jsx.sprout.ui.score

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-21
 * Time: 09:57
 * Description:
 */
class ScoreRepo : BaseRepository() {

    private var page = 1

    /**
     * 获取积分
     */
    suspend fun getIntegral() = withIO {
        page = 1
        RetrofitManager.getApiService(ApiService::class.java)
            .getScoreRecord(page)
            .data()
            .let {
                ScoreListBean.trans(it.datas?: mutableListOf())
            }
    }

    /**
     * 获取下一页积分
     */
    suspend fun loadMore() = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getScoreRecord(page)
            .data()
            .let {
                ScoreListBean.trans(it.datas?: mutableListOf())
            }
    }

}