package com.jsx.sprout.ui.collect

import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.smartDismiss
import com.jsx.sprout.R
import com.jsx.sprout.common.ArticleAdapter
import com.jsx.sprout.databinding.FragmentCollectBinding

/**
 * Author: JackPan
 * Date: 2021-10-21
 * Time: 10:39
 * Description:
 */
class CollectFragment : BaseVmFragment<FragmentCollectBinding>() {
    /**
     * 文章适配器
     */
    private lateinit var adapter: ArticleAdapter

    private lateinit var mState: CollectVM

    override fun initViewModel() {
        mState = getFragmentViewModel(CollectVM::class.java)
    }

    override fun observe() {
        mState.articleLiveData.observe(viewLifecycleOwner, {
            binding.smartRefresh.smartDismiss()
//            gloding?.dismiss()
            adapter.submitList(it)
        })

        mState.errorLiveData.observe(viewLifecycleOwner, {
            binding.smartRefresh.smartDismiss()
            if (it.errorCode == -100) {
//                //显示网络错误
//                gloding?.showInternetError()
//                gloding?.setReloadListener {
//                    mState.getCollect()
//                }
            }
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    override fun initView() {
        //关闭更新动画
        (binding.rvCollect.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter = ArticleAdapter(activity).apply {
            binding.rvCollect.adapter = this
            setOnItemClickListener { i, _ ->
                nav().navigate(
                    R.id.action_collect_fragment_to_web_fragment,
                    this@CollectFragment.adapter.getBundle(i)
                )
            }
            setOnItemChildClickListener { i, view ->
                when (view.id) {
                    //收藏
                    R.id.iv_collect -> {
                        this@CollectFragment.adapter.currentList[i].apply {
                            mState.unCollect(id)
                        }
                    }
                }
            }
        }

        //刷新
        binding.smartRefresh.setOnRefreshListener {
            mState.getCollect()
        }
        //加载更多
        binding.smartRefresh.setOnLoadMoreListener {
            mState.loadMoreCollect()
        }

        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun loadData() {
        mState.getCollect()
//        gloding?.loading()
    }
    override fun getLayoutId() = R.layout.fragment_collect

    override fun getLceParentId() = R.id.smart_refresh
}