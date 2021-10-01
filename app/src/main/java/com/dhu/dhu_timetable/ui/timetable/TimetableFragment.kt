package com.dhu.dhu_timetable.ui.timetable

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.FragmentTimetableBinding
import com.dhu.dhu_timetable.model.TimetableModel
import com.dhu.dhu_timetable.ui.main.MainActivityViewModel
import com.dhu.dhu_timetable.util.Conts.USER
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time
import com.github.tlaabs.timetableview.TimetableView
import java.util.*

/**
 * 시간표 오픈소스
 */
class TimetableFragment : Fragment() {
    private val timetable: TimetableView by lazy { binding.timetable }
    private val schedules = ArrayList<Schedule>()
    private val scheduleID = ArrayList<String>()
    private val mGetID = HashMap<Schedule, String>()
    private var user: String? = null
    var defaultHour = intArrayOf(-1, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)

    // [75분용시간표현] A교시:09:00~10:15, B교시:10:30~11:45, C교시:14:00~15:15  D교시: 15:30~16:45
    // (index * 2 - 1, index * 2)
    var quarterMinute = intArrayOf(-1, 0, 15, 30, 45, -1, -1, 0, 15, 30, 45)
    private var schedule: Schedule? = null
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance(user: String?): TimetableFragment = with(TimetableFragment()) {
            arguments = Bundle().apply {
                putString(USER, user)
            }
            this
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getString(USER)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimetableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 레트로핏 --> 라이브데이터 가져오기
        mainActivityViewModel.getTimetable()?.observe(viewLifecycleOwner,
            { timetableModels -> // 새로 초기화 후
                timetable.removeAll()
                schedules.clear()
                scheduleID.clear()
                mGetID.clear()
                setTimetable(timetableModels)

                // 업데이트
                if (schedules.isNotEmpty()) {
                    schedules.forEachIndexed { i, _ ->
                        val aSchedule = ArrayList<Schedule>()
                        aSchedule.add(schedules[i])
                        timetable.add(aSchedule)
                        mGetID[schedules[i]] = scheduleID[i]
                    }
                }
            })
        timetable.setOnStickerSelectEventListener { idx, schedules -> // 삭제서버 연결
            AlertDialog.Builder(context)
                .setMessage(getString(R.string.timetable_dialog_title))
                .setPositiveButton(
                    "확인"
                ) { _, _ ->
                    mGetID[schedules[0]]?.let {
                        mainActivityViewModel.deleteTimetable(user!!, it.toInt())
                    } ?: return@setPositiveButton
                }
                .setNegativeButton("취소", null)
                .create()
            .show()
        }
    }

    fun setTimetable(timetableModels: List<TimetableModel>) {
        var startTime: Time
        var endTime: Time
        var cIndex = 0
        // 타임 테이블 설정
        for ((_, id, _, subjectName, workDay1, quarter, cyber) in timetableModels) {
            schedule = Schedule()
            val workDay = workDay1.replace(" ", "")


            // 사이버 강의일 경우
            if (cyber == "Y") {
                schedule!!.day = 6
                schedule!!.classTitle = subjectName
                schedule!!.startTime = Time(cIndex + 9, 0)
                schedule!!.endTime = Time(cIndex + 10, 0)
                cIndex++
                schedules.add(schedule!!)
                scheduleID.add(id)
                continue
            }

            // 75분
            if (quarter == "Y") {
                var i = 0
                while (i < workDay.length) {
                    schedule = Schedule()
                    val day = Character.getNumericValue(workDay[i]) - 1
                    val hour = defaultHour[Character.getNumericValue(workDay[i + 1]) +
                            Character.getNumericValue(workDay[i + 2])]
                    val startMinute = quarterMinute[Character.getNumericValue(workDay[i + 2]) * 2 - 1]
                    val endMinute = quarterMinute[Character.getNumericValue(workDay[i + 2]) * 2]
                    schedule!!.day = day
                    schedule!!.classTitle = subjectName
                    schedule!!.startTime = Time(hour, startMinute)
                    schedule!!.endTime = Time(hour + 1, endMinute)
                    schedules.add(schedule!!)
                    scheduleID.add(id)
                    i += 3
                }
                continue
            }

            // 초기값 저장
            var firstTime = workDay.substring(1, 3)
            var firstDay = Character.getNumericValue(workDay[0]) - 1
            var firstHour = defaultHour[Character.getNumericValue(firstTime[0]) +
                    Character.getNumericValue(firstTime[1])]
            val minute = 0
            startTime = Time(firstHour, minute)
            endTime = Time(firstHour + 1, minute)

            // 공강 확인 데이터
            var preEndTime = firstHour + 1
            var nextDay = false

            // 1교시 과목
            if (workDay.length == 3) {
                schedule!!.day = firstDay
                schedule!!.classTitle = subjectName
                schedule!!.startTime = startTime
                schedule!!.endTime = endTime
            }

            // workDay --> 요일, 시간 분리하기
            // 시간 부분 --> time[0] + time[1]
            var i = 3
            while (i < workDay.length) {

                // 날짜가 바뀐 후 시작시간 재설정
                if (nextDay) {
                    nextDay = false
                    schedule = Schedule()
                    firstTime = workDay.substring(i - 3, i)
                    firstDay = Character.getNumericValue(firstTime[0]) - 1
                    firstHour = defaultHour[Character.getNumericValue(firstTime[1]) +
                            Character.getNumericValue(firstTime[2])]
                    startTime = Time(firstHour, minute)
                    endTime = Time(firstHour + 1, minute)
                    preEndTime = firstHour + 1
                }
                val day = Character.getNumericValue(workDay[i]) - 1
                val time = intArrayOf(
                    Character.getNumericValue(workDay[i + 1]),
                    Character.getNumericValue(workDay[i + 2])
                )

                // 현 인덱스 시간 확인
                val hour = defaultHour[time[0] + time[1]]
                // 공강 확인
                val emptyHour = (preEndTime != hour)

                // 날짜가 바뀌는 시점
                if (firstDay != day) {
                    endTime.hour = preEndTime
                    endTime.minute = minute
                    schedule!!.day = firstDay
                    schedule!!.classTitle = subjectName
                    schedule!!.startTime = startTime
                    schedule!!.endTime = endTime
                    schedules.add(schedule!!)
                    scheduleID.add(id)
                    nextDay = true
                    i += 3
                    continue
                }

                // 공강이 있는 시간
                // currentStartHour != previousEndHour 끊기
                if (emptyHour) {
                    // 중간 공강 있는 시간
                    endTime.hour = preEndTime
                    endTime.minute = minute
                    schedule!!.day = firstDay
                    schedule!!.classTitle = subjectName
                    schedule!!.startTime = startTime
                    schedule!!.endTime = endTime
                    schedules.add(schedule!!)
                    scheduleID.add(id)
                    nextDay = true
                    i += 3
                    continue
                }

                // 기본적인 시간표
                endTime.hour = hour + 1
                schedule!!.day = day
                schedule!!.classTitle = subjectName
                schedule!!.startTime = startTime
                schedule!!.endTime = endTime

                // 다음 시간과 비교하기 위해 저장
                preEndTime = hour + 1
                i += 3
            }
            schedules.add(schedule!!)
            scheduleID.add(id)
        }
    }
}