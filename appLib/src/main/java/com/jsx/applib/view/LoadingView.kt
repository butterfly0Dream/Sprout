package com.jsx.applib.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.jsx.applib.R

/**
 * Author: JackPan
 * Date: 2021-10-25
 * Time: 16:32
 * Description:
 */
class LoadingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var maxRadius = 30        //最大半径
    private var minRadius = 5        //最小半径
    private var leftRadius = 5        //左边小球当前半径
    private var midRadius = 5        //中间小球当前半径
    private var rightRadius = 5        //右边小球当前半径
    private var duration = 1000        //小球由最小半径变为最大半径的时间
    private var color = Color.BLUE     //小球颜色
    private var internal = 100          //两球间距

    //三个属性动画分别控制左中右三个小球半径
    private val leftRadiusAnimator = ValueAnimator()
    private val midRadiusAnimator = ValueAnimator()
    private val rightRadiusAnimator = ValueAnimator()

    private lateinit var mPaint: Paint

    init {
        initParams(attrs)
    }

    //初始化各种属性
    private fun initParams(attrs: AttributeSet?) {
        val typeArray = this.context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        //获取自定义属性
        typeArray.apply {
            maxRadius = getDimensionPixelSize(R.styleable.LoadingView_max_radius, 30)
            if (maxRadius > 100) maxRadius = 100       //最大半径不超过100
            minRadius = getDimensionPixelSize(R.styleable.LoadingView_min_radius, 5)
            if (minRadius < 1) minRadius = 1         //最小半径不小于1
            duration = getInt(R.styleable.LoadingView_duration, 1000)
            color = getColor(R.styleable.LoadingView_ballColor, Color.BLUE)
            internal = getDimensionPixelSize(R.styleable.LoadingView_internal, internal)
        }
        typeArray.recycle()        //需要将TypedArray进行回收

        leftRadiusAnimator.run {
            setIntValues(minRadius, maxRadius)
            repeatCount = ValueAnimator.INFINITE    //动画无限循环
            repeatMode = ValueAnimator.REVERSE        //小球变到最大后再逐渐变为最小
            duration = 1000
            addUpdateListener {
                leftRadius = it.animatedValue as Int
                invalidate()
            }
        }

        midRadiusAnimator.run {
            setIntValues(minRadius, maxRadius)
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            duration = 1000
            startDelay = duration * 2 / 3    //延迟动画开始时间，这样就实现了三个小球不同步的变化
            addUpdateListener {
                midRadius = it.animatedValue as Int
                invalidate()
            }
        }

        rightRadiusAnimator.run {
            setIntValues(minRadius, maxRadius)
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            duration = 1000
            startDelay = duration * 4 / 3
            addUpdateListener {
                rightRadius = it.animatedValue as Int
                invalidate()
            }
        }

        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.color = color

        visibility = GONE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        //处理高度为wrap_content的情况，宽度如果为wrap_content时默认为屏幕宽度
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, maxRadius * 2)
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val pivotX = width / 2      //中心小球横坐标
        val pivotY = height / 2     //中心小球纵坐标

        canvas?.apply {
            //绘制三个小球
            drawCircle(pivotX.toFloat(), pivotY.toFloat(), leftRadius.toFloat(), mPaint)
            drawCircle((pivotX + internal).toFloat(), pivotY.toFloat(), midRadius.toFloat(), mPaint)
            drawCircle((pivotX - internal).toFloat(), pivotY.toFloat(), rightRadius.toFloat(), mPaint)
        }
    }

    fun show(){
        visibility = VISIBLE
        leftRadiusAnimator.start()
        midRadiusAnimator.start()
        rightRadiusAnimator.start()
    }

    fun hide(){
        visibility = GONE
        leftRadiusAnimator.end()
        midRadiusAnimator.end()
        rightRadiusAnimator.end()
    }

    //退出activity时停止动画
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        hide()
    }
}