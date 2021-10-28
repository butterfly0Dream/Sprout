package com.jsx.sprout

import android.content.Intent
import android.os.Bundle
import com.jsx.applib.base.BaseVmActivity
import com.jsx.applib.common.TAG
import com.jsx.applib.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseVmActivity() {

    override fun onResume() {
        super.onResume()
        startIntent()
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId() = R.layout.activity_splash

    /**
     * 开始倒计时跳转
     */
    private fun startIntent() {
        CoroutineScope(Dispatchers.Default).launch {
            startMain()
        }
    }

    private suspend fun startMain() {
        LogUtil.d(TAG, "开始计时")
        delay(2000)
        LogUtil.d(TAG, "结束计时")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}