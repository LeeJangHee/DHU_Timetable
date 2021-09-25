package com.dhu.dhu_timetable.bindingadapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhu.dhu_timetable.model.SubjectModel
import com.dhu.dhu_timetable.util.gone
import com.dhu.dhu_timetable.util.invisible
import com.dhu.dhu_timetable.util.visible


@BindingAdapter("isSubjectList")
fun bindIsSubjectList(view: View, subjects: List<SubjectModel>?) {
    if (subjects.isNullOrEmpty()) {
        when(view) {
            is RecyclerView -> {
                view.invisible()
            }
            is ImageView, is TextView -> {
                view.visible()
            }
        }
    } else {
        when(view) {
            is RecyclerView -> {
                view.visible()
            }
            is ImageView, is TextView -> {
                view.invisible()
            }
        }
    }
}

@BindingAdapter("isCyberCheck")
fun bindIsCyberCheck(view: TextView, cyberCheck: String?) {
    if (cyberCheck.isNullOrEmpty()) {
        view.gone()
    } else {
        view.visible()
        if (cyberCheck == "Y")
            view.text = "사이버강의: YES"
        else
            view.gone()
    }
}

@BindingAdapter("isOkButton")
fun bindIsOkButton(view: Button, isTime: Boolean) {
    view.isEnabled = isTime
}