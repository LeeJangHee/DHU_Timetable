package com.dhu.dhu_timetable.bindingadapter

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.dhu.dhu_timetable.util.invisible
import com.dhu.dhu_timetable.util.visible

@BindingAdapter("showProgressLogin")
fun bindShowProgressLogin(view: View, isLogin: Boolean) {
    if (isLogin) {
        view.visible()
    } else {
        view.invisible()
    }
}