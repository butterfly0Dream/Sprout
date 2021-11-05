package com.jsx.sprout.bean

import android.os.Build
import android.text.Html
import android.text.TextUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.ui.collect.CollectBean

/**
 * Author: JackPan
 * Date: 2021-10-08
 * Time: 18:05
 * Description:
 */
@Entity(tableName = "browse_history")
data class ArticleListBean(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "id") var id: Int = 0,

    /**
     * 作者
     */
    @ColumnInfo(name = "author") var author: String? = null,

    /**
     * 是否收藏
     */
    @ColumnInfo(name = "collect") var collect: Boolean = false,

    /**
     * 描述信息
     */
    @ColumnInfo(name = "desc") var desc: String? = null,

    /**
     * 图片类型，有和无
     */
    @ColumnInfo(name = "picUrl") var picUrl: String? = null,

    /**
     * 链接
     */
    @ColumnInfo(name = "link") var link: String? = null,

    /**
     * 日期
     */
    @ColumnInfo(name = "date") var date: String? = null,

    /**
     * 标题
     */
    @ColumnInfo(name = "title") var title: String? = null,

    /**
     * 文章标签
     */
    @ColumnInfo(name = "articleTag") var articleTag: String? = null,

    /**
     * 1.置顶
     */
    @ColumnInfo(name = "topTitle") var topTitle: String? = null
) : MultiItemEntity {

    override val itemType: Int
        get() = if (picUrl.isNullOrEmpty()) {
            Constants.ITEM_ARTICLE
        } else {
            Constants.ITEM_ARTICLE_PIC
        }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    companion object {
        fun trans(list: MutableList<ArticleBean.DatasBean>): MutableList<ArticleListBean> {
            return list.map {
                ArticleListBean(0).apply {
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

        fun transByCollect(list: MutableList<CollectBean.DatasBean>): MutableList<ArticleListBean> {
            return list.map {
                ArticleListBean(0).apply {
                    id = it.originId
                    author = it.author
                    collect = true
                    desc = it.desc
                    picUrl = it.envelopePic
                    link = it.link
                    date = it.niceDate
                    title = Html.fromHtml(it.title).toString()
                    articleTag = it.chapterName
                    topTitle = ""
                }
            }.toMutableList()
        }
    }
}
