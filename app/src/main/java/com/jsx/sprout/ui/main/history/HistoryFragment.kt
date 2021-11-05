package com.jsx.sprout.ui.main.history

import android.view.View
import com.jsx.applib.base.LazyVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.smartConfig
import com.jsx.applib.common.smartDismiss
import com.jsx.sprout.R
import com.jsx.sprout.common.ArticleAdapter
import com.jsx.sprout.databinding.FragmentHistoryBinding
import com.jsx.sprout.utils.CacheUtil

/**
 * Author: JackPan
 * Date: 2021-11-05
 * Time: 14:57
 * Description:
 */
class HistoryFragment : LazyVmFragment<FragmentHistoryBinding>()  {

    private lateinit var mState: HistoryVM
    private val adapter by lazy { ArticleAdapter(activity) }

    override fun initViewModel() {
        mState = getFragmentViewModel(HistoryVM::class.java)
    }

    override fun lazyInit() {
        initView()
        loadData()
    }

    override fun initView() {
        binding.smartRefresh.setOnRefreshListener {
            mState.getArticle()
        }
        //上拉加载
        binding.smartRefresh.setOnLoadMoreListener {
            mState.loadMoreArticle()
        }
        binding.smartRefresh.smartConfig()
        adapter.apply {
            binding.rvList.adapter = this
            setOnItemClickListener { i, _ ->
                nav().navigate(
                    R.id.action_history_fragment_to_web_fragment,
                    this@HistoryFragment.adapter.getBundle(i)
                )
            }
            setOnItemChildClickListener { i, view ->
                when (view.id) {
                    //收藏
                    R.id.iv_collect -> {
                        if (CacheUtil.isLogin()) {
                            this@HistoryFragment.adapter.currentList[i].apply {
                                //已收藏取消收藏
                                if (collect) {
                                    mState.unCollect(id)
                                } else {
                                    mState.collect(id)
                                }
                            }
                        } else {
                            nav().navigate(R.id.action_history_fragment_to_login_fragment)
                        }
                    }
                }
            }
        }
    }

    override fun loadData() {
        mState.getArticle()
        showLoading()
    }

    override fun observe() {
        //文章列表
        mState.historyList.observe(viewLifecycleOwner, {
            binding.smartRefresh.visibility = View.VISIBLE
            loadFinished()
            binding.smartRefresh.smartDismiss()
            adapter.submitList(it)
        })
        //请求错误
        mState.errorLiveData.observe(viewLifecycleOwner, {
            binding.smartRefresh.smartDismiss()
            showBadNetworkView{loadData()}
        })
    }

    override fun onClick() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun getLayoutId() = R.layout.fragment_history
}