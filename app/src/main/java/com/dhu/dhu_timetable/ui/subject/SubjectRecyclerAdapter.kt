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
import com.dhu.dhu_timetable.util.gone
import com.dhu.dhu_timetable.util.visible

class SubjectRecyclerAdapter(
        private val requireActivity: FragmentActivity,
        private val user: String,
        private val onSubjectListener: OnSubjectListener
) : RecyclerView.Adapter<SubjectRecyclerAdapter.SubjectViewHolder>() {

    private var subjectModels: List<SubjectModel> = listOf()
    private val isTime = BooleanArray(2000)
    private val isExpand = arrayListOf<Int>()

    inner class SubjectViewHolder(val binding: FragmentSubjectItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.subjectItemBtnOk.setOnClickListener {
                if (subjectModels[absoluteAdapterPosition].workDay.isNullOrEmpty() &&
                        subjectModels[absoluteAdapterPosition].cyberCheck == "") {
                    showToast(requireActivity.getString(R.string.subject_check_timetable))
                    return@setOnClickListener
                }
                if (isTime[absoluteAdapterPosition]) {
                    // 시간표 넣기 가능
                    showToast(requireActivity.getString(R.string.subject_success_timetable))

                    // insert into timetable
                    onSubjectListener.onAddSubject(
                            user,
                            subjectModels[absoluteAdapterPosition].subjectName,
                            subjectModels[absoluteAdapterPosition].workDay,
                            subjectModels[absoluteAdapterPosition].cyberCheck,
                            subjectModels[absoluteAdapterPosition].quarterCheck
                    )
                    binding.subjectItemBtnOk.isEnabled = false
                } else {
                    // 불가능
                    showToast(requireActivity.getString(R.string.subject_overlap_timetable))
                }
                isTime[absoluteAdapterPosition] = onSubjectListener.onTimeCheck(subjectModels[absoluteAdapterPosition].workDay)
            }

        }

        fun bind(subject: SubjectModel, isTimeItem: Boolean) {
            binding.apply {
                this.subject = subject
                this.isTimeItem = isTimeItem
                clickListener = View.OnClickListener{
                    if (isExpand.contains(absoluteAdapterPosition)) {
                        isExpand.remove(absoluteAdapterPosition)
                    } else {
                        isTime[absoluteAdapterPosition] = onSubjectListener.onTimeCheck(subjectModels[absoluteAdapterPosition].workDay)
                        isExpand.add(absoluteAdapterPosition)
                    }
                    notifyDataSetChanged()
                }
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentSubjectItemBinding.inflate(layoutInflater, parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(subjectModels[position], isTime[position])

        applyExpand(holder, isExpand.contains(position))

//        holder.binding.subjectItemBtnOk.isEnabled = isTime[position]
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

    private fun applyExpand(holder: SubjectViewHolder, isExpand: Boolean) {
        val transition = AutoTransition()
        if (isExpand) {
            holder.binding.expandableView.visible()
            holder.binding.imageBtn.setImageResource(R.drawable.ic_baseline_expand_less_24)
        } else {
            TransitionManager.beginDelayedTransition(holder.binding.cardView, transition)
            holder.binding.expandableView.gone()
            holder.binding.imageBtn.setImageResource(R.drawable.ic_baseline_expand_more_24)
        }
    }

}