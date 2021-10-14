package com.jsx.sprout.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jsx.applib.base.LazyVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.sprout.R
import com.jsx.sprout.SharedViewModel
import com.jsx.sprout.databinding.FragmentHomeBinding
import com.jsx.sprout.ui.main.mine.MineVM

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : LazyVmFragment<FragmentHomeBinding>() {

    private lateinit var mState: HomeVM
    private lateinit var mEvent: SharedViewModel

    override fun getLayoutId() = R.layout.fragment_home

    override fun initViewModel() {
        mState = getFragmentViewModel(HomeVM::class.java)
        mEvent = getApplicationViewModel(SharedViewModel::class.java)
    }

    override fun lazyInit() {
    }

    override fun onClick() {
        binding.tvHome.clickNoRepeat {
            mEvent.loginState.value = true
        }
    }
}