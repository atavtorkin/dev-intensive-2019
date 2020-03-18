package ru.skillbranch.devintensive.extensions

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dp: Int): Float {
    return dp.toFloat() * this.resources.displayMetrics.density
}

fun Context.pxToDp(px: Float): Float {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            px,
            this.resources.displayMetrics
    )
}

