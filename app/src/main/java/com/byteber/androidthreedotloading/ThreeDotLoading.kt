package com.byteber.androidthreedotloading

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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (i in 0..2) {
            val dotWidth = width / 3
            val dotHeight = height
            val dotRadius = dotWidth.toFloat() / 2
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