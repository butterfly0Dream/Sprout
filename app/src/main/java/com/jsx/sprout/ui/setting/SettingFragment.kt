package com.jsx.sprout.ui.setting

import android.os.Bundle
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.toast
import com.jsx.sprout.R
import com.jsx.sprout.SharedViewModel
import com.jsx.sprout.constants.Constants
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
        mState.logoutLiveData.observe(this, {
            toast("已退出登陆")
            nav().navigateUp()
            mEvent.loginState.value = false
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mState
//        setNightMode()
    }

    override fun onClick() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
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
            if (!CacheUtil.isLogin()){
                toast("请先登陆～")
                return@clickNoRepeat
            }
            DialogUtils.confirm(activity,"确定退出登录？"){
                mState.logout()
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_setting
}