package com.jsx.sprout.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.jsx.sprout.R

/**
 * Author: JackPan
 * Date: 2021-10-18
 * Time: 14:41
 * Description:
 */
object ArticleBindAdapter {

    /**
     * 加载图片,做高斯模糊处理
     */
    @BindingAdapter(value = ["articleCollect"])
    @JvmStatic
    fun imgPlayBlur(view: ImageView, collect: Boolean) {
        if (collect) {
            view.setImageResource(R.drawable.collect)
        } else {
            view.setImageResource(R.drawable.un_collect)
        }
    }
}