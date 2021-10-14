package com.jsx.applib.common

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

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