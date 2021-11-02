package com.jsx.sprout.ui.setting

import android.os.Build
import android.os.Bundle
import com.jsx.applib.BaseApp
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.toast
import com.jsx.applib.utils.LanguageUtils
import com.jsx.applib.utils.SPUtils
import com.jsx.sprout.R
import com.jsx.sprout.SharedViewModel
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.constants.SPConstants
import com.jsx.sprout.constants.UrlConstants
import com.jsx.sprout.databinding.FragmentSettingBinding
import com.jsx.sprout.utils.CacheUtil
import com.jsx.sprout.view.DialogUtils

/**
 * Author: JackPan
 * Date: 2021-10-20
 * Time: 14:14
 * Description:
 */
class SettingFragment : BaseVmFragment<FragmentSettingBinding>() {

    private lateinit var mState: SettingVM
    private lateinit var mEvent: SharedViewModel

    override fun initViewModel() {
        mState = getFragmentViewModel(SettingVM::class.java)
        mEvent = getApplicationViewModel(SharedViewModel::class.java)
    }

    override fun observe() {
        mState.logoutLiveData.observe(viewLifecycleOwner, {
            toast(resources.getString(R.string.common_logout_done))
            nav().navigateUp()
            mEvent.loginState.value = false
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mState
        setNightMode()
    }

    override fun onClick() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        binding.tvLanguage.clickNoRepeat {
            mState.languageItemVisible.set(
                if (mState.languageItemVisible.get() == null) {
                    true
                } else {
                    !mState.languageItemVisible.get()!!
                }
            )
        }
        // 注意: api24以上时，application的locale设置需要重建才能生效
        // 此处仅调用了activity的recreate，所有页面语言会立即生效，但是从application中获得的string等resources都是修改前的语言
        // 尽量使用activity获取resources
        binding.tvSystem.clickNoRepeat {
            val appLanguage = SPUtils.getString(SPConstants.LANGUAGE, "")
            val sysLanguage = LanguageUtils.getSystemLanguage()
            SPUtils.setString(SPConstants.LANGUAGE, Constants.LANG_SYS)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                LanguageUtils.changeAppLanguage(activity, sysLanguage)
                LanguageUtils.changeAppLanguage(BaseApp.getContext(), sysLanguage)
            }
            if (appLanguage != sysLanguage.language){
                activity.recreate()
            }
        }
        binding.tvZh.clickNoRepeat {
            SPUtils.setString(SPConstants.LANGUAGE, Constants.LANG_ZH)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                LanguageUtils.changeAppLanguage(activity, Constants.LANG_ZH)
                LanguageUtils.changeAppLanguage(BaseApp.getContext(), Constants.LANG_ZH)
            }
            activity.recreate()
        }
        binding.tvEn.clickNoRepeat {
            SPUtils.setString(SPConstants.LANGUAGE, Constants.LANG_EN)
            // 如果是android 7.0及以上系统，直接把我们想要切换的语言类型保存在SharedPreferences中即可

            // 如果是android 7.0以下，我们只需要调用changeAppLanguage方法即可
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                LanguageUtils.changeAppLanguage(activity, Constants.LANG_EN)
                LanguageUtils.changeAppLanguage( BaseApp.getContext(), Constants.LANG_EN)
//
//                // 结束当前页面，重启至首页，以刷新显示修改后的语言
//                finish()
//                val it = Intent(this, MainActivity::class.java)
//                // 清空任务栈确保当前打开activit为前台任务栈栈顶
//                // Intent.FLAG_ACTIVITY_CLEAR_TASK需搭配Intent.FLAG_ACTIVITY_NEW_TASK食用
//                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(it)
            }
            activity.recreate()
        }
        binding.tvClear.clickNoRepeat {

        }
        binding.tvVersion.clickNoRepeat {

        }
        binding.tvAuthor.clickNoRepeat {

        }
        binding.tvProject.clickNoRepeat {
            nav().navigate(R.id.action_set_fragment_to_web_fragment, Bundle().apply {
                putString(Constants.WEB_URL, UrlConstants.APP_GITHUB)
                putString(Constants.WEB_TITLE, Constants.APP_NAME)
            })
        }
        binding.tvCopyright.clickNoRepeat {

        }
        binding.tvLogout.clickNoRepeat {
            if (!CacheUtil.isLogin()) {
                toast(resources.getString(R.string.common_no_login))
                return@clickNoRepeat
            }
            DialogUtils.confirm(activity, resources.getString(R.string.common_logout_confirm)) {
                mState.logout()
            }
        }
    }

    /**
     * 却换夜间/白天模式
     */
    private fun setNightMode() {
        val theme = SPUtils.getBoolean(SPConstants.DARK_MODE,false)
        binding.scDayNight.isChecked = theme
        //不能用切换监听,否则会递归
        binding.scDayNight.clickNoRepeat {
            it.isSelected = !theme
            SPUtils.setBoolean(SPConstants.DARK_MODE, it.isSelected)
            // TODO: 2021/11/2  recreate时，activity会闪烁。
            activity.recreate()
        }
    }

    override fun getLayoutId() = R.layout.fragment_setting
}