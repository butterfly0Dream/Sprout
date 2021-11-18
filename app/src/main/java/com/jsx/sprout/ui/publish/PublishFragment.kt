package com.jsx.sprout.ui.publish

import android.os.Bundle
import com.jsx.applib.base.BaseVmFragment
import com.jsx.applib.common.clickNoRepeat
import com.jsx.applib.common.toast
import com.jsx.applib.utils.KeyBoardUtil
import com.jsx.sprout.R
import com.jsx.sprout.databinding.FragmentPublishBinding
import com.jsx.sprout.view.DialogUtils

class PublishFragment : BaseVmFragment<FragmentPublishBinding>() {

    private lateinit var publishVM: PublishVM

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = publishVM
    }

    override fun initViewModel() {
        publishVM = getFragmentViewModel(PublishVM::class.java)
    }

    override fun observe() {
        publishVM.publishLiveData.observe(this, {
            DialogUtils.dismiss()
            //结束当前界面
            nav().navigateUp()
            toast("发布成功")
        })
        publishVM.errorLiveData.observe(this, {
            DialogUtils.dismiss()
        })
    }

    override fun onClick() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        binding.btPublish.clickNoRepeat {
            if (publishVM.articleTitle.get()!!.isEmpty()){
                toast(getString(R.string.common_check_title))
                return@clickNoRepeat
            }
            if (publishVM.articleLink.get()!!.isEmpty()){
                toast(getString(R.string.common_check_link))
                return@clickNoRepeat
            }
            //关闭软键盘
            KeyBoardUtil.closeKeyboard(binding.etTitle, ctx)
            KeyBoardUtil.closeKeyboard(binding.etLink, ctx)
            DialogUtils.showLoading(activity, "正在发布～")
            publishVM.publish()
        }
    }

    override fun getLayoutId() = R.layout.fragment_publish
}