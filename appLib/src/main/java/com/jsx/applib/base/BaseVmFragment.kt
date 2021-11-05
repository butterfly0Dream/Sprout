package com.jsx.applib.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.jsx.applib.BaseApp
import com.jsx.applib.R
import com.jsx.applib.common.TAG
import com.jsx.applib.common.dip2px
import com.jsx.applib.navigation.NavHostFragment
import com.jsx.applib.utils.LogUtil
import com.jsx.applib.utils.ParamUtils
import com.jsx.applib.view.LoadingView
import com.jsx.applib.view.lce.DefaultLceImpl
import com.jsx.applib.view.lce.ILce

/**
 * Author: JackPan
 * Date: 2021-10-12
 * Time: 10:18
 * Description:
 */
abstract class BaseVmFragment<BD : ViewDataBinding> : Fragment(), ILce {

    /**
     * Activity中显示加载等待的控件。
     */
    private var mLoading: LoadingView? = null

    /**
     * Activity中由于服务器异常导致加载失败显示的布局。
     */
    private var mLoadErrorView: View? = null

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private var mBadNetworkView: View? = null

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private var mNoContentView: View? = null
    private var mDefaultLce: ILce? = null

    /**
     * 开放给外部使用
     */
    lateinit var ctx: Context
    lateinit var activity: AppCompatActivity
    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null
    protected lateinit var binding: BD
    private var mBinding: ViewDataBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
        activity = context as AppCompatActivity
        // 必须要在Activity与Fragment绑定后，因为如果Fragment可能获取的是Activity中ViewModel
        // 必须在onCreateView之前初始化viewModel，因为onCreateView中需要通过ViewModel与DataBinding绑定
        initViewModel()
        ParamUtils.initParam(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //由于同一个fragment对象可能被activity attach多次(比如viewPager+PagerStateAdapter中)
        //所以fragmentViewModel不能放在onCreateView初始化，否则会产生多个fragmentViewModel
//        initFragmentViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getLayoutId()?.let {
            setStatusColor()
            setSystemInvadeBlack()
            //获取ViewDataBinding
            binding = DataBindingUtil.inflate(inflater, it, container, false)
            //将ViewDataBinding生命周期与Fragment绑定
            binding.lifecycleOwner = viewLifecycleOwner
            val frameLayout = FrameLayout(requireContext())
            frameLayout.addView(binding.root)
            frameLayout.addView(createLoadingView())
            return frameLayout
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
        //observe一定要在初始化最后，因为observe会收到黏性事件，随后对ui做处理
        observe()
        onClick()
    }

    /**
     * 初始化viewModel
     * 之所以没有设计为抽象，是因为部分简单activity可能不需要viewModel
     * observe同理
     */
    open fun initViewModel() {

    }

    /**
     * 注册观察者
     */
    open fun observe() {

    }

    /**
     * 点击事件
     */
    open fun onClick() {

    }

    /**
     * 设置状态栏背景颜色
     */
    open fun setStatusColor() {
        //StatusUtils.setUseStatusBarColor(mActivity, ColorUtils.parseColor("#00ffffff"))
    }

    /**
     * 沉浸式状态
     */
    open fun setSystemInvadeBlack() {
        //第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        //StatusUtils.setSystemStatus(mActivity, true, true)
    }

    /**
     * 初始化View以及事件
     */
    open fun initView() {

    }

    /**
     * 加载数据
     */
    open fun loadData() {

    }

    /**
     * 通过baseApp获取viewModel，跟随application生命周期
     */
    protected fun <T : ViewModel?> getApplicationViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(BaseApp.getContext() as BaseApp)
        }
        return mApplicationProvider!!.get(modelClass)
    }

    /**
     * 通过activity获取viewModel，跟随activity生命周期
     */
    protected fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(activity)
        }
        return mActivityProvider!!.get(modelClass)
    }

    /**
     * 通过fragment获取viewModel，跟随fragment生命周期
     */
    protected open fun <T : ViewModel?> getFragmentViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!!.get(modelClass)
    }

    /**
     * fragment跳转
     */
    protected fun nav(): NavController {
        return NavHostFragment.findNavController(this)
    }

    /**
     * 初始化入口
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 获取layout布局
     */
    abstract fun getLayoutId(): Int?

    /**
     * 在Fragment基类中获取通用的控件，会将传入的View实例原封不动返回。
     * @param view
     * Fragment中inflate出来的View实例。
     * @return  Fragment中inflate出来的View实例原封不动返回。
     */
    private fun createLoadingView(): View {
        val lce = View.inflate(context, R.layout.layout_lce, null)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(
            0,
            dip2px(ctx, 70f),
            0,
            0
        )
        lce.layoutParams = params

        mLoading = lce.findViewById(R.id.loading)
        mNoContentView = lce.findViewById(R.id.noContentView)
        mBadNetworkView = lce.findViewById(R.id.badNetworkView)
        mLoadErrorView = lce.findViewById(R.id.loadErrorView)
        if (mLoading == null) {
            LogUtil.e(TAG, "loading is null")
        }
        if (mBadNetworkView == null) {
            LogUtil.e(TAG, "badNetworkView is null")
        }
        if (mLoadErrorView == null) {
            LogUtil.e(TAG, "loadErrorView is null")
        }
        mDefaultLce = DefaultLceImpl(
            mLoading,
            mLoadErrorView,
            mBadNetworkView,
            mNoContentView
        )
        return lce
    }

    override fun showLoading() {
        mDefaultLce?.showLoading()
    }

    override fun loadFinished() {
        mDefaultLce?.loadFinished()
    }

    override fun showLoadErrorView(tip: String) {
        mDefaultLce?.showLoadErrorView(tip)
    }

    override fun showBadNetworkView(listener: View.OnClickListener) {
        mDefaultLce?.showBadNetworkView(listener)
    }

    override fun showNoContentView(tip: String) {
        mDefaultLce?.showNoContentView(tip)
    }
}