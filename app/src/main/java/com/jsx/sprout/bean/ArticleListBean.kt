package com.jsx.sprout.bean

import android.os.Build
import android.text.Html
import android.text.TextUtils

/**
 * Author: JackPan
 * Date: 2021-10-08
 * Time: 18:05
 * Description:
 */
data class ArticleListBean(
    var id: Int = 0,

    /**
     * 作者
     */
    var author: String? = null,

    /**
     * 是否收藏
     */
    var collect: Boolean = false,

    /**
     * 描述信息
     */
    var desc: String? = null,

    /**
     * 图片类型，有和无
     */
    var picUrl: String? = null,

    /**
     * 链接
     */
    var link: String? = null,

    /**
     * 日期
     */
    var date: String? = null,

    /**
     * 标题
     */
    var title: String? = null,

    /**
     * 文章标签
     */
    var articleTag: String? = null,

    /**
     * 1.置顶
     */
    var topTitle: String? = null
) {

    companion object {
        fun trans(list: MutableList<ArticleBean.DatasBean>): MutableList<ArticleListBean> {
            return list.map {
                ArticleListBean().apply {
                    id = it.id
                    author = if (TextUtils.isEmpty(it.author)) {
                        it.shareUser
                    } else {
                        it.author
                    }
                    collect = it.collect
                    desc = it.desc
                    picUrl = it.envelopePic
                    link = it.link
                    date = it.niceDate
                    title = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(it.title, Html.FROM_HTML_MODE_LEGACY).toString()
                    } else {
                        Html.fromHtml(it.title).toString()
                    }
                    articleTag = it.superChapterName
                    topTitle = if (it.type == 1) "置顶" else ""
                }
            }.toMutableList()
        }

//        fun transByCollect(list: MutableList<CollectBean.DatasBean>): MutableList<ArticleListBean> {
//            return list.map {
//                ArticleListBean().apply {
//                    id = it.originId
//                    author = it.author
//                    collect = true
//                    desc = it.desc
//                    picUrl = it.envelopePic
//                    link = it.link
//                    date = it.niceDate
//                    title = Html.fromHtml(it.title).toString()
//                    articleTag = it.chapterName
//                    topTitle = ""
//                }
//            }.toMutableList()
//        }
    }
}
