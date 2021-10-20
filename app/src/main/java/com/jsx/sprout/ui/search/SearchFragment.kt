package com.jsx.sprout.ui.search

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.SimpleItemAnimator
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.*
import com.jsx.applib.utils.KeyBoardUtil
import com.jsx.applib.utils.LogUtil
import com.jsx.applib.utils.SPUtils
import com.jsx.applib.utils.ScreenUtils
import com.jsx.sprout.R
import com.jsx.sprout.common.ArticleAdapter
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.databinding.FragmentSearchBinding
import com.jsx.sprout.utils.CacheUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Author: JackPan
 * Date: 2021-10-20
 * Time: 10:03
 * Description:
 */
class SearchFragment : BaseVmFragment<FragmentSearchBinding>() {

    private lateinit var mState: SearchVM

    private var mRecordList: MutableList<String>? = null

    /**
     * 文章适配器
     */
    private lateinit var mAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //自定义返回
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startSearchAnim(false)
                    mState.viewModelScope.launch {
                        back()
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun getLayoutId() = R.layout.fragment_search

    override fun onDestroyView() {
        super.onDestroyView()
        //将新的搜索记录保存在本地
        SPUtils.setObject(Constants.SEARCH_RECORD, mRecordList)
    }

    override fun initViewModel() {
        mState = getFragmentViewModel(SearchVM::class.java)
    }

    override fun observe() {
        mState.articleLiveData.observe(this, {
            binding.smartRefresh.smartDismiss()
            mAdapter.submitList(it)
//            gloding?.dismiss()
        })

        mState.errorLiveData.observe(this, {
            binding.smartRefresh.smartDismiss()
            if (it.errorCode == -100) {
                //显示网络错误
//                gloding?.showInternetError()
//                gloding?.setReloadListener {
//                    searchVM.search()
//                }
            }
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mState
        //获取收缩记录
        mRecordList = if (SPUtils.getObject(Constants.SEARCH_RECORD) == null) {
            mutableListOf()
        } else {
            SPUtils.getObject(Constants.SEARCH_RECORD) as MutableList<String>?
        }
        initView()
        loadData()
    }

    override fun initView() {
        //关闭更新动画
        (binding.rvSearch.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter = ArticleAdapter(activity).apply {
            setOnItemClickListener { i, _ ->
                nav().navigate(
                    R.id.action_search_fragment_to_web_fragment,
                    this@SearchFragment.mAdapter.getBundle(i)
                )
            }
            setOnItemChildClickListener { i, view ->
                when (view.id) {
                    //收藏
                    R.id.iv_collect -> {
                        if (CacheUtil.isLogin()) {
                            this@SearchFragment.mAdapter.currentList[i].apply {
                                //已收藏取消收藏
                                if (collect) {
                                    mState.unCollect(id)
                                } else {
                                    mState.collect(id)
                                }
                            }
                        } else {
                            nav().navigate(R.id.action_search_fragment_to_login_fragment)
                        }
                    }
                }
            }
            binding.rvSearch.adapter = this
        }
        startSearchAnim(true)
        //加载更多
        binding.smartRefresh.setOnLoadMoreListener {
            search(false)
        }
        //editText获取焦点
        binding.etSearch.requestFocus()
        KeyBoardUtil.openKeyboard(binding.etSearch, activity)
        addListener()
    }

    /**
     * 为EditText添加监听事件，以及搜索按钮
     */
    private fun addListener() {
        //搜索框监听事件
        binding.etSearch.doAfterTextChanged {
            //搜索框为空的时候显示搜索历史
            if (TextUtils.isEmpty(mState.keyWord.get()!!)) {
                loadRecord()
                //隐藏清除按钮
                binding.ivClear.visibility = View.GONE

                binding.smartRefresh.visibility = View.GONE
                binding.clRecord.visibility = View.VISIBLE
            } else {
                //显示清除按钮
                binding.ivClear.visibility = View.VISIBLE
                binding.etSearch.setSelection(mState.keyWord.get()!!.length)
            }
        }
        //添加搜索按钮
        binding.etSearch.keyBoardSearch {
            //将关键字空格去除
            val keyWord = mState.keyWord.get()!!.trim { it <= ' ' }
            //如果关键字部位null或者""
            if (!TextUtils.isEmpty(keyWord)) {
                //将已存在的key移除，避免存在重复数据
                for (index in 0 until mRecordList?.size!!) {
                    if (mRecordList!![index] == keyWord) {
                        mRecordList!!.removeAt(index)
                        break
                    }
                }
                mRecordList?.add(keyWord)
                search(true)
            }
        }
    }

    override fun onClick() {
        binding.ivClear.clickNoRepeat {
            mState.keyWord.set("")
        }
        binding.tvClear.clickNoRepeat {
            mRecordList?.clear()
            loadRecord()
        }
    }

    override fun loadData() {
        loadRecord()
        startLabelAnim()
    }

    /**
     * 开启搜索动画
     * @param isIn 是否为进
     */
    private fun startSearchAnim(isIn: Boolean) {
        //搜索按钮初始宽度为：屏幕宽度-140dp
        val searchWidth = ScreenUtils.getScreenWidth(activity) - dip2px(activity, 140f)
        //搜索按钮目标宽度为：屏幕宽度-32dp
        val targetWidth = ScreenUtils.getScreenWidth(activity) - dip2px(activity, 32f)

        //进入
        val anim = if (isIn) {
            ValueAnimator.ofInt(searchWidth, targetWidth)
        }
        //退出
        else {
            ValueAnimator.ofInt(targetWidth, searchWidth)
        }
        anim.duration = 249
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            //平滑的，动态的设置宽度
            val params = binding.clSearch.layoutParams as ViewGroup.MarginLayoutParams
            params.width = value
            binding.clSearch.layoutParams = params
        }
        anim.start()
    }

    /**
     * 标签展开动画
     * 逐个展开设置可见，并开启动画
     */
    private fun startLabelAnim() {
        for (index in 0 until binding.labelsView.childCount) run {
            val view: View = binding.labelsView.getChildAt(index)
            view.visibility = View.VISIBLE
            val aa = ScaleAnimation(0f, 1f, 0.5f, 1f)
            aa.interpolator = DecelerateInterpolator()
            aa.duration = 249
            view.startAnimation(aa)
        }
    }

    /**
     * 加载搜索tag
     */
    private fun loadRecord() {
        binding.labelsView.setLabels(mRecordList) { _, _, data ->
            data
        }
        //不知为何，在xml中设置主题背景无效
        for (child in binding.labelsView.children) {
            child.setBackgroundResource(R.drawable.ripple_tag_bg)
        }
        //标签的点击监听
        binding.labelsView.setOnLabelClickListener { _, data, _ ->
            if (data is String) {
                mState.keyWord.set(data)
                search(true)
            }
        }
    }

    /**
     * @param isRefresh 是否为初次加载
     */
    private fun search(isRefresh: Boolean) {
        binding.clRecord.visibility = View.GONE
        binding.smartRefresh.visibility = View.VISIBLE
        if (isRefresh) {
            mState.search()
//            gloding?.loading()
        } else {
            mState.loadMore()
        }
        KeyBoardUtil.closeKeyboard(binding.etSearch, activity)
    }

    private suspend fun back() {
        LogUtil.d(TAG, "开始计时")
        delay(250)
        LogUtil.d(TAG, "结束计时")
        nav().navigateUp()
    }

}