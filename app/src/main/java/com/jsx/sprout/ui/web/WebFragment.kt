package com.jsx.sprout.ui.web

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.TAG
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.utils.Param
import com.jsx.sprout.R
import com.jsx.sprout.databinding.FragmentWebBinding

/**
 * Author: JackPan
 * Date: 2021-10-20
 * Time: 10:43
 * Description:
 */
class WebFragment : BaseVmFragment<FragmentWebBinding>() {

    /**
     * 通过注解接收参数
     * url
     */
    @Param(value = "loadUrl")
    private var loadUrl: String? = null

    /**
     * 文章标题
     */
    @Param(value = "title")
    private var title: String? = null

    /**
     * 文章id
     */
    @Param(value = "id")
    private var id: Int? = -1

    /**
     * 作者
     */
    @Param(value = "author")
    private var author: String? = null

    private lateinit var mState: WebVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //自定义返回
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        //返回上个页面
                        binding.webView.goBack()
                    } else {
                        //退出H5界面
                        nav().navigateUp()
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun initViewModel() {
        mState = getFragmentViewModel(WebVM::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mState
        initView()
    }

    override fun initView() {
//        arguments?.apply {
//            loadUrl = getString("loadUrl")
//            title = getString("title")
//            id = getInt("id")
//            author = getString("author")
//        }
        Log.d(TAG, "initView: $title $loadUrl")
        title?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvTitle.text = Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)
            } else {
                binding.tvTitle.text = Html.fromHtml(it)
            }
        }
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        loadUrl?.let {
            initWebView()
        }
    }

    override fun getLayoutId() = R.layout.fragment_web

    override fun getLceParentId() = R.id.webView

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        //自适应屏幕
        binding.webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        binding.webView.settings.loadWithOverviewMode = true

        //如果不设置WebViewClient，请求会跳转系统浏览器
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }
        }

        loadUrl?.let { binding.webView.loadUrl(it) }

        //设置最大进度
        mState.maxProgress.set(100)
        //webView加载成功回调
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                //进度小于100，显示进度条
                if (newProgress < 100) {
                    mState.isVisible.set(true)
                }
                //等于100隐藏
                else if (newProgress == 100) {
                    mState.isVisible.set(false)
                }
                //改变进度
                mState.progress.set(newProgress)
            }
        }
    }
}