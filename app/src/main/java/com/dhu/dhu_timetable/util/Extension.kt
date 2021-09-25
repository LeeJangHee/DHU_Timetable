package com.dhu.dhu_timetable.util

import android.view.View


fun View.gone() = apply {
    visibility = View.GONE
}

fun View.visible() = apply {
    visibility = View.VISIBLE
}

fun View.invisible() = apply {
    visibility = View.INVISIBLE
}