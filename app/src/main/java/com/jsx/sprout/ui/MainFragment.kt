package com.jsx.sprout.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.doSelected
import com.jsx.applib.common.initFragment
import com.jsx.sprout.R
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.databinding.FragmentMainBinding
import com.jsx.sprout.ui.main.home.HomeFragment
import com.jsx.sprout.ui.main.mine.MineFragment
import com.jsx.sprout.ui.main.tab.TabFragment

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : BaseVmFragment<FragmentMainBinding>() {
    private val fragmentList = arrayListOf<Fragment>()

    /** 首页 */
    private val homeFragment by lazy { HomeFragment() }

    /**
     * 项目
     */
    private val projectFragment by lazy {
        TabFragment().apply {
            arguments = Bundle().apply {
                putInt("type", Constants.TAB_PROJECT)
            }
        }
    }

    /**
     * 公众号
     */
    private val publicNumberFragment by lazy {
        TabFragment().apply {
            arguments = Bundle().apply {
                putInt("type",Constants.TAB_WXARTICLE)
            }
        }
    }

    /**
     * 我的
     */
    private val mineFragment by lazy { MineFragment() }

    init {
        fragmentList.apply {
            add(homeFragment)
            add(projectFragment)
            add(publicNumberFragment)
            add(mineFragment)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vpHome.initFragment(this, fragmentList)
            .run {
                // 全部缓存
                offscreenPageLimit = fragmentList.size
                // 关闭滑动到边缘后的阴影动画
                (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }
        // 页面切换后对应修改bottomNav的按钮样式
        binding.vpHome.doSelected {
            binding.btmNav.menu.getItem(it).isChecked = true
        }
        binding.btmNav.run {
            setOnItemSelectedListener{item ->
                when(item.itemId){
                    R.id.menu_home -> {
                        binding.vpHome.setCurrentItem(0, false)
                    }
                    R.id.menu_project -> binding.vpHome.setCurrentItem(1, false)
                    R.id.menu_official_account -> binding.vpHome.setCurrentItem(2, false)
                    R.id.menu_mine -> binding.vpHome.setCurrentItem(3, false)
                }
                true
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_main

}