package com.jsx.sprout.common

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.jsx.applib.common.CenterBlurTransformation
import com.jsx.applib.view.GlideRoundTransform
import com.jsx.sprout.R

/**
 * Author: JackPan
 * Date: 2021-10-18
 * Time: 14:18
 * Description:
 */

/**
 * 通过url加载
 */
fun ImageView.loadUrl(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

/**
 * 通过uri加载
 */
fun ImageView.loadUri(context: Context, uri: Uri) {
    Glide.with(context)
        .load(uri)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

/**
 * 高斯模糊加渐入渐出
 */
fun ImageView.loadBlurTrans(context: Context, uri: Uri, radius: Int) {
    Glide.with(context)
        .load(uri)
        .thumbnail(0.1f).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .apply(RequestOptions.bitmapTransform(CenterBlurTransformation(radius, 8, context)))
        .transition(DrawableTransitionOptions.withCrossFade(400))
        .into(this)
}

/**
 * 圆形图片
 */
fun ImageView.loadCircle(context: Context, uri: Uri) {
    Glide.with(context)
        .load(uri)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .into(this)
}

/**
 * 圆形图片
 */
fun ImageView.loadRadius(context: Context, url: String, radius: Int = 20) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .error(R.mipmap.ic_launcher)
        .transition(DrawableTransitionOptions.withCrossFade())
        .transform(GlideRoundTransform(context,radius))
        .into(this)
}