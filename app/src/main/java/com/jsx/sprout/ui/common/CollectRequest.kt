package com.jsx.sprout.ui.common

import androidx.lifecycle.MutableLiveData
import com.jsx.sprout.bean.ArticleListBean

/**
 * Author: JackPan
 * Date: 2021-10-18
 * Time: 15:27
 * Description: 收藏／取消收藏。单独抽成request可提升复用性
 * @param listLiveData 文章liveData，收藏成功后直接更新
 */
class CollectRequest(private val listLiveData: MutableLiveData<MutableList<ArticleListBean>>) {

    private val repo by lazy { CollectRepo() }

    /**
     * 收藏
     */
    suspend fun collect(id: Int) {
        repo.collect(id)
        val list = listLiveData.value
        list?.map {
            if (id == it.id) {
                it.copy(collect = true)
            } else {
                it
            }
        }?.toMutableList().let {
            listLiveData.value = it
        }
    }

    /**
     * 取消收藏
     */
    suspend fun unCollect(id: Int) {
        repo.unCollect(id)
        val list = listLiveData.value
        list?.map {
            if (id == it.id) {
                it.copy(collect = false)
            } else {
                it
            }
        }?.toMutableList().let {
            listLiveData.value = it
        }
    }
}