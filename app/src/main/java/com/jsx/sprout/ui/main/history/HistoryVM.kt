package com.jsx.sprout.ui.main.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jsx.applib.base.BaseViewModel
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.ui.common.CollectRequest
import kotlinx.coroutines.async

/**
 * Author: JackPan
 * Date: 2021-11-05
 * Time: 14:57
 * Description:
 */
class HistoryVM : BaseViewModel() {
    private val repo by lazy { HistoryRepo() }
    private val collectRequest by lazy { CollectRequest(_historyList) }

    /**
     * 文章列表
     */
    private val _historyList = MutableLiveData<MutableList<ArticleListBean>>()

    /**
     * 对外部提供只读的LiveData
     */
    val historyList: LiveData<MutableList<ArticleListBean>> = _historyList

    /**
     * 获取浏览历史文章列表
     */
    fun getArticle() {
        launch {
            val list = mutableListOf<ArticleListBean>()
            val articles = viewModelScope.async {
                repo.getArticles()
            }
            list.addAll(articles.await())
            _historyList.value = list
        }
    }

    /**
     * 加载更多
     */
    fun loadMoreArticle() {
        launch {
            val list = _historyList.value
            list?.addAll(repo.loadMoreArticles())
            _historyList.value = list
            handleList(_historyList)
        }
    }

    /**
     * 收藏
     */
    fun collect(id: Int) {
        launch {
            collectRequest.collect(id)
        }
    }

    /**
     * 取消收藏
     */
    fun unCollect(id: Int) {
        launch {
            collectRequest.unCollect(id)
        }
    }

}