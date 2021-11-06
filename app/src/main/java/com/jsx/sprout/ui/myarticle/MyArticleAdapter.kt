package com.jsx.sprout.ui.myarticle

import android.text.Html
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jsx.applib.common.OnChildItemClickListener
import com.jsx.applib.common.clickNoRepeat
import com.jsx.sprout.R
import com.jsx.sprout.bean.ArticleBean

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 15:46
 * Description:
 */
class MyArticleAdapter: BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder>(R.layout.item_my_article) {
    /**
     * 子view点击回调接口。单独写一个接口目的是可以使用防止快速点击
     */
    private var onItemClickListener: OnChildItemClickListener? = null


    fun setOnChildItemClickListener(onItemClickListener: OnChildItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun convert(holder: BaseViewHolder, item: ArticleBean.DatasBean) {
        item.run {
            holder.setText(R.id.tvAuthor, if (!TextUtils.isEmpty(author)) author else shareUser)
            holder.setText(R.id.tvDate, niceDate)
            holder.setText(R.id.tvTitle, Html.fromHtml(title))
            holder.setText(R.id.tvChapterName, superChapterName)
            holder.getView<View>(R.id.rlContent).clickNoRepeat {
                onItemClickListener?.onItemChildClick(this@MyArticleAdapter,it,holder.bindingAdapterPosition)
            }
            holder.getView<View>(R.id.tvDelete).clickNoRepeat {
                onItemClickListener?.onItemChildClick(this@MyArticleAdapter,it,holder.bindingAdapterPosition)
            }
        }
    }

    /**
     * 单个删除
     */
    fun deleteById(id: Int) {
        for (index in 0 until data.size){
            if (data[index].id == id){
                data.removeAt(index)
                notifyItemRemoved(index)
                return
            }
        }
    }
}