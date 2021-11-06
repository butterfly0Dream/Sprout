package com.jsx.sprout.ui.rank

import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 15:21
 * Description:
 */
class RankVM : BaseViewModel() {

    private val repo by lazy { RankRepo() }

    private val _rankLiveData = MutableLiveData<MutableList<RankBean.DatasBean>>()
    val rankLiveData = _rankLiveData

    /**
     * 获取排名
     */
    fun getRank() {
        launch {
            _rankLiveData.value = repo.getRank()
        }
    }

    fun loadMore() {
        launch {
            repo.loadMore()?.let {
                val list = _rankLiveData.value
                list?.addAll(it)
                _rankLiveData.value = list
                handleList(_rankLiveData)
            }
        }
    }
}