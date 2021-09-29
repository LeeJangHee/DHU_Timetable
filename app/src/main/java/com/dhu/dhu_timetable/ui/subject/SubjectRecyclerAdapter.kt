package com.dhu.dhu_timetable.ui.subject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.FragmentSubjectItemBinding
import com.dhu.dhu_timetable.model.SubjectModel
import com.dhu.dhu_timetable.model.TimetableModel
import com.dhu.dhu_timetable.ui.main.MainActivityViewModel
import com.dhu.dhu_timetable.util.SubjectDiffUtils
import com.dhu.dhu_timetable.util.gone
import com.dhu.dhu_timetable.util.visible

class SubjectRecyclerAdapter(
        private val requireActivity: FragmentActivity,
        private val user: String,
        private val mainActivityViewModel: MainActivityViewModel
) : RecyclerView.Adapter<SubjectRecyclerAdapter.SubjectViewHolder>() {

    private var subjectModels: List<SubjectModel> = listOf()
    private var onTime = arrayListOf<TimetableModel>()
    private val isExpand = arrayListOf<SubjectModel>()

    inner class SubjectViewHolder(val binding: FragmentSubjectItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.subjectItemBtnOk.setOnClickListener {
                val subjectData = subjectModels[absoluteAdapterPosition]
                if (subjectData.workDay.isNullOrEmpty() && subjectData.cyberCheck == "") {
                    showToast(requireActivity.getString(R.string.subject_check_timetable))
                    return@setOnClickListener
                }
                if (mainActivityViewModel.isTimeCheck(subjectData.workDay)) {
                    // 시간표 넣기 가능
                    showToast(requireActivity.getString(R.string.subject_success_timetable))

                    // insert into timetable
                    mainActivityViewModel.onAddSubject(
                            user,
                            subjectModels[absoluteAdapterPosition].subjectName,
                            subjectModels[absoluteAdapterPosition].workDay,
                            subjectModels[absoluteAdapterPosition].cyberCheck,
                            subjectModels[absoluteAdapterPosition].quarterCheck
                    )
                } else {
                    // 불가능
                    showToast(requireActivity.getString(R.string.subject_overlap_timetable))
                }
//                applyOnTime(subjectModels[absoluteAdapterPosition])
            }

        }

        fun bind(subject: SubjectModel) {
            binding.apply {
                this.subject = subject
                clickListener = View.OnClickListener{
                    if (isExpand.contains(subject)) {
                        // 열려있음
                        isExpand.remove(subject)
                    } else {
                        // 닫혀있음
                        isExpand.add(subject)
                    }
                    notifyItemChanged(absoluteAdapterPosition)
//                    notifyItemChanged(absoluteAdapterPosition)
//                    expand = !expandableView.isVisible
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
        holder.bind(subjectModels[position])
        applyExpand(holder, isExpand.contains(subjectModels[position]))
    }

    override fun getItemId(position: Int): Long {
        return subjectModels[position].hashCode().toLong()
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

    fun setOnTime(timetableModels: List<TimetableModel>) {
        onTime = timetableModels as ArrayList
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun applyExpand(holder: SubjectViewHolder, isExpand: Boolean) {
        val transition = ChangeBounds().apply {
            interpolator = AccelerateInterpolator()
            duration = 200L
        }
        if (isExpand) {
            TransitionManager.beginDelayedTransition(holder.binding.cardView, transition)
            holder.binding.expandableView.visible()
            holder.binding.imageBtn.setImageResource(R.drawable.ic_baseline_expand_less_24)
        } else {
            holder.binding.expandableView.gone()
            holder.binding.imageBtn.setImageResource(R.drawable.ic_baseline_expand_more_24)
        }
    }

}