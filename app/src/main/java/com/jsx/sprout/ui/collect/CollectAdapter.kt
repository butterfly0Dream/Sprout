package com.jsx.sprout.ui.collect

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jsx.applib.common.OnChildItemClickListener
import com.jsx.applib.common.clickNoRepeat
import com.jsx.sprout.R

/**
 * Author: JackPan
 * Date: 2021-10-21
 * Time: 10:30
 * Description:
 */
class CollectAdapter : BaseQuickAdapter<CollectBean.DatasBean, BaseViewHolder>(R.layout.item_home_article) {

    private var collectClickListener: OnChildItemClickListener? = null

    fun setOnChildItemClickListener(collectClickListener: OnChildItemClickListener){
        this.collectClickListener = collectClickListener
    }


    override fun convert(holder: BaseViewHolder, item: CollectBean.DatasBean) {
        item.run {
            holder.setText(R.id.tv_tag,"")
            holder.setText(R.id.tv_author,author)
            holder.setText(R.id.tv_date,niceDate)
            holder.setText(R.id.tv_title,title)
            holder.setText(R.id.tv_chapter,chapterName)
            holder.getView<ImageView>(R.id.iv_collect).apply {
                setImageResource(R.drawable.collect)
                clickNoRepeat {
                    collectClickListener?.onItemChildClick(this@CollectAdapter,it,holder.bindingAdapterPosition)
                }
            }
            holder.getView<View>(R.id.root).apply {
                clickNoRepeat {
                    collectClickListener?.onItemChildClick(this@CollectAdapter,it,holder.bindingAdapterPosition)
                }
            }
        }
    }

    /**
     * 单个删除
     */
    fun deleteById(id: Int) {
        for (index in 0 until data.size){
            if (data[index].originId == id){
                data.removeAt(index)
                notifyItemRemoved(index)
                return
            }
        }
    }

    /**
     * 获取跳转至web界面的bundle
     */
    fun getBundle(position: Int): Bundle {
        return Bundle().apply {
            putString("loadUrl", data[position].link)
            putString("title", data[position].title)
            putString("author", data[position].author)
            putInt("id", data[position].originId)
        }
    }
}