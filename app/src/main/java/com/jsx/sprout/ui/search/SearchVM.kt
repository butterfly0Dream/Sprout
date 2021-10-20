package com.jsx.sprout.ui.search

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel
import com.jsx.applib.common.toast
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.ui.common.CollectRequest

/**
 * Author: JackPan
 * Date: 2021-10-20
 * Time: 09:47
 * Description:
 */
class SearchVM : BaseViewModel() {
    private val mRepo by lazy { SearchRepo() }
    private val mCollectRequest by lazy { CollectRequest(mArticleLiveData) }

    /**
     * 关键字，与搜索框保持一致
     */
    val keyWord = ObservableField<String>("")

    /**
     * 搜索到的文章
     */
    private val mArticleLiveData = MutableLiveData<MutableList<ArticleListBean>>()
    val articleLiveData: LiveData<MutableList<ArticleListBean>> = mArticleLiveData

    fun search() {
        if (TextUtils.isEmpty(keyWord.get())) {
            toast("请输入关键字")
            return
        }
        launch {
            mArticleLiveData.value = mRepo.search(keyWord.get()!!)
        }
    }

    fun loadMore() {
        if (TextUtils.isEmpty(keyWord.get())) {
            toast("请输入关键字")
            return
        }
        launch {
            val list = mArticleLiveData.value
            list?.addAll(mRepo.loadMore(keyWord.get()!!))
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