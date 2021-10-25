package com.jsx.sprout.ui.login

import android.os.Bundle
import android.view.View
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.toast
import com.jsx.applib.utils.KeyBoardUtil
import com.jsx.sprout.R
import com.jsx.sprout.SharedViewModel
import com.jsx.sprout.databinding.FragmentLoginBinding

/**
 * Author: JackPan
 * Date: 2021-10-13
 * Time: 15:25
 * Description:
 */
class LoginFragment : BaseVmFragment<FragmentLoginBinding>() {

    private lateinit var loginVM: LoginVM
    private lateinit var mEvent: SharedViewModel

    override fun initViewModel() {
        loginVM = getFragmentViewModel(LoginVM::class.java)
        mEvent = getApplicationViewModel(SharedViewModel::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = loginVM
        initView()
    }

    override fun initView() {
        super.initView()
    }

    override fun observe() {
        loginVM.loginLiveData.observe(viewLifecycleOwner, {
            toast(getString(R.string.login_success))
            nav().navigateUp()
            mEvent.loginState.value = true
        })

        loginVM.errorLiveData.observe(viewLifecycleOwner, {
            setViewStatus(true)
        })
    }

    override fun onClick() {
        super.onClick()
        binding.tvRegister.clickNoRepeat {
            nav().navigate(R.id.action_login_fragment_to_register_fragment)
        }
        binding.ivClear.clickNoRepeat {
            loginVM.username.set("")
        }
        binding.ivVisibility.clickNoRepeat {
            loginVM.passIsVisibility.get()
            loginVM.passIsVisibility.set(!loginVM.passIsVisibility.get()!!)
        }
        binding.rlLogin.clickNoRepeat {
            if (loginVM.username.get()!!.isEmpty()){
                toast(getString(R.string.common_check_username))
                return@clickNoRepeat
            }
            if (loginVM.password.get()!!.isEmpty()){
                toast(getString(R.string.common_check_pwd))
                return@clickNoRepeat
            }
            //关闭软键盘
            KeyBoardUtil.closeKeyboard(binding.etUsername,ctx)
            KeyBoardUtil.closeKeyboard(binding.etPassword,ctx)
            login()
        }
        binding.tvCancel.clickNoRepeat {
            nav().navigateUp()
        }
    }

    private fun login(){
        setViewStatus(false)
        loginVM.login()
    }

    /**
     * 登录时给具备点击事件的View上锁，登陆失败时解锁
     * 并且施加动画
     */
    private fun setViewStatus(lockStatus:Boolean){
        binding.rlLogin.isEnabled = lockStatus
        binding.tvRegister.isEnabled = lockStatus
        binding.tvCancel.isEnabled = lockStatus
        binding.etUsername.isEnabled = lockStatus
        binding.ivClear.isEnabled = lockStatus
        binding.etPassword.isEnabled = lockStatus
        if (lockStatus) {
            binding.tvLoginTxt.visibility = View.VISIBLE
            binding.loadingView.hide()
        }else {
            binding.tvLoginTxt.visibility = View.GONE
            binding.loadingView.show()
        }
    }

    override fun getLayoutId() = R.layout.fragment_login

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        Log.d(TAG, "onAttach: ")
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(TAG, "onCreate: ")
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        Log.d(TAG, "onCreateView: ")
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.d(TAG, "onViewCreated: ")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.d(TAG, "onStart: ")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d(TAG, "onResume: ")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d(TAG, "onPause: ")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d(TAG, "onStop: ")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.d(TAG, "onDestroyView: ")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(TAG, "onDestroy: ")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.d(TAG, "onDetach: ")
//    }
}