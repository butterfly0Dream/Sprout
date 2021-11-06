package com.jsx.sprout.ui.rank

import android.os.Bundle
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.smartDismiss
import com.jsx.applib.utils.Param
import com.jsx.sprout.R
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.constants.UrlConstants
import com.jsx.sprout.databinding.FragmentRankBinding

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 15:21
 * Description:
 */
class RankFragment : BaseVmFragment<FragmentRankBinding>() {

    /**
     * 我的积分
     */
    @Param(value = Constants.MY_INTEGRAL)
    private var myIntegral: Int? = null

    /**
     * 我的排名
     */
    @Param(value = Constants.MY_RANK)
    private var myRank: Int? = null

    /**
     * 我的名称
     */
    @Param(value = Constants.MY_NAME)
    private var myName: String? = null

    private val adapter by lazy { RankAdapter() }

    private lateinit var rankVM: RankVM

    override fun initViewModel() {
        rankVM = getFragmentViewModel(RankVM::class.java)
    }

    override fun observe() {
        rankVM.rankLiveData.observe(this, {
            binding.smartRefresh.smartDismiss()
            adapter.setNewInstance(it)
            loadFinished()
        })
        rankVM.errorLiveData.observe(this, {
            binding.smartRefresh.smartDismiss()
            showBadNetworkView { loadData() }
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    override fun initView() {
        binding.tvIntegral.text = "$myIntegral"
        binding.tvRanking.text = "$myRank"

        binding.rvRank.adapter = adapter
        binding.smartRefresh.setOnRefreshListener {
            rankVM.getRank()
        }
        binding.smartRefresh.setOnLoadMoreListener {
            rankVM.loadMore()
        }
    }

    override fun loadData() {
        //自动刷新
//        binding.smartRefresh.autoRefresh()
        rankVM.getRank()
        showLoading()
    }

    override fun onClick() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        binding.ivDetail.clickNoRepeat {
            nav().navigate(R.id.action_rank_fragment_to_web_fragment, Bundle().apply {
                putString(Constants.WEB_URL, UrlConstants.INTEGRAL_RULE)
                putString(Constants.WEB_TITLE, getString(R.string.common_integral_rule))
            })
        }
    }

    override fun getLayoutId() = R.layout.fragment_rank
}