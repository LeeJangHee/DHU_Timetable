package com.dhu.dhu_timetable.ui.navitem.notice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.FragmentNoticeItemBinding
import com.dhu.dhu_timetable.model.NoticeModel
import com.dhu.dhu_timetable.util.SubjectDiffUtils
import com.dhu.dhu_timetable.util.gone
import com.dhu.dhu_timetable.util.visible

class NoticeAdapter: RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    private var noticeModels: List<NoticeModel> = listOf()
    private val isExpand = arrayListOf<NoticeModel>()

    inner class NoticeViewHolder(val binding: FragmentNoticeItemBinding): RecyclerView.ViewHolder(binding.root) {


        fun bind(noticeModel: NoticeModel) {
            binding.apply {
                data = noticeModel
                noticeClickListener = View.OnClickListener{
                    if (isExpand.contains(noticeModel)) {
                        // 열려있음
                        isExpand.remove(noticeModel)
                    } else {
                        // 닫혀있음
                        isExpand.add(noticeModel)
                    }
                    notifyItemChanged(absoluteAdapterPosition)
                }

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentNoticeItemBinding.inflate(layoutInflater, parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(noticeModels[position])
        applyExpand(holder, isExpand.contains(noticeModels[position]))
    }

    override fun getItemCount(): Int {
        return noticeModels.size
    }

    override fun getItemId(position: Int): Long {
        return noticeModels[position].hashCode().toLong()
    }

    fun setNoticeList(noticeModels: List<NoticeModel>) {
        val noticeDiffUtil = SubjectDiffUtils(noticeModels, this.noticeModels)
        val diffResult = DiffUtil.calculateDiff(noticeDiffUtil)
        this.noticeModels = noticeModels
        diffResult.dispatchUpdatesTo(this)
    }

    private fun applyExpand(holder: NoticeViewHolder, isExpand: Boolean) {
        val transition = ChangeBounds().apply {
            interpolator = AccelerateInterpolator()
            duration = 200L
        }
        if (isExpand) {
            TransitionManager.beginDelayedTransition(holder.binding.noticeCardView, transition)
            holder.binding.noticeExpandableView.visible()
            holder.binding.noticeExpbtn.setImageResource(R.drawable.ic_baseline_expand_less_24)
            holder.binding.noticeTitle.isSelected = true
        } else {
            holder.binding.noticeExpandableView.gone()
            holder.binding.noticeExpbtn.setImageResource(R.drawable.ic_baseline_expand_more_24)
            holder.binding.noticeTitle.isSelected = false
        }
    }
}