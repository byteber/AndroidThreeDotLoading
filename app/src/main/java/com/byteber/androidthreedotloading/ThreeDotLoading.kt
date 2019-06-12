package com.byteber.androidthreedotloading

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ThreeDotLoading @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val dotPaint = Paint().apply {
        color = Color.RED
    }
    private val dotScales = floatArrayOf(0f, 0f, 0f)
    private var animator: TimeAnimator? = null

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (isInEditMode) {
            dotScales[0] = 0.3f
            dotScales[1] = 0.6f
            dotScales[2] = 0.9f
            return
        }
        if (visibility == View.VISIBLE) {
            if (animator == null) {
                createAnimator()
            }
            animator?.start()
        } else {
            animator?.cancel()
        }
    }

    /**
     * return scale value as flow 0 -> 1 -> 0 -> 0
     */
    private fun createAnimator() {
        animator = TimeAnimator().apply {
            setTimeListener { timeAnimator, totalTime, deltaTime ->
                dotScales[0] = getNextScale(totalTime)
                dotScales[1] = getNextScale(totalTime - 170)
                dotScales[2] = getNextScale(totalTime - 340)
                invalidate()
            }
        }
    }

    private fun getNextScale(totalTime: Long): Float {
        val periodTime = totalTime % 1500
        if (periodTime >= 1000) {
            return 0f
        }
        if (periodTime <= 500) {
            return periodTime.toFloat() / 500
        }
        return (1000 - periodTime).toFloat() / 500
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (i in 0..2) {
            val dotWidth = width / 3
            val dotHeight = height
            val dotRadius = dotWidth.toFloat() / 2 * dotScales[i]
            val dotCenterX = dotWidth * i + dotWidth.toFloat() / 2
            val dotCenterY = dotHeight.toFloat() / 2

            canvas?.drawCircle(dotCenterX, dotCenterY, dotRadius, dotPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(heightSize * 3, heightSize)
    }
}