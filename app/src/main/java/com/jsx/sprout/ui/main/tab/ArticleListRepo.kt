package com.jsx.sprout.ui.main.tab

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.http.ApiService
import com.jsx.sprout.http.RetrofitManager

/**
 * Author: JackPan
 * Date: 2021-10-19
 * Time: 10:56
 * Description:
 */
class ArticleListRepo : BaseRepository() {

    private var page = 1

    /**
     * 请求第一页
     */
    suspend fun getArticles(
        type: Int,
        tabId: Int
    ) = withIO {
        page = 1
        getList(page, type, tabId)
    }

    /**
     * 请求下一页
     */
    suspend fun loadMoreArticles(
        type: Int,
        tabId: Int
    ) = withIO {
        page++
        getList(page, type, tabId)
    }

    private suspend fun getList(
        page: Int,
        type: Int,
        tabId: Int
    ): MutableList<ArticleListBean> {
        //项目
        return if (type == Constants.TAB_PROJECT) {
            RetrofitManager.getApiService(ApiService::class.java)
                .getProjectList(page, tabId)
                .data()
                .let {
                    ArticleListBean.trans(it.datas ?: mutableListOf())
                }
        }
        //公号
        else {
            RetrofitManager.getApiService(ApiService::class.java)
                .getAccountList(tabId, page)
                .data()
                .let {
                    ArticleListBean.trans(it.datas ?: mutableListOf())
                }
        }
    }
}