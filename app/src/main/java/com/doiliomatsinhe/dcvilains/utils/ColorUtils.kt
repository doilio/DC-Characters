package com.doiliomatsinhe.dcvilains.utils

import android.graphics.Color
import kotlin.math.roundToInt

object ColorUtils {
    /**
     * @param color is the given color.
     * @param factor number that makes the color darker.
     * @return's a darker color based the given color.
     */
    fun manipulateColor(color: Int, factor: Float): Int {
        val a: Int = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()
        return Color.argb(
            a, r.coerceAtMost(255),
            g.coerceAtMost(255), b.coerceAtMost(255)
        )
    }
}
