package com.jsx.sprout.ui.myarticle

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 16:02
 * Description:
 */
class MyArticleRepo : BaseRepository() {

    private var page = 1

    suspend fun getMyArticle() = withIO {
        page = 1
        RetrofitManager.getApiService(ApiService::class.java)
            .getMyArticle(page)
            .data()
            .shareArticles
            ?.datas
    }

    suspend fun loadMore() = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getMyArticle(page)
            .data()
            .shareArticles
            ?.datas
    }

    suspend fun delete(id: Int) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .deleteMyArticle(id)
            .data(Any::class.java)
    }

}