package com.jsx.sprout.ui.main.mine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jsx.applib.base.LazyVmFragment
import com.jsx.sprout.R
import com.jsx.sprout.databinding.FragmentMineBinding

/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MineFragment : LazyVmFragment<FragmentMineBinding>() {
    override fun getLayoutId() = R.layout.fragment_mine

    override fun lazyInit() {
    }
}