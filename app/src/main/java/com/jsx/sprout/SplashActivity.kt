package com.jsx.sprout

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.jinrishici.sdk.android.JinrishiciClient
import com.jinrishici.sdk.android.factory.JinrishiciFactory
import com.jinrishici.sdk.android.listener.JinrishiciCallback
import com.jinrishici.sdk.android.model.JinrishiciRuntimeException
import com.jinrishici.sdk.android.model.PoetySentence
import com.jsx.applib.base.BaseVmActivity
import com.jsx.applib.common.TAG
import com.jsx.applib.utils.LogUtil
import com.jsx.applib.utils.SPUtils
import com.jsx.sprout.constants.SPConstants
import kotlinx.coroutines.delay

class SplashActivity : BaseVmActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        changeTheme()
        super.onCreate(savedInstanceState)
        val tv:TextView = findViewById(R.id.tv_motto)
        JinrishiciFactory.init(this)
        JinrishiciClient.getInstance().init(this)
        JinrishiciClient.getInstance().getOneSentenceBackground(object : JinrishiciCallback{
            override fun done(poetySentence: PoetySentence?) {
                tv.text = poetySentence?.data?.content
            }

            override fun error(e: JinrishiciRuntimeException?) {
            }
        })
        }

    override fun init(savedInstanceState: Bundle?) {
        // 使用协程时一定要注意coroutineScope的生命周期，特别是延迟和定时任务
        // 使用lifecycleScope创建一个与当前lifecycleOwner同生命周期的协程,避免内存泄漏
        lifecycleScope.launchWhenResumed { startMain() }
    }

    override fun getLayoutId() = R.layout.activity_splash

    /**
     * 开始倒计时跳转
     */
    private suspend fun startMain() {
        LogUtil.d(TAG, "开始计时")
        delay(2500)
        LogUtil.d(TAG, "结束计时")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /**
     * 动态切换主题
     */
    private fun changeTheme() {
        val theme = SPUtils.getBoolean(SPConstants.DARK_MODE, false)
        if (theme) {
            setTheme(R.style.AppTheme_Night)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}