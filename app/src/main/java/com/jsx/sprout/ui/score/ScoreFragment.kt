package com.jsx.sprout.ui.score

import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.smartDismiss
import com.jsx.sprout.R
import com.jsx.sprout.databinding.FragmentScoreBinding

/**
 * Author: JackPan
 * Date: 2021-10-21
 * Time: 10:22
 * Description:
 */
class ScoreFragment : BaseVmFragment<FragmentScoreBinding>() {
    /**
     * 文章适配器
     */
    private lateinit var adapter: IntegralAdapter


    private lateinit var mState: ScoreVM


    override fun initViewModel() {
        mState = getFragmentViewModel(ScoreVM::class.java)
    }

    override fun observe() {
        mState.integralLiveData.observe(viewLifecycleOwner, {
            binding.smartRefresh.smartDismiss()
//            gloding?.dismiss()
            adapter.submitList(it)
        })
        mState.footLiveDate.observe(viewLifecycleOwner, {

        })
        mState.errorLiveData.observe(viewLifecycleOwner, {
            binding.smartRefresh.smartDismiss()
            if (it.errorCode == -100) {
//                //显示网络错误
//                gloding?.showInternetError()
//                gloding?.setReloadListener {
//                    mState.getIntegral()
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
        (binding.rvIntegral.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter = IntegralAdapter().apply {
            binding.rvIntegral.adapter = this
        }

        //刷新
        binding.smartRefresh.setOnRefreshListener {
            mState.getIntegral()
        }
        //加载更多
        binding.smartRefresh.setOnLoadMoreListener {
            mState.loadMore()
        }
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun loadData() {
        mState.getIntegral()
//        gloding?.loading()
    }


    override fun getLayoutId() = R.layout.fragment_score

    override fun getLceParentId() = R.id.smart_refresh
}