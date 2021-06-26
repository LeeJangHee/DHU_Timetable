package com.dhu.dhu_timetable.ui.subject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.FragmentSubjectItemBinding
import com.dhu.dhu_timetable.model.SubjectModel
import com.dhu.dhu_timetable.util.SubjectDiffUtils

class SubjectRecycler(
        private val requireActivity: FragmentActivity,
        private val user: String,
        private val onSubjectListener: OnSubjectListener
): RecyclerView.Adapter<SubjectRecycler.SubjectViewHolder>() {

    private var subjectModels: List<SubjectModel> = listOf()
    private val isTime = BooleanArray(2000)

    inner class SubjectViewHolder(val binding: FragmentSubjectItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(subject: SubjectModel) {
            binding.subject = subject
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentSubjectItemBinding.inflate(layoutInflater, parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(subjectModels[position])
        val subjectModel: SubjectModel = subjectModels[position]

        holder.binding.imageBtn.setOnClickListener {
            val transition = AutoTransition()
            if (holder.binding.expandableView.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.binding.cardView, transition)
                holder.binding.imageBtn.setImageResource(R.drawable.ic_baseline_expand_less_24)
                holder.binding.expandableView.visibility = View.VISIBLE
                holder.binding.subjectName.isSelected = true
                isTime[position] = onSubjectListener.onTimeCheck(subjectModel.workDay)
                holder.binding.subjectItemBtnOk.isEnabled = isTime[position]
            } else {
                holder.binding.imageBtn.setImageResource(R.drawable.ic_baseline_expand_more_24)
                holder.binding.expandableView.visibility = View.GONE
                holder.binding.subjectName.isSelected = false
            }
        }

        holder.binding.subjectItemBtnOk.setOnClickListener {
            if (subjectModel.workDay.isNullOrEmpty() && subjectModel.cyberCheck == "") {
                showToast("사간을 확인 중 입니다.")
                return@setOnClickListener
            }
            if (isTime[position]) {
                // 시간표 넣기 가능
                    showToast("시간표 성공")

                // insert into timetable
                onSubjectListener.onAddSubject(
                        user,
                        subjectModel.subjectName,
                        subjectModel.workDay,
                        subjectModel.cyberCheck,
                        subjectModel.quarterCheck
                )
                holder.binding.subjectItemBtnOk.isEnabled = false
            } else {
                // 불가능
                    showToast("원하는 시간에 강의가 있습니다.")
            }
            isTime[position] = onSubjectListener.onTimeCheck(subjectModel.workDay)
        }
    }

    override fun getItemCount(): Int {
        return subjectModels.size
    }

    fun setSubjectList(subjectModels: List<SubjectModel>) {
        val subjectDiffUtils = SubjectDiffUtils(subjectModels, this.subjectModels)
        val diffUtilResult = DiffUtil.calculateDiff(subjectDiffUtils)
        this.subjectModels = subjectModels
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity, message, Toast.LENGTH_SHORT).show()
    }

}