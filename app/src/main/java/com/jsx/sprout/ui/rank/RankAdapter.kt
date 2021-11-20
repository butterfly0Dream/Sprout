package com.jsx.sprout.ui.rank

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jsx.sprout.R

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 15:08
 * Description:
 */
class RankAdapter(): BaseQuickAdapter<RankBean.DatasBean, BaseViewHolder>(R.layout.item_rank) {
    override fun convert(holder: BaseViewHolder, item: RankBean.DatasBean) {
        item.apply {

            when(holder.bindingAdapterPosition){
                0->{
                    holder.setVisible(R.id.ivRank,true)
                    holder.setImageResource(R.id.ivRank,R.drawable.gold_crown)
                    holder.setVisible(R.id.tvRank,false)
                    //占位符
                    holder.setText(R.id.tvRank,"1")

                }
                1->{
                    holder.setVisible(R.id.ivRank,true)
                    holder.setImageResource(R.id.ivRank,R.drawable.silver_crown)
                    holder.setVisible(R.id.tvRank,false)
                    //占位符
                    holder.setText(R.id.tvRank,"1")
                }
                2->{
                    holder.setVisible(R.id.ivRank,true)
                    holder.setImageResource(R.id.ivRank,R.drawable.cooper_crown)
                    holder.setVisible(R.id.tvRank,false)
                    //占位符
                    holder.setText(R.id.tvRank,"1")
                }
                else->{
                    holder.setVisible(R.id.ivRank,false)
                    holder.setText(R.id.tvRank,"${holder.bindingAdapterPosition+1}")
                    holder.setVisible(R.id.tvRank,true)
                }
            }
            holder.setText(R.id.tvName,username)
            holder.setText(R.id.tvIntegral,"$coinCount")
        }
    }
}