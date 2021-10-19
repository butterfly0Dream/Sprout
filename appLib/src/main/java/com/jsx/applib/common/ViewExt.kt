package com.jsx.applib.common

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * Author: JackPan
 * Date: 2021-10-12
 * Time: 13:58
 * Description: 视图拓展方法
 */

/**
 * viewPager2适配fragment
 */
fun ViewPager2.initFragment(
    fragment: Fragment,
    fragments: MutableList<Fragment>
): ViewPager2 {
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

/**
 * ViewPager2选中
 */
fun ViewPager2.doSelected(selected: (Int) -> Unit) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            selected.invoke(position)
        }
    })
}

/**
 * 防止重复点击,可同时注册多个view
 */
fun setNoRepeatClick(vararg views: View, interval: Long = 400, onClick: (View) -> Unit) {
    views.forEach {
        it.clickNoRepeat(interval = interval) { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 防止重复点击
 * @param interval 重复间隔
 * @param onClick  事件响应
 */
// 所有的view是共享这个值的，所以连续点击不同view，后面的也不会响应
var lastTime = 0L
fun View.clickNoRepeat(interval: Long = 400, onClick: (View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastTime != 0L && (currentTime - lastTime < interval)) {
            return@setOnClickListener
        }
        lastTime = currentTime
        onClick(it)
    }
}

/**
 * 隐藏刷新加载ui
 */
fun SmartRefreshLayout.smartDismiss() {
    finishRefresh(0)
    finishLoadMore(0)
}

/**
 * 配置SmartRefreshLayout
 */
fun SmartRefreshLayout.smartConfig() {
    //加载
    setEnableLoadMore(true)
    //刷新
    setEnableRefresh(true)
    //不满一页关闭加载
    //setEnableLoadMoreWhenContentNotFull(false)
    //滚动回弹
    setEnableOverScrollDrag(true)
}

/**
 * 获取当前主图颜色属性
 */
fun Context.getThemeColor(attr: Int): Int {
    val array: TypedArray = theme.obtainStyledAttributes(
        intArrayOf(
            attr
        )
    )
    val color = array.getColor(0, -0x50506)
    array.recycle()
    return color
}
