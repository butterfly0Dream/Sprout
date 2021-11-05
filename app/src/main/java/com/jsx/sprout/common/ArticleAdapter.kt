package com.jsx.sprout.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jsx.applib.common.clickNoRepeat
import com.jsx.sprout.R
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.constants.Constants
import com.jsx.sprout.databinding.ItemHomeArticleBinding
import com.jsx.sprout.databinding.ItemProjectBinding
import com.jsx.sprout.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Author: JackPan
 * Date: 2021-10-18
 * Time: 11:46
 * Description:
 */
class ArticleAdapter(private val context: Context) : ListAdapter<ArticleListBean, RecyclerView.ViewHolder>(getArticleDiff()) {

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * item点击事件
     * @param Int 角标
     * @param View 点击的View
     */
    private var onItemClickListener: ((Int, View) -> Unit)? = null


    /**
     * item中子View点击事件，需要子类做具体触发
     * @param Int 角标
     * @param View 点击的View
     */
    private var onItemChildClickListener: ((Int, View) -> Unit)? = null

    /**
     * 注册item点击事件
     */
    fun setOnItemClickListener(onItemClickListener: ((Int, View) -> Unit)? = null) {
        this.onItemClickListener = onItemClickListener
    }

    /**
     * 注册item子View点击事件
     */
    fun setOnItemChildClickListener(onItemChildClickListener: ((Int, View) -> Unit)? = null) {
        this.onItemChildClickListener = onItemChildClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.ITEM_ARTICLE) {
            val binding: ItemHomeArticleBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_home_article,
                parent,
                false
            )
            ArticleViewHolder(binding.root)
        } else {
            val binding: ItemProjectBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_project, parent,
                false
            )
            ArticlePicViewHolder(binding.root)
        }
    }

    /**
     * 将数据和ui进行绑定
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.clickNoRepeat {
            onItemClickListener?.invoke(position, it)
            // 插入一条浏览历史数据
            ioScope.launch {
                val browseHistoryDao = AppDatabase.getInstance().browseHistoryDao()
                val data = getItem(position)
                if (browseHistoryDao.getArticle(data.id) == null) {
                    browseHistoryDao.insert(data)
                }
            }
        }
        //收藏
        holder.itemView.findViewById<View>(R.id.iv_collect)?.clickNoRepeat {
            onItemChildClickListener?.invoke(position,it)
        }
        val binding = if (holder is ArticleViewHolder) {
            DataBindingUtil.getBinding<ItemHomeArticleBinding>(holder.itemView)?.apply {
                dataBean = getItem(position)
            }
        } else {
            DataBindingUtil.getBinding<ItemProjectBinding>(holder.itemView)?.apply {
                dataBean = getItem(position)
            }
        }
        binding?.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].itemType == Constants.ITEM_ARTICLE){
            //普通类型
            Constants.ITEM_ARTICLE
        }else{
            //带图片类型
            Constants.ITEM_ARTICLE_PIC
        }
    }

    /**
     * 默认viewHolder
     */
    class ArticleViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    }

    /**
     * 带图片viewHolder
     */
    class ArticlePicViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    }

    /**
     * 重新加载数据时必须换一个list集合，否则diff不生效
     */
    override fun submitList(list: List<ArticleListBean>?) {
        super.submitList(if (list == null) mutableListOf() else
            mutableListOf<ArticleListBean>().apply {
                addAll(
                    list
                )
            })
    }

    /**
     * 获取跳转至web界面的bundle
     */
    fun getBundle(position: Int): Bundle {
        return Bundle().apply {
            putString("loadUrl", currentList[position].link)
            putString("title", currentList[position].title)
            putString("author", currentList[position].author)
            putInt("id", currentList[position].id)
        }
    }
}