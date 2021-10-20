package com.jsx.sprout.ui.search

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-20
 * Time: 09:44
 * Description:
 */
class SearchRepo : BaseRepository() {

    /**
     * 页码
     */
    private var page = 0

    /**
     * 搜索
     */
    suspend fun search(keyWord: String) = withIO {
        page = 0
        RetrofitManager.getApiService(ApiService::class.java)
            .search(page, keyWord)
            .data()
            .let {
                ArticleListBean.trans(it.datas ?: mutableListOf())
            }
    }

    suspend fun loadMore(keyWord: String) = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .search(page, keyWord)
            .data()
            .let {
                ArticleListBean.trans(it.datas ?: mutableListOf())
            }
    }
}