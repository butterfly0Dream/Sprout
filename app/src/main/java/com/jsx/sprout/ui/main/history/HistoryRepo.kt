package com.jsx.sprout.ui.main.history

import com.jsx.applib.base.BaseRepository
import com.jsx.sprout.db.AppDatabase

/**
 * Author: JackPan
 * Date: 2021-11-05
 * Time: 14:57
 * Description:
 */
class HistoryRepo : BaseRepository() {
    private val browseHistoryDao = AppDatabase.getInstance().browseHistoryDao()
    private var page = 0

    /**
     * 请求第一页
     */
    suspend fun getCount() = withIO {
        browseHistoryDao.getCount()
    }

    /**
     * 请求第一页
     */
    suspend fun getArticles() = withIO {
        page = 0
        browseHistoryDao.getHistoryArticleList(page * 20).toMutableList()
    }

    /**
     * 请求下一页
     */
    suspend fun loadMoreArticles() = withIO {
        page++
        browseHistoryDao.getHistoryArticleList(page * 20).toMutableList()
    }
}