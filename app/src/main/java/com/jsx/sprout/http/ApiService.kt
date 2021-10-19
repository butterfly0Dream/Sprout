package com.jsx.sprout.http

import com.jsx.sprout.bean.ArticleBean
import com.jsx.sprout.ui.main.home.BannerBean
import com.jsx.sprout.ui.login.UserBean
import com.jsx.sprout.ui.main.tab.TabBean
import retrofit2.http.*

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

    /**
     * 获取首页置顶文章数据
     */
    @GET("/article/top/json")
    suspend fun getTopList(): ApiResponse<MutableList<ArticleBean.DatasBean>>

    /**
     * banner
     */
    @GET("/banner/json")
    suspend fun getBanner(): ApiResponse<MutableList<BannerBean>>

    /**
     * 收藏
     */
    @POST("/lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResponse<Any>

    /**
     * 取消收藏
     */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int): ApiResponse<Any>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<UserBean>

    /**
     * 退出
     */
    @GET("/user/logout/json")
    suspend fun logout(): ApiResponse<Any>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResponse<Any>

    /**
     * 获取项目分类tab
     */
    @GET("/project/tree/json")
    suspend fun getProjectTabList(): ApiResponse<MutableList<TabBean>>

    /**
     * 获取微信公众号分类tab
     */
    @GET("/wxarticle/chapters/json")
    suspend fun getAccountTabList(): ApiResponse<MutableList<TabBean>>

    /**
     * 获取项目列表
     */
    @GET("/project/list/{pageNum}/json")
    suspend fun getProjectList(@Path("pageNum")pageNum:Int,@Query("cid")cid:Int)
            :ApiResponse<ArticleBean>

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/list/{id}/{pageNum}/json")
    suspend fun getAccountList(@Path("id")cid:Int,@Path("pageNum")pageNum:Int)
            : ApiResponse<ArticleBean>
}