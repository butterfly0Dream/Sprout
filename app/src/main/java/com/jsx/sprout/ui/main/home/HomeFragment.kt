package com.jsx.sprout.ui.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import cn.bingoogolapple.bgabanner.BGABanner
import com.jsx.applib.base.LazyVmFragment
import com.jsx.applib.common.*
import com.jsx.sprout.R
import com.jsx.sprout.SharedViewModel
import com.jsx.sprout.common.ArticleAdapter
import com.jsx.sprout.common.loadUrl
import com.jsx.sprout.databinding.FragmentHomeBinding
import com.jsx.sprout.ui.main.mine.MineVM
import com.jsx.sprout.utils.CacheUtil

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : LazyVmFragment<FragmentHomeBinding>(), BGABanner.Adapter<ImageView?, String?>,
    BGABanner.Delegate<ImageView?, String?> {

    private lateinit var mState: HomeVM
    private lateinit var mEvent: SharedViewModel
    private var bannerList: MutableList<BannerBean>? = null
    private val adapter by lazy { ArticleAdapter(activity) }

    override fun getLayoutId() = R.layout.fragment_home

    override fun initViewModel() {
        mState = getFragmentViewModel(HomeVM::class.java)
        mEvent = getApplicationViewModel(SharedViewModel::class.java)
    }

    override fun lazyInit() {
        initView()
        loadData()
    }

    override fun initView() {
        binding.vm = mState
        //关闭更新动画
        (binding.rvList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.smartRefresh.setOnRefreshListener {
            mState.getBanner()
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
                    R.id.action_main_fragment_to_web_fragment,
                    this@HomeFragment.adapter.getBundle(i)
                )
            }
            setOnItemChildClickListener { i, view ->
                when (view.id) {
                    //收藏
                    R.id.iv_collect -> {
                        if (CacheUtil.isLogin()) {
                            this@HomeFragment.adapter.currentList[i].apply {
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
        //自动刷新
        mState.getBanner()
        mState.getArticle()
//        binding.loadingTip.loading()
    }

    override fun observe() {
        //文章列表
        mState.articleList.observe(this, {
            binding.smartRefresh.smartDismiss()
            adapter.submitList(it)
//            binding.loadingTip.dismiss()
        })
        //banner
        mState.banner.observe(this, {
            bannerList = it
            initBanner()
        })
        //请求错误
        mState.errorLiveData.observe(this, {
            binding.smartRefresh.smartDismiss()
        })
    }

    override fun onClick() {
        binding.bgSearch.clickNoRepeat {
            nav().navigate(R.id.action_main_fragment_to_search_fragment)
            Log.d(TAG, "onClick: bgSearch")
        }
        binding.ivAdd.clickNoRepeat {
            when (it.id) {
//                R.id.iv_add -> nav().navigate(R.id.action_main_fragment_to_publish_fragment)
                // TODO: 2021/10/18 发布文章页面
            }
        }
    }


    override fun fillBannerItem(banner: BGABanner?, itemView: ImageView?, model: String?, position: Int) {
        itemView?.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            loadUrl(activity, bannerList?.get(position)?.imagePath!!)
        }
    }

    override fun onBannerItemClick(banner: BGABanner?, itemView: ImageView?, model: String?, position: Int) {
        nav().navigate(R.id.action_main_fragment_to_web_fragment, Bundle().apply {
            bannerList?.get(position)?.let {
                putString("loadUrl", it.url)
                putString("title", it.title)
                putInt("id", it.id)
            }
        })
    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        binding.banner.apply {
            setAutoPlayAble(true)
            val views: MutableList<View> = ArrayList()
            bannerList?.forEach { _ ->
                views.add(ImageView(activity).apply {
                    setBackgroundResource(R.drawable.ripple_bg)
                })
            }
            setAdapter(this@HomeFragment)
            setDelegate(this@HomeFragment)
            setData(views)
        }
    }
}