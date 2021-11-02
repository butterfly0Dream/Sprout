package com.jsx.applib.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.util.DisplayMetrics
import com.jsx.applib.BaseApp
import java.util.*

/**
 * Author: JackPan
 * Date: 2021-10-29
 * Time: 15:17
 * Description:
 */
object LanguageUtils {

    // 该方法用于bnt或radioBnt随时切换语言时调用，无返回
    fun changeAppLanguage(context: Context = BaseApp.getContext(), language: String){
        if (language.isEmpty()) {
            return
        }
//        val resources: Resources = context.resources
//        val config: Configuration = resources.configuration
//        // 获得屏幕参数：主要是分辨率，像素等。
//        val dm: DisplayMetrics = resources.displayMetrics
//        // 切换语言
//        config.setLocale(getLocaleByLanguage(language))
//        resources.updateConfiguration(config, dm)
        changeAppLanguage(context, getLocaleByLanguage(language))
    }

    fun changeAppLanguage(context: Context = BaseApp.getContext(), locale: Locale){
        val resources: Resources = context.resources
        val config: Configuration = resources.configuration
        // 获得屏幕参数：主要是分辨率，像素等。
        val dm: DisplayMetrics = resources.displayMetrics
        // 切换语言
        config.setLocale(locale)
        resources.updateConfiguration(config, dm)
    }

    // 该方法为activity中的attachBaseContext提供继承内容，用于初始化切换语言
    fun attachBaseContext(context: Context?, language: String): Context? {
        return if (context != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else {
            return context
        }
    }

    /**
     * 获取系统语言
     */
    fun getSystemLanguage(): Locale{
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales.get(0)
        } else {
            Resources.getSystem().configuration.locale
        }
        return locale
    }

    // 根据想要的语言参数language，获取对应语言的Locale，更多语言请另行添加
    private fun getLocaleByLanguage(language: String): Locale {
        val myLocale: Locale = when (language) {
            "en" -> {
                Locale.ENGLISH
            }
            else -> {
                Locale.CHINESE
            }
        }
        return myLocale
    }

    // 该方法用于页面初始化时的切换语言，返回配置信息的上下文context
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context , language: String ) : Context {
        val resources :Resources = context.resources
        val locale: Locale = getLocaleByLanguage(language)
        val configuration: Configuration= resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }
}
