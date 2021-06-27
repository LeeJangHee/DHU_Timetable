package com.dhu.dhu_timetable.repo

import androidx.lifecycle.LiveData
import com.dhu.dhu_timetable.model.TimetableModel
import com.dhu.dhu_timetable.network.TimetableApiClient

class TimetableRepo {

    private val mTimetableApiClient: TimetableApiClient by lazy { TimetableApiClient.getInstance() }

    companion object {
        @Volatile private var instance: TimetableRepo? = null
        @JvmStatic
        fun getInstance(): TimetableRepo =
                instance ?: synchronized(this) {
                    instance ?: TimetableRepo().also { instance = it }
                }
    }

    fun getTimetable(): LiveData<List<TimetableModel>> {
        return mTimetableApiClient.timetable
    }

    fun setTimetableApi(email: String) {
        mTimetableApiClient.getTimetableData(email)
    }

    fun insertTimetableApi(email: String, subjectName: String, workDay: String, cyber: String, quarter: String) {
        mTimetableApiClient.setTimetableData(email, subjectName, workDay, cyber, quarter)
    }

    fun nextInsertTimetableApi(email: String) {
        setTimetableApi(email)
    }

    fun deleteTimetableApi(email: String, id: Int) {
        mTimetableApiClient.deleteTimetableData(email, id)
    }

}