package com.jsx.sprout.ui.main.home

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-09
 * Time: 17:32
 * Description:
 */
class HomeRepo : BaseRepository() {

    private var page = 0

    /**
     * 请求第一页
     */
    suspend fun getArticles() = withIO {
        page = 0
        RetrofitManager.getApiService(ApiService::class.java)
            .getHomeList(page)
            .data()
            .datas?.let {
                ArticleListBean.trans(it)
            } ?: mutableListOf()
    }

    /**
     * 请求下一页
     */
    suspend fun loadMoreArticles() = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getHomeList(page)
            .data()
            .datas?.let {
                ArticleListBean.trans(it)
            } ?: mutableListOf()
    }


}