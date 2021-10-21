package com.jsx.sprout.ui.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel

/**
 * Author: JackPan
 * Date: 2021-10-21
 * Time: 09:58
 * Description:
 */
class ScoreVM : BaseViewModel() {
    private val repo by lazy { ScoreRepo() }

    /**
     * 收藏的的文章
     */
    private val _integralLiveData = MutableLiveData<MutableList<ScoreListBean>>()
    val integralLiveData: LiveData<MutableList<ScoreListBean>> = _integralLiveData

    /**
     * 获取收藏列表
     */
    fun getIntegral() {
        launch {
            _integralLiveData.value = repo.getIntegral()
            handleList(integralLiveData)
        }
    }

    /**
     * 获取收藏列表
     */
    fun loadMore() {
        launch {
            val list =  integralLiveData.value
            list?.addAll(repo.loadMore())
            _integralLiveData.value = list
            handleList(integralLiveData)
        }
    }
}