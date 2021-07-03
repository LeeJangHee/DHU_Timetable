package com.dhu.dhu_timetable.bindingadapter

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("showProgressLogin")
fun bindShowProgressLogin(view: View, isLogin: Boolean) {
    if (isLogin) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}