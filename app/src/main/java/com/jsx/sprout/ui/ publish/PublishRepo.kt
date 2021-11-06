package com.jsx.sprout.ui.publish

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

class PublishRepo : BaseRepository() {

    /**
     * 发布
     * @param title 文章标题
     * @param link  文章链接
     */
    suspend fun publish(title:String,link:String) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .publishArticle(title,link)
            .data(Any::class.java)
    }
}