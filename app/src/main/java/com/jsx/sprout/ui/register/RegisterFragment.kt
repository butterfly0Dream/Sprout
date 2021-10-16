package com.jsx.sprout.ui.register

import android.os.Bundle
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.toast
import com.jsx.applib.utils.KeyBoardUtil
import com.jsx.sprout.R
import com.jsx.sprout.databinding.FragmentRegisterBinding

/**
 * Author: JackPan
 * Date: 2021-10-16
 * Time: 09:30
 * Description:
 */
class RegisterFragment : BaseVmFragment<FragmentRegisterBinding>() {

    private lateinit var mState: RegisterVM

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mState
    }

    override fun initViewModel() {
        mState = getFragmentViewModel(RegisterVM::class.java)
    }

    override fun observe() {
        mState.registerLiveData.observe(this, {
            toast(getString(R.string.register_success))
            nav().navigateUp()
        })

        mState.errorLiveData.observe(this, {
            setViewStatus(true)
        })
    }

    override fun onClick() {
        binding.ivClose.clickNoRepeat {
            nav().navigateUp()
        }
        binding.ivClear.clickNoRepeat {
            mState.username.set("")
        }
        binding.ivVisibility.clickNoRepeat {
            mState.passIsVisibility.set(!mState.passIsVisibility.get()!!)
        }
        binding.ivVisibilityAgain.clickNoRepeat {
            mState.rePassIsVisibility.set(!mState.rePassIsVisibility.get()!!)
        }
        binding.btnRegister.clickNoRepeat {
            if (mState.username.get()!!.isEmpty()) {
                toast(getString(R.string.common_check_username))
                return@clickNoRepeat
            }
            if (mState.password.get()!!.isEmpty()) {
                toast(getString(R.string.common_check_pwd))
                return@clickNoRepeat
            }
            if (mState.rePassword.get()!!.isEmpty()) {
                toast(getString(R.string.common_check_repwd))
                return@clickNoRepeat
            }
            if (!mState.rePassword.get().equals(mState.password.get())) {
                toast(getString(R.string.common_check_2pwd))
                return@clickNoRepeat
            }
            //关闭软键盘
            KeyBoardUtil.closeKeyboard(binding.etUsername, ctx)
            KeyBoardUtil.closeKeyboard(binding.etPassword, ctx)
            register()
        }
    }

    private fun register() {
        setViewStatus(false)
        mState.register()
    }

    /**
     * 注册时给具备点击事件的View上锁，注册失败时解锁
     * 并且施加动画
     */
    private fun setViewStatus(lockStatus: Boolean) {
        binding.btnRegister.isEnabled = lockStatus
        binding.ivClose.isEnabled = lockStatus
        binding.etUsername.isEnabled = lockStatus
        binding.etPassword.isEnabled = lockStatus
        binding.etRePwd.isEnabled = lockStatus
        if (lockStatus) {
//            binding.tvLoginTxt.visibility = View.VISIBLE
//            binding.indicatorView.visibility = View.GONE
//            binding.indicatorView.hide()
        } else {
            // TODO: 2021/10/14 显示注册中动画
//            binding.tvLoginTxt.visibility = View.GONE
//            binding.indicatorView.visibility = View.VISIBLE
//            binding.indicatorView.show()
        }
    }

    override fun getLayoutId() = R.layout.fragment_register
}