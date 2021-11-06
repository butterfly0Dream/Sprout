package com.jsx.sprout.http

import com.jsx.sprout.bean.ArticleBean
import com.jsx.sprout.bean.MyArticleEntity
import com.jsx.sprout.ui.collect.CollectBean
import com.jsx.sprout.ui.login.UserBean
import com.jsx.sprout.ui.main.home.BannerBean
import com.jsx.sprout.ui.main.mine.ScoreBean
import com.jsx.sprout.ui.main.tab.TabBean
import com.jsx.sprout.ui.rank.RankBean
import com.jsx.sprout.ui.score.ScoreRecordBean
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
    suspend fun getProjectList(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int)
            : ApiResponse<ArticleBean>

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/list/{id}/{pageNum}/json")
    suspend fun getAccountList(@Path("id") cid: Int, @Path("pageNum") pageNum: Int)
            : ApiResponse<ArticleBean>

    /**
     * 按关键字搜索
     */
    @POST("/article/query/{pageNum}/json")
    suspend fun search(@Path("pageNum") pageNum: Int, @Query("k") k: String)
            : ApiResponse<ArticleBean>

    /**
     * 获取个人积分
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getScore(): ApiResponse<ScoreBean>

    /**
     * 积分记录
     */
    @GET("/lg/coin/list/{pageNum}/json")
    suspend fun getScoreRecord(@Path("pageNum") pageNum: Int): ApiResponse<ScoreRecordBean>

    /**
     * 获取收藏文章数据
     */
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectData(@Path("page") pageNo: Int):
            ApiResponse<CollectBean>

    /**
     * 排名
     */
    @GET("/coin/rank/{pageNum}/json")
    suspend fun getRank(@Path("pageNum") pageNum: Int): ApiResponse<RankBean>

    /**
     * 我分享的文章
     */
    @GET("/user/lg/private_articles/{pageNum}/json")
    suspend fun getMyArticle(@Path("pageNum")pageNum: Int) : ApiResponse<MyArticleEntity>

    /**
     * 删除分享的文章
     */
    @POST("/lg/user_article/delete/{id}/json")
    suspend fun deleteMyArticle(@Path("id")id: Int) : ApiResponse<Any>
}