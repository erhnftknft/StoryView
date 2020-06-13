package com.erhn.ftknft.storyview.storyview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.erhn.ftknft.storyview.RoundRectF

class StoryProgressView : View {
    //dpUtil
    val oneDp: Int


    //colors
    private var backColor: Int = Color.DKGRAY

    private var frontColor: Int = Color.WHITE

    //paint
    private var paint: Paint = Paint()

    //bounds
    private val backBounds: RoundRectF = RoundRectF()

    private val frontBounds: RoundRectF = RoundRectF()

    //animateValue
    private var currentAnimateValue = 0f

    //animator
    private val mainAnimator = ValueAnimator.ofFloat(START_ANIM_VALUE, END_ANIM_VALUE)

    private var animDuration: Long = 5000L

    constructor(context: Context?) : super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    init {
        oneDp = 1.dp
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mainAnimator.addUpdateListener {
            currentAnimateValue = it.animatedValue as Float
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val actualWidth = MeasureSpec.getSize(widthMeasureSpec)
        val actualHeight = MeasureSpec.getSize(heightMeasureSpec)
        calculateBounds(actualWidth, actualHeight)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = backColor
        canvas.drawRoundRect(backBounds, backBounds.rxRound, backBounds.ryRound, paint)
        paint.color = frontColor
        canvas.drawRoundRect(frontBounds, frontBounds.rxRound, frontBounds.ryRound, paint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mainAnimator.removeAllUpdateListeners()
    }

    private fun initialize() {
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true

        mainAnimator.setDuration(animDuration)
        mainAnimator.interpolator = LinearInterpolator()
    }

    private fun calculateBounds(actualWidth: Int, actualHeight: Int) {
        backBounds.apply {
            left = paddingStart.toFloat()
            right = actualWidth - paddingEnd.toFloat()
            top = paddingTop.toFloat()
            bottom = actualHeight - paddingBottom.toFloat()
            rxRound = this.height() / 2f
            ryRound = this.height() / 2f
        }

        frontBounds.apply {
            left = backBounds.left
            right = backBounds.right * currentAnimateValue
            top = paddingTop.toFloat()
            bottom = actualHeight - paddingBottom.toFloat()
            rxRound = backBounds.rxRound
            ryRound = backBounds.ryRound
        }

    }

    companion object {
        private const val START_ANIM_VALUE = 0f
        private const val END_ANIM_VALUE = 1f

        // private const val DEFAULT_HEIGHT =
    }

    private val Int.dp: Int
        get() = Math.round(context.resources.displayMetrics.density * this)
}