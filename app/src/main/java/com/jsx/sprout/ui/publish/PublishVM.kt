package com.jsx.sprout.ui.publish

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel
import com.jsx.applib.common.toast

class PublishVM : BaseViewModel() {
    /**
     * 文章标题
     */
    val articleTitle = ObservableField<String>().apply { set("") }

    /**
     * 文章链接
     */
    val articleLink = ObservableField<String>().apply { set("") }

    /**
     * 发布文章
     */
    val publishLiveData = MutableLiveData<Any>()

    private val repo by lazy { PublishRepo() }

    fun publish(){
        if (TextUtils.isEmpty(articleTitle.get())|| TextUtils.isEmpty(articleLink.get())){
            toast("请输入标题跟链接～")
        }else{
            launch {
                publishLiveData.value = repo.publish(articleTitle.get()!!,articleLink.get()!!)
            }
        }
    }
}