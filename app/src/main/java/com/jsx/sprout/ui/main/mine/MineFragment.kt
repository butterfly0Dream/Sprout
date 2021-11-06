package com.jsx.sprout.ui.main.mine

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jsx.applib.base.LazyVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.toast
import com.jsx.sprout.R
import com.jsx.sprout.SharedViewModel
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.constants.UrlConstants
import com.jsx.sprout.databinding.FragmentMineBinding
import com.jsx.sprout.utils.CacheUtil

/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MineFragment : LazyVmFragment<FragmentMineBinding>() {

    private lateinit var mState: MineVM
    private lateinit var mEvent: SharedViewModel

    override fun getLayoutId() = R.layout.fragment_mine

    override fun initViewModel() {
        mState = getFragmentViewModel(MineVM::class.java)
        mEvent = getApplicationViewModel(SharedViewModel::class.java)
    }

    override fun lazyInit() {
        binding.vm = mState
        mState.getScore()
    }

    override fun onResume() {
        super.onResume()
        mState.getHistoryCount()
    }

    override fun observe() {
        mEvent.loginState.observe(this, {
            if (it) {
                mState.getScore()
            } else {
                mState.username.set(resources.getString(R.string.common_no_login))
                mState.id.set("---")
                mState.rank.set("0")
                mState.internal.set("0")
            }
        })
    }

    override fun onClick() {
        binding.ivHead.clickNoRepeat {
            toast("我只是一只睡着的小老鼠...")
        }
        binding.tvName.clickNoRepeat {
            if (!CacheUtil.isLogin()) {
                nav().navigate(R.id.action_main_fragment_to_login_fragment)
            }
        }
        binding.llHistory.clickNoRepeat {
            nav().navigate(R.id.action_main_fragment_to_history_fragment)
        }
        binding.llRanking.clickNoRepeat {
            if (CacheUtil.isLogin()) {
                val integralBean = mState.scoreLiveData?.value
                nav().navigate(R.id.action_main_fragment_to_rank_fragment, Bundle().apply {
                    integralBean?.apply {
                        putInt(Constants.MY_INTEGRAL, coinCount)
                        putInt(Constants.MY_RANK, rank)
                        putString(Constants.MY_NAME, username)
                    }
                })
            } else {
                toast("请先登录")
            }
        }
        binding.clIntegral.clickNoRepeat {
            if (CacheUtil.isLogin()) {
                nav().navigate(R.id.action_main_fragment_to_score_fragment)
            } else {
                toast(resources.getString(R.string.common_no_login))
            }
        }
        binding.clCollect.clickNoRepeat {
            if (CacheUtil.isLogin()) {
                nav().navigate(R.id.action_main_fragment_to_collect_fragment)
            } else {
                toast(resources.getString(R.string.common_no_login))
            }
        }
        binding.clArticle.clickNoRepeat {
            if (CacheUtil.isLogin()) {
                nav().navigate(R.id.action_main_fragment_to_my_article_fragment)
            } else {
                toast("请先登录")
            }
        }
        binding.clWebsite.clickNoRepeat {
            nav().navigate(R.id.action_main_fragment_to_web_fragment, Bundle().apply {
                putString(Constants.WEB_URL, UrlConstants.WEBSITE)
                putString(Constants.WEB_TITLE, Constants.APP_NAME)
            })
        }
        binding.clSet.clickNoRepeat {
            nav().navigate(R.id.action_main_fragment_to_set_fragment)
        }
    }
}