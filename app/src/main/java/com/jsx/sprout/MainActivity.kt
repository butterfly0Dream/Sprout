package com.jsx.sprout

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.jsx.applib.base.BaseVmActivity
import com.jsx.applib.common.TAG
import com.jsx.applib.utils.LanguageUtils
import com.jsx.applib.utils.SPUtils
import com.jsx.sprout.constants.SPConstants

class MainActivity : BaseVmActivity() {

    override fun attachBaseContext(newBase: Context?) {
        // 这里，请读者使用SharePreference获取已保存的语言设置内容，关键字A，同上
        // 获取我们存储的语言环境，比如 "en","zh",等等
        val language = SPUtils.getString(SPConstants.LANGUAGE, "zh") ?: "zh"

        /**
         * attach对应语言环境下的context
         * 继承自LanguageUtil.attachBaseContext的方法，用于初始化语言设置
         */
        Log.d(TAG, "attachBaseContext: $language, ${LanguageUtils.getSystemLanguage().language}")
        val context = if (language == "system") {
            Log.d(TAG, "attachBaseContext: ")
            LanguageUtils.attachBaseContext(newBase, LanguageUtils.getSystemLanguage().language)
        } else {
            LanguageUtils.attachBaseContext(newBase, language)
        }
        super.attachBaseContext(context)
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId() = R.layout.activity_main
}