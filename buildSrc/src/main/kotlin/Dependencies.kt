/**
 * Author: JackPan
 * Date: 2021-09-30
 * Time: 14:15
 * Description:
 */
object Versions {
    const val junit = "4.13.2"
    const val ANDROIDX_TEST_EXT = "1.1.3"
    const val ESPRESSO_CORE = "3.4.0"

    const val appcompat = "1.3.1"
    const val coreKtx = "1.6.0"
    const val constraintlayout = "2.1.1"
    const val material = "1.4.0"
    const val viewpager2 = "1.0.0"
}

object Dependencies {
    const val junit = "junit:junit:${Versions.junit}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
}

object AndroidTestingLib {
    const val testExt = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_EXT}"
    const val testEspresso = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val testRunner = "androidx.test:runner:1.4.0"
    const val testRules = "androidx.test:rules:1.4.0"
}

object Navigation {
    private const val navigation_version = "2.3.5"
    const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$navigation_version"
    const val uiKtx = "androidx.navigation:navigation-ui-ktx:$navigation_version"
}

object Room {
    private const val version = "2.3.0"
    const val runtime = "androidx.room:room-runtime:$version"
    const val compiler = "androidx.room:room-compiler:$version"
    const val ktx = "androidx.room:room-ktx:$version"
}