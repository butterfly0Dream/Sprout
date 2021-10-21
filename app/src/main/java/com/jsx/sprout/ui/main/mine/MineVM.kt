package com.jsx.sprout.ui.main.mine

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel
import com.jsx.applib.utils.SPUtils
import com.jsx.sprout.constants.SPConstants
import com.jsx.sprout.utils.CacheUtil

/**
 * Author: JackPan
 * Date: 2021-10-12
 * Time: 15:09
 * Description:
 */
class MineVM : BaseViewModel() {

    /**
     * 用户名
     */
    val username = ObservableField<String>().apply {
        set("请先登录")
    }

    /**
     * id
     */
    val id = ObservableField<String>().apply {
        set("---")
    }

    /**
     * 排名
     */
    val rank = ObservableField<String>().apply {
        set("0")
    }

    /**
     * 当前积分
     */
    val internal = ObservableField<String>().apply {
        set("0")
    }

    private val mRepo by lazy { MineRepo() }
    val scoreLiveData = MutableLiveData<ScoreBean>()

    fun getScore() {
        launch {
            var integralBean:ScoreBean? = null
            SPUtils.getObject(SPConstants.SCORE_INFO)?.let {
                //先从本地获取积分，获取不到再通过网络获取
                integralBean = it as ScoreBean?
            }
            if (integralBean == null) {
                if (CacheUtil.isLogin()) {
                    val data = mRepo.getInternal()
                    setScore(data)
                    SPUtils.setObject(SPConstants.SCORE_INFO,data)
                }
            } else {
                setScore(integralBean)
            }
        }
    }

    private fun setScore(integralBean:ScoreBean?){
        integralBean?.let  { it ->
            //通过dataBinDing与View绑定
            username.set(it.username)
            id.set("${it.userId}")
            rank.set("${it.rank}")
            internal.set("${it.coinCount}")
            scoreLiveData.value = it
        }
    }
}