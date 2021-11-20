package com.jsx.sprout

import android.content.Context
import com.jsx.applib.BaseApp
import com.jsx.applib.utils.LanguageUtils
import com.jsx.applib.utils.SPUtils
import com.jsx.sprout.constants.SPConstants
import com.jsx.sprout.db.AppDatabase

/**
 * Author: JackPan
 * Date: 2021-10-12
 * Time: 15:12
 * Description:
 */
class App : BaseApp() {

    override fun attachBaseContext(newBase: Context?) {
        var context = newBase
        newBase?.let {
            val language = SPUtils.getString(SPConstants.LANGUAGE, "zh", newBase) ?: "zh"
            context = if (language == "system") {
                LanguageUtils.attachBaseContext(newBase, LanguageUtils.getSystemLanguage().language)
            } else {
                LanguageUtils.attachBaseContext(newBase, language)
            }
        }
        super.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
    }

    private fun initOnBackground(){
        AppDatabase.getInstance();
    }
}