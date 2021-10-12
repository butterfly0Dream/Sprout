package com.jsx.sprout.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jsx.applib.base.LazyVmFragment
import com.jsx.sprout.R
import com.jsx.sprout.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : LazyVmFragment<FragmentHomeBinding>() {
    override fun getLayoutId() = R.layout.fragment_home

    override fun lazyInit() {
    }
}