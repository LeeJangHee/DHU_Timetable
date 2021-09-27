package com.dhu.dhu_timetable.ui.subject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dhu.dhu_timetable.databinding.FragmentSubjectListBinding
import com.dhu.dhu_timetable.ui.main.MainActivityViewModel
import com.dhu.dhu_timetable.util.Conts

class SubjectFragment : Fragment() {

    private var _binding: FragmentSubjectListBinding? = null
    private val binding get() = _binding!!

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private val user: String by lazy { this.requireArguments().getString(Conts.USER.uppercase()) ?: "" }

    private val subjectRecyclerAdapter: SubjectRecyclerAdapter by lazy {
        SubjectRecyclerAdapter(requireActivity(), user, mainActivityViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSubjectListBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = requireActivity()
            this.mainViewModel = mainActivityViewModel
        }
        setRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivityViewModel.subjectData.observe(requireActivity()) {
            subjectRecyclerAdapter.setSubjectList(it)
        }

    }

    private fun setRecyclerView() {
        binding.subjectRecyclerview.adapter = subjectRecyclerAdapter
    }

    companion object {

        @JvmStatic
        fun newInstance(year: String, month: String, user: String) = with(SubjectFragment()) {
            arguments = Bundle().apply {
                putString(Conts.YEAR.uppercase(), year)
                putString(Conts.MONTH.uppercase(), month)
                putString(Conts.USER.uppercase(), user)
            }
            this
        }

        @JvmStatic
        fun searchInstance(year: String?, semester: String?, subjectName: String?, level: String?, major: String?, cyber: String?) =
            with(SubjectFragment()) {
                arguments = Bundle().apply {
                    putString(Conts.YEAR, year)
                    putString(Conts.SEMESTER, semester)
                    putString(Conts.SUBJECT_NAME, subjectName)
                    putString(Conts.LEVEL, level)
                    putString(Conts.MAJOR, major)
                    putString(Conts.CYBER, cyber)
                }
                this
            }
    }


}