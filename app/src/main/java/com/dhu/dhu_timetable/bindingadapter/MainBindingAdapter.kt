package com.dhu.dhu_timetable.bindingadapter

import androidx.databinding.BindingAdapter
import com.dhu.dhu_timetable.util.setImageUrl
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("setCircleImageViewUrl")
fun bindSetCircleImageViewUrl(view: CircleImageView, url: String?) {
    view.setImageUrl(url)
}
