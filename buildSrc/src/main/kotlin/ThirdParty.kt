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
}

object Retrofit {
    private const val retrofit_version = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
    const val gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
}