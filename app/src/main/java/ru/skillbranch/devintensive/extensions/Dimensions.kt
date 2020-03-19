package ru.skillbranch.devintensive.extensions

import android.content.Context

/**
 * Created on 19.03.2020.
 *
 * @author Alexander Tavtorkin (av.tavtorkin@gmail.com)
 */

fun convertDpToPx(context: Context, dp: Float): Float {
    return dp * context.resources.displayMetrics.density
}

fun convertPxToDp(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}