package com.jsx.sprout.ui.score

import com.jsx.applib.common.adapter.BaseDiffAdapter
import com.jsx.applib.common.adapter.DefaultDiff
import com.jsx.sprout.R
import com.jsx.sprout.databinding.ItemScoreBinding

/**
 * des 积分适配器
 * @author zs
 * @date 2020/9/10
 */
class IntegralAdapter :BaseDiffAdapter<ScoreListBean,ItemScoreBinding>(Diff()) {

    override val itemLayoutId: Int = R.layout.item_score
    override fun bindData(
        holder: BaseDiffViewHolder,
        position: Int,
        itemData: ScoreListBean,
        binding: ItemScoreBinding
    ) {
        binding.dataBean = itemData
    }

    class Diff : DefaultDiff<ScoreListBean>() {
        override fun areItemsTheSame(
            oldItem: ScoreListBean,
            newItem: ScoreListBean
        ): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(
            oldItem: ScoreListBean,
            newItem: ScoreListBean
        ): Boolean {
            return true
        }
    }
}