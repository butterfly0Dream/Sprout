package com.jsx.applib.common

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * des 适配器中item子view点击⌚️
 * @date 2020/6/1
 * @author zs
 */
interface OnChildItemClickListener {
    fun onItemChildClick(adapter: BaseQuickAdapter<*,*>, view: View, position:Int )
}