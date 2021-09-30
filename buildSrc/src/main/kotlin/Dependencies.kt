/**
 * Author: JackPan
 * Date: 2021-09-30
 * Time: 14:15
 * Description:
 */
object Dependencies {

    object Versions {
        const val appcompat = "1.2.0+"
        const val coreKtx = "1.3.1+"
        const val constraintlayout = "1.1.3+"
        const val material = "1.3.0"
    }

    val libs = Libs

    object Libs {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
        const val material = "com.google.android.material:material:${Versions.material}"
    }

}