package com.jsx.sprout.ui.main.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.ui.common.CollectRequest

/**
 * Author: JackPan
 * Date: 2021-10-19
 * Time: 10:59
 * Description:
 */
class ArticleListVM : BaseViewModel() {

    private val mRepo by lazy { ArticleListRepo() }
    private val mCollectRequest by lazy { CollectRequest(mArticleLiveData) }

    /**
     * 体系列表数据
     */
    private val mArticleLiveData = MutableLiveData<MutableList<ArticleListBean>>()
    val articleLiveData: LiveData<MutableList<ArticleListBean>> = mArticleLiveData

    /**
     * 获取第一页文章列表
     */
    fun getArticleList(type:Int,tabId:Int) {
        launch {
            mArticleLiveData.value = mRepo.getArticles(type, tabId)
            handleList(mArticleLiveData)
        }
    }

    /**
     * 获取下一页文章列表
     */
    fun loadMoreArticleList(type:Int,tabId:Int) {
        launch {
            val list = mArticleLiveData.value
            list?.addAll(mRepo.loadMoreArticles(type, tabId))
            mArticleLiveData.value = list
            handleList(mArticleLiveData)
        }
    }

    /**
     * 收藏
     */
    fun collect(id: Int) {
        launch {
            mCollectRequest.collect(id)
        }
    }

    /**
     * 取消收藏
     */
    fun unCollect(id: Int) {
        launch {
            mCollectRequest.unCollect(id)
        }
    }
}