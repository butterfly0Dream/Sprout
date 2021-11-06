package com.jsx.sprout.ui.myarticle

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.OnChildItemClickListener
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.smartDismiss
import com.jsx.applib.common.toast
import com.jsx.sprout.R
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.databinding.FragmentMyArticleBinding
import com.jsx.sprout.utils.CacheUtil

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 16:10
 * Description:
 */
class MyArticleFragment : BaseVmFragment<FragmentMyArticleBinding>(), OnChildItemClickListener {
    private val adapter by lazy { MyArticleAdapter() }
    private lateinit var myVM: MyArticleVM

    override fun initViewModel() {
        myVM = getFragmentViewModel(MyArticleVM::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    override fun initView() {
        adapter.apply {
            setOnChildItemClickListener(this@MyArticleFragment)
            binding.rvMyArticleList.adapter = this
        }
        binding.smartRefresh.setOnRefreshListener {
            myVM.getMyArticle()
        }
        binding.smartRefresh.setOnLoadMoreListener {
            myVM.loadMore()
        }
    }

    override fun onClick() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        binding.ivAdd.clickNoRepeat {
            if (CacheUtil.isLogin()) {
//                nav().navigate(R.id.action_my_article_fragment_to_publish_fragment)
            } else {
                toast("请先登录～")
            }
        }
    }

    override fun observe() {
        myVM.myLiveDate.observe(this, Observer {
            binding.smartRefresh.smartDismiss()
            adapter.setNewInstance(it)
            loadFinished()
        })
        myVM.deleteLiveData.observe(this, Observer {
            adapter.deleteById(it)
        })
        myVM.emptyLiveDate.observe(this, Observer {
            showNoContentView("无内容")
        })
        myVM.errorLiveData.observe(this, Observer {
            binding.smartRefresh.smartDismiss()
            showBadNetworkView { loadData()}
        })
    }

    override fun loadData() {
//        binding.smartRefresh.autoRefresh()
        myVM.getMyArticle()
        showLoading()
    }


    override fun getLayoutId() = R.layout.fragment_my_article

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        this.adapter.data.apply {
            when(view.id){
                R.id.rlContent->{
                    nav().navigate(R.id.action_my_article_fragment_to_web_fragment,Bundle().apply {
                        putString(Constants.WEB_URL,get(position).link)
                        putString(Constants.WEB_TITLE,get(position).title)
                    })
                }
                R.id.tvDelete->{
                    if (position<size){
                        myVM.delete(get(position).id)
                    }
                }
            }
        }
    }
}