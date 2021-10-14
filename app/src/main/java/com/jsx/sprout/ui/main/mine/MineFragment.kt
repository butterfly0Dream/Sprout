package com.jsx.sprout.ui.main.mine

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jsx.applib.base.LazyVmFragment
import com.jsx.applib.common.TAG
import com.jsx.applib.common.clickNoRepeat
import com.jsx.sprout.R
import com.jsx.sprout.SharedViewModel
import com.jsx.sprout.databinding.FragmentMineBinding
import com.jsx.sprout.ui.login.LoginVM

/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MineFragment : LazyVmFragment<FragmentMineBinding>() {

    private lateinit var mState: MineVM
    private lateinit var mEvent: SharedViewModel

    override fun getLayoutId() = R.layout.fragment_mine

    override fun initViewModel() {
        mState = getFragmentViewModel(MineVM::class.java)
        mEvent = getApplicationViewModel(SharedViewModel::class.java)
    }

    override fun observe() {
        mEvent.loginState.observe(this, {
            Log.d(TAG, "observe login state: $it")
        })
    }

    override fun lazyInit() {

    }

    override fun onClick() {
        binding.tvUsername.clickNoRepeat{
            nav().navigate(R.id.action_main_fragment_to_login_fragment)
        }
    }

}