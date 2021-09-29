package com.dhu.dhu_timetable.bindingadapter

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.model.SubjectModel
import com.dhu.dhu_timetable.util.gone
import com.dhu.dhu_timetable.util.invisible
import com.dhu.dhu_timetable.util.visible
import com.google.android.material.card.MaterialCardView


object SubjectBindingAdapter {

    @JvmStatic
    @BindingAdapter("isSubjectList")
    fun bindIsSubjectList(view: View, subjects: List<SubjectModel>?) {
        if (subjects.isNullOrEmpty()) {
            when (view) {
                is RecyclerView -> {
                    view.invisible()
                }
                is ImageView, is TextView -> {
                    view.visible()
                }
            }
        } else {
            when (view) {
                is RecyclerView -> {
                    view.visible()
                }
                is ImageView, is TextView -> {
                    view.invisible()
                }
            }
        }
    }

    @JvmStatic
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

    @JvmStatic
    @BindingAdapter("isOkButton")
    fun bindIsOkButton(view: Button, isTime: Boolean) {
        view.isEnabled = isTime
    }

    @JvmStatic
    @BindingAdapter("applyExpand")
    fun bindApplyExpand(view: View, isExpand: Boolean) {
        val transition = AutoTransition()
        if (isExpand) {
            when (view) {
                is MaterialCardView -> TransitionManager.beginDelayedTransition(view ,transition)
                is ConstraintLayout -> view.visible()
                is ImageButton -> view.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        } else {
            when (view) {
                is ConstraintLayout -> view.gone()
                is ImageButton -> view.setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
        }
    }
}