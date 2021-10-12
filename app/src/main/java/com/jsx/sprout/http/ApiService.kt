package com.jsx.sprout.http

import com.jsx.sprout.bean.ArticleBean
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author: JackPan
 * Date: 2021-10-09
 * Time: 14:07
 * Description:
 */
interface ApiService {
    /**
     * 获取首页文章数据
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeList(@Path("page") pageNo: Int): ApiResponse<ArticleBean>
}