package com.example.hospital_application
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class CurvedBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCurve()
    }

    private fun drawCurve() {
        path.reset()
        val height = height.toFloat()
        val width = width.toFloat()
        val mid = width / 2f
        val bottomCurveHeight = height * 0.8f // Adjust this value to control the curve's height

        path.moveTo(0f, 0f)
        path.lineTo(mid - 50, 0f)
        path.quadTo(mid, bottomCurveHeight, mid + 50, 0f)
        path.lineTo(width, 0f)
        path.lineTo(width, height)
        path.lineTo(0f, height)
        path.close()


    }
}
