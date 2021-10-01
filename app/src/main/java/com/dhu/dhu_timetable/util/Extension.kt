package com.dhu.dhu_timetable.util

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dhu.dhu_timetable.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


fun View.gone() = apply {
    visibility = View.GONE
}

fun View.visible() = apply {
    visibility = View.VISIBLE
}

fun View.invisible() = apply {
    visibility = View.INVISIBLE
}

fun CircleImageView.setImageUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .error(R.drawable.app_icon)
        .circleCrop()
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

fun String?.getCurrentMonth() = this?.let { this } ?: with(Calendar.getInstance()) { this.get(Calendar.YEAR).toString() }

fun String?.getCurrentYear() = this?.let { this } ?: with(Calendar.getInstance()) { this.get(Calendar.MONTH + 1).toString() }