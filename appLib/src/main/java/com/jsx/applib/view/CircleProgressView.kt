package com.jsx.applib.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ClipDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.jsx.applib.R
import com.jsx.applib.common.TAG
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Author: JackPan
 * Date: 2021-10-26
 * Time: 13:56
 * Description: 流量进度球view
 * 移植需要修改：当前类、attr中styleable、vector_circle做的clipDrawable
 */
class CircleProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mHaloRadius = 230 // 光晕半径
    private var mHaloColor = Color.parseColor("#44ffffff")     //光晕颜色
    private var mRadius = 200  // 主圆半径
    private var mColor = Color.parseColor("#64B5F6")  // 主圆颜色
    private var mStrokeColor = Color.WHITE // 圆周颜色
    private var mStrokeWidth = 3 // 圆周线宽度
    private var mLineWidth = 3 // 分隔线宽度
    private var mLineColor = Color.WHITE     //分隔线宽度颜色
    private var mTextWidth = 50f
    private var mText1 = "已使用流量"
    private var mText1Color = Color.WHITE
    private var mText1Size = 45f
    private var mText2Color = Color.WHITE
    private var mText2Size = 60f
    private var mText2 = "99999.99MB"
    private var mText3Color = Color.WHITE
    private var mText3Size = 45f
    private var mText3 = "2500MB"

    private var mTextRect = Rect()
    private var mTextRectF = RectF()
    private lateinit var mPaint: Paint

    private var mProgress = 0.5f    // 进度，即进度球填充的比例
    private var mLineHeight = 0.8f  // 分隔线位置，0~2,0在圆底部，2在圆顶部

    init {
        initParams(attrs)
    }

    private fun initParams(attrs: AttributeSet?) {
        val typeArray = this.context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView)
        //获取自定义属性
        typeArray.apply {
            mRadius = getDimensionPixelSize(R.styleable.CircleProgressView_radius, 200)
            mColor = getColor(R.styleable.CircleProgressView_color, Color.parseColor("#64B5F6"))
            mText1Size =
                getDimensionPixelSize(R.styleable.CircleProgressView_text_size, 50).toFloat()
            mText1Color = getColor(R.styleable.CircleProgressView_text_color, Color.WHITE)
            mHaloRadius = mRadius + mRadius / 6
            mText2Color = mText1Color
            mText2Size = mText1Size * 4 / 3
            mText3Color = mText1Color
            mText3Size = mText1Size
        }
        typeArray.recycle()

        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
    }

    fun setProgress(p: Float) {
        Log.d(TAG, "setProgress: $p")
        mProgress = p
        invalidate()
    }

    /**
     * @param text1 第一行文字
     * @param text2 第二行文字
     * @param text3  第三行文字
     * @param progress 填充的比例
     */
    fun setTexts(text1: String, text2: String, text3: String, progress: Float) {
        if (text1.isNotEmpty()) {
            mText1 = text1
        }
        if (text2.isNotEmpty()) {
            mText2 = text2
        }
        if (text3.isNotEmpty()) {
            mText3 = text3
        }
        mProgress = progress
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        // TODO: 2021/10/27 当在xml中限定了view的宽高时，默认参数需要调整，否则可能显示不全 
        Log.d(
            TAG,
            "onMeasure: ${widthSpecMode},${widthSpecSize}  ${heightSpecMode},${heightSpecSize}"
        )

        //处理高度为wrap_content的情况，宽度如果为wrap_content时默认为屏幕宽度
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            widthSpecSize = mHaloRadius * 2 + 20
        }
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            heightSpecSize = mHaloRadius * 2 + 20
        }
        setMeasuredDimension(widthSpecSize, heightSpecSize)
    }

    override fun onDraw(canvas: Canvas?) {
        val pivotX = width / 2      //中心圆横坐标
        val pivotY = height / 2     //中心圆纵坐标

        canvas?.apply {
            // 绘制光晕
            mPaint.color = mHaloColor
            drawCircle(pivotX.toFloat(), pivotY.toFloat(), mHaloRadius.toFloat(), mPaint)
            // 绘制圆周
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = mStrokeWidth.toFloat()
            mPaint.color = mStrokeColor
            drawCircle(pivotX.toFloat(), pivotY.toFloat(), mRadius.toFloat() + mStrokeWidth, mPaint)
            // 绘制中心圆
            mPaint.style = Paint.Style.FILL
            mPaint.color = mColor
            drawCircle(pivotX.toFloat(), pivotY.toFloat(), mRadius.toFloat(), mPaint)

            val dst = getBitmap()//获取bitmap
            setLayerType(LAYER_TYPE_HARDWARE, null) //开启硬件离屏缓存
            drawBitmap(dst, (pivotX - mRadius).toFloat(), (pivotY - mRadius).toFloat(), mPaint)

            // 绘制分隔线
            val xArray = getX(mLineHeight)
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = mLineWidth.toFloat()
            mPaint.color = mLineColor
            drawLine(xArray[0], xArray[2], xArray[1], xArray[2], mPaint)

            // 绘制文字
            mPaint.style = Paint.Style.FILL
            mPaint.textAlign = Paint.Align.CENTER
            mPaint.strokeWidth = mTextWidth
            mPaint.textSize = mText1Size
            mPaint.color = mText1Color
            drawText(
                mText1,
                pivotX.toFloat(),
                pivotY.toFloat() - mHaloRadius * 0.45f,
                mPaint
            )
            mPaint.textSize = mText2Size
            mPaint.color = mText2Color
            drawText(
                mText2,
                pivotX.toFloat(),
                pivotY.toFloat() - mHaloRadius * 0.45f + mText1Size * 2.0f,
                mPaint
            )
            mPaint.textSize = mText3Size
            mPaint.color = mText3Color
            val baseLine3 = pivotY.toFloat() + mHaloRadius * 0.55f
            drawText(
                mText3,
                pivotX.toFloat(),
                baseLine3,
                mPaint
            )

            // 绘制text3的圆角矩形框
            // 取得字体所占宽高
            mPaint.getTextBounds(mText3, 0, mText3.length, mTextRect)
            // 基准线到bottom的距离，正数
            val distanceB = mPaint.fontMetrics.bottom
            // 基准线到top的距离，负数
            val distanceT = mPaint.fontMetrics.top
            mTextRectF.set(
                pivotX.toFloat() - mTextRect.width() / 2 - 10,
                baseLine3 + distanceT,
                pivotX.toFloat() + mTextRect.width() / 2 + 10,
                baseLine3 + distanceB
            )
//            Log.d(
//                TAG,
//                "onDraw: ${mTextRectF.left},${mTextRectF.top},${mTextRectF.right},${mTextRectF.bottom}"
//            )
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = mLineWidth.toFloat()
            mPaint.color = mLineColor
            //绘制圆角矩形
            drawRoundRect(mTextRectF, mTextRect.height() / 2f, mTextRect.height() / 2f, mPaint)
        }
        super.onDraw(canvas)
    }

    private fun getBitmap(): Bitmap {
        val dstBitmap: Bitmap =
            Bitmap.createBitmap(mRadius * 2, mRadius * 2, Bitmap.Config.ARGB_8888)
        val clipDrawable = ClipDrawable(
            ResourcesCompat.getDrawable(resources, R.drawable.vector_circle, null),
            Gravity.BOTTOM,
            ClipDrawable.VERTICAL
        )
        //获取球形的背景图片，用于裁剪，就是界面进度球中的图片
        clipDrawable.bounds = Rect(0, 0, mRadius * 2, mRadius * 2)//设置边界
        clipDrawable.level = (10000 * mProgress).toInt()//设置进度，
        val canvas = Canvas(dstBitmap)//设置画布
        clipDrawable.draw(canvas)//绘制
        return dstBitmap//将bitmap返回
    }

    private fun getX(radio: Float): FloatArray {
        val array = FloatArray(3)
        val a = width / 2
        val b = height / 2
        val y = mRadius + b - radio * mRadius
        val xSQ = sqrt(mRadius.toDouble().pow(2) - (y - b).pow(2))
        val x1 = a - xSQ
        val x2 = a + xSQ
//        Log.d(TAG, "getXY: $a,$b,$y,$xSQ")
//        Log.d(TAG, "getXY: $x1,$x2,${y - radio * mRadius},${2 * mRadius + b}")
        array[0] = x1.toFloat()
        array[1] = x2.toFloat()
        array[2] = y
        return array
    }
}