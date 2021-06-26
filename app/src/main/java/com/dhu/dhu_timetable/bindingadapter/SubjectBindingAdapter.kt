package com.dhu.dhu_timetable.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhu.dhu_timetable.model.SubjectModel


@BindingAdapter("isSubjectList")
fun bindIsSubjectList(view: View, subjects: List<SubjectModel>?) {
    if (subjects.isNullOrEmpty()) {
        when(view) {
            is RecyclerView -> {
                view.visibility = View.INVISIBLE
            }
            is ImageView, is TextView -> {
                view.visibility = View.VISIBLE
            }
        }
    } else {
        when(view) {
            is RecyclerView -> {
                view.visibility = View.VISIBLE
            }
            is ImageView, is TextView -> {
                view.visibility = View.INVISIBLE
            }
        }
    }
}

@BindingAdapter("isCyberCheck")
fun bindIsCyberCheck(view: TextView, cyberCheck: String?) {
    if (cyberCheck.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        if (cyberCheck == "Y")
            view.text = "사이버강의: YES"
        else
            view.visibility = View.GONE
    }
}