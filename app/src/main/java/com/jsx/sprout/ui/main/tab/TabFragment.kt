package com.jsx.sprout.ui.main.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jsx.applib.base.LazyVmFragment
import com.jsx.applib.common.initFragment
import com.jsx.applib.utils.Param
import com.jsx.sprout.R
import com.jsx.sprout.common.TabNavigatorAdapter
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.databinding.FragmentTabBinding
import com.jsx.sprout.view.MagicIndicatorUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter

/**
 * Author: JackPan
 * Date: 2021-10-19
 * Time: 11:43
 * Description:
 */
class TabFragment : LazyVmFragment<FragmentTabBinding>() {

    private lateinit var mState: TabVM

    /**
     * fragment 类型
     */
    @Param(value = Constants.TAB_TYPE)
    private var mType = 0

    override fun lazyInit() {
//        arguments?.apply {
//            mType = getInt("type")
//        }
        loadData()
    }

    override fun initViewModel() {
        mState = getFragmentViewModel(TabVM::class.java)
    }

    override fun observe() {
        mState.tabData.observe(viewLifecycleOwner, {
            loadFinished()
            initViewPager(it)
        })
        //请求错误
        mState.errorLiveData.observe(viewLifecycleOwner, {
            showBadNetworkView{loadData()}
        })
    }

    override fun loadData() {
        mState.getTab(mType)
        showLoading()
    }

    private fun initViewPager(tabList: MutableList<TabBean>) {
        binding.vpArticle.initFragment(this, arrayListOf<Fragment>().apply {
            tabList.forEach {
                add(ArticleListFragment().apply {
                    //想各个fragment传递信息
                    val bundle = Bundle()
                    bundle.putInt(Constants.ARTICLE_TYPE, mType)
                    bundle.putInt(Constants.ARTICLE_ID, it.id)
                    bundle.putString(Constants.ARTICLE_NAME, it.name)
                    arguments = bundle
                })
            }
        })
        //下划线绑定
        val commonNavigator = CommonNavigator(activity)
        commonNavigator.adapter = getCommonNavigatorAdapter(tabList)
        binding.tab.navigator = commonNavigator
        MagicIndicatorUtils.bindForViewPager2(binding.vpArticle, binding.tab)
    }

    /**
     * 获取下划线根跟字适配器
     */
    private fun getCommonNavigatorAdapter(tabList: MutableList<TabBean>): CommonNavigatorAdapter {
        return TabNavigatorAdapter(mutableListOf<String>().apply {
            //将tab转换为String
            tabList.forEach {
                it.name?.let { it1 -> add(it1) }
            }
        }) {
            binding.vpArticle.currentItem = it
        }
    }

    override fun getLayoutId() = R.layout.fragment_tab

    override fun getLceParentId() = R.id.vp_article
}