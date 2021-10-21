package com.jsx.sprout.ui.collect

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-21
 * Time: 10:27
 * Description:
 */
class CollectListRepo : BaseRepository() {

    private var page = 0

    /**
     * 请求第一页
     */
    suspend fun getCollectArticle() = withIO {
        page = 0
        RetrofitManager.getApiService(ApiService::class.java)
            .getCollectData(page)
            .data()
            .let {
                ArticleListBean.transByCollect(it.datas?: mutableListOf())
            }
    }

    /**
     * 请求下一页
     */
    suspend fun loadMoreCollectArticle() = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getCollectData(page)
            .data()
            .let {
                ArticleListBean.transByCollect(it.datas?: mutableListOf())
            }
    }

    /**
     * 取消收藏
     */
    suspend fun unCollect(id:Int) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .unCollect(id)
            //如果data可能为空,可通过此方式通过反射生成对象,避免空判断
            .data(Any::class.java)
    }
}