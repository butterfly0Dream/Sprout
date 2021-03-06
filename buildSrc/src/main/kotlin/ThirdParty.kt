/**
 * Author: JackPan
 * Date: 2021-10-12
 * Time: 16:15
 * Description:
 */
@Suppress("SpellCheckingInspection")
object ThirdParty {
    const val okhttp = "com.squareup.okhttp3:okhttp:4.9.0"
    // cookie持久化
    const val cookieJar = "com.github.franmontiel:PersistentCookieJar:1.0.1"
    // 轮播图
    const val banner = "com.github.bingoogolapple:BGABanner-Android:3.0.1"
    // RecyclerViewAdapter
    const val baseQuickAdapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.6"
    // viewpager指示器
    const val magicIndicator = "com.github.hackware1993:MagicIndicator:1.7.0"
    // 标签列表
    const val labelsView = "com.github.donkingliang:LabelsView:1.6.5"
    // 今日诗词(官网:https://www.jinrishici.com/)
    const val jinrishici = "com.jinrishici:android-sdk:1.5"
}

object Retrofit {
    private const val retrofit_version = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
    const val gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
}

object SmartRefresh {
    private const val version = "2.0.3"
    const val core = "com.scwang.smart:refresh-layout-kernel:$version"
    const val header = "com.scwang.smart:refresh-header-classics:$version"
}

object Glide {
    private const val version = "4.12.0"
    const val glide = "com.github.bumptech.glide:glide:$version"
    // Glide Transformations
    const val transformations = "jp.wasabeef:glide-transformations:4.3.0"
}