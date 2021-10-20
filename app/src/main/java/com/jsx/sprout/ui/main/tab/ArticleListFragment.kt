package com.jsx.sprout.ui.main.tab

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.smartConfig
import com.jsx.applib.common.smartDismiss
import com.jsx.sprout.R
import com.jsx.sprout.common.ArticleAdapter
import com.jsx.sprout.databinding.FragmentArticleBinding
import com.jsx.sprout.utils.CacheUtil

/**
 * Author: JackPan
 * Date: 2021-10-19
 * Time: 11:11
 * Description:
 */
class ArticleListFragment : BaseVmFragment<FragmentArticleBinding>() {

    private lateinit var mState: ArticleListVM

    /**
     * fragment类型，项目或公号
     */
    private var mType = 0

    /**
     * tab的id
     */
    private var mTabId = 0

    /**
     * 文章适配器
     */
    private val mAdapter by lazy { ArticleAdapter(activity) }

    override fun initViewModel() {
        mState = getFragmentViewModel(ArticleListVM::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        mType = arguments?.getInt("type") ?: 0
        mTabId = arguments?.getInt("tabId") ?: 0
        initView()
        loadData()
    }

    override fun observe() {
        mState.articleLiveData?.observe(this, {
            binding.smartRefresh.smartDismiss()
//            loadingTip.dismiss()
            mAdapter.submitList(it)
        })
        mState.errorLiveData.observe(this, {
            binding.smartRefresh.smartDismiss()
            if (it.errorCode == -100) {
                //显示网络错误
//                loadingTip.showInternetError()
//                loadingTip.setReloadListener {
//                    articleVM?.getArticleList(type, tabId)
//                }
            }
        })
    }
    override fun initView() {
        //关闭更新动画
        (binding.rvArticleList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        //下拉刷新
        binding.smartRefresh.setOnRefreshListener {
            mState.getArticleList(mType, mTabId)
        }
        //上拉加载
        binding.smartRefresh.setOnLoadMoreListener {
            mState.loadMoreArticleList(mType,mTabId)
        }
        binding.smartRefresh.smartConfig()
        mAdapter.apply {
            binding.rvArticleList.adapter = this
            setOnItemClickListener { i, _ ->
                nav().navigate(
                    R.id.action_main_fragment_to_web_fragment,
                    this@ArticleListFragment.mAdapter.getBundle(i)
                )
            }
            setOnItemChildClickListener { i, view ->
                when (view.id) {
                    //收藏
                    R.id.iv_collect -> {
                        if (CacheUtil.isLogin()) {
                            this@ArticleListFragment.mAdapter.currentList[i].apply {
                                //已收藏取消收藏
                                if (collect) {
                                    mState.unCollect(id)
                                } else {
                                    mState.collect(id)
                                }
                            }
                        } else {
                            nav().navigate(R.id.action_main_fragment_to_login_fragment)
                        }
                    }
                }
            }
        }
    }

    override fun loadData() {
        mState.getArticleList(mType, mTabId)
//        loadingTip.loading()
    }

    override fun getLayoutId() = R.layout.fragment_article
}