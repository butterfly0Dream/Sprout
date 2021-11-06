package com.jsx.sprout.ui.myarticle

import androidx.lifecycle.MutableLiveData
import com.jsx.applib.base.BaseViewModel
import com.jsx.sprout.bean.ArticleBean

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 16:03
 * Description:
 */
class MyArticleVM : BaseViewModel() {
    private val repo by lazy { MyArticleRepo() }

    val myLiveDate = MutableLiveData<MutableList<ArticleBean.DatasBean>>()

    val deleteLiveData = MutableLiveData<Int>()

    fun getMyArticle(){
        launch {
            val datas = repo.getMyArticle()
            if (datas == null || datas.isEmpty()){
                handleEmpty()
            }else{
                myLiveDate.value = datas.toMutableList()
            }
        }
    }

    fun loadMore(){
        launch {
            val list = myLiveDate.value
            repo.loadMore()?.toMutableList()?.let {
                list?.addAll(it)
            }
            myLiveDate.value = list
            handleList(myLiveDate)
        }
    }

    fun delete(id:Int){
        launch {
            repo.delete(id)
            deleteLiveData.value = id
        }
    }
}