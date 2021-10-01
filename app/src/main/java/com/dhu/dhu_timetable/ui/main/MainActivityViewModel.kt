package com.dhu.dhu_timetable.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dhu.dhu_timetable.model.SubjectModel
import com.dhu.dhu_timetable.model.TimetableModel
import com.dhu.dhu_timetable.repo.SubjectRepo
import com.dhu.dhu_timetable.repo.TimetableRepo
import java.lang.Exception

class MainActivityViewModel: ViewModel() {
    private val timetableRepo: TimetableRepo by lazy { TimetableRepo.getInstance() }
    private val subjectRepo: SubjectRepo by lazy { SubjectRepo.getInstance() }

    // 시간표라이브데이터
    fun getTimetable(): LiveData<List<TimetableModel>> = timetableRepo.getTimetable()

    fun setTimetable(email: String) {
        timetableRepo.setTimetableApi(email)
    }

    fun insertTimetable(email: String, subjectName: String, workDay: String, cyber: String, quarter: String) {
        timetableRepo.insertTimetableApi(email, subjectName, workDay, cyber, quarter)
    }

    fun getTimetableList(): List<TimetableModel>? = timetableRepo.getTimetable().value

    fun nextInsertTimetable(email: String) {
        timetableRepo.nextInsertTimetableApi(email)
    }

    fun deleteTimetable(email: String, id: Int) {
        timetableRepo.deleteTimetableApi(email, id)
    }

    // 강의표 라이브 데이터
    fun getSubjectData(): LiveData<List<SubjectModel>> = subjectRepo.getSubjectData()

    fun setSubjectData(year: String, semester: String, name: String, level: String, major: String, cyber: String) {
        subjectRepo.setSubjectData(year, semester, name, level, major, cyber)
    }

    fun isTimeCheck(workDay: String?): Boolean {
        getTimetableList()?.forEach {
            for (i in it.workDay.indices step 3) {
                if (workDay!!.contains(it.workDay.substring(i, i+3))) {
                    Log.d("janghee", "timeCheck: false")
                    return false
                }
            }
        }
        Log.d("janghee", "timeCheck: true")
        return true
    }

    fun onAddSubject(email: String?, subjectName: String?, workDay: String?, cyber: String?, quarter: String?) {
        insertTimetable(email!!, subjectName!!, workDay!!, cyber!!, quarter!!)
    }

}