package com.dhu.dhu_timetable.repo

import androidx.lifecycle.LiveData
import com.dhu.dhu_timetable.model.SubjectModel
import com.dhu.dhu_timetable.network.SubjectApiClient

class SubjectRepo {

    private val mSubjectApiClient: SubjectApiClient by lazy { SubjectApiClient.getInstance() }

    companion object {
        @Volatile private var instance: SubjectRepo? = null
        @JvmStatic
        fun getInstance(): SubjectRepo =
                instance ?: synchronized(this) {
                    instance ?: SubjectRepo().also { instance = it }
                }
    }

    fun getSubjectData(): LiveData<List<SubjectModel>> =
            mSubjectApiClient.subjectData

    fun setSubjectData(year: String, semester: String, name: String, level: String, major: String, cyber: String) {
        mSubjectApiClient.setSubjectData(year, semester, name, level, major, cyber)
    }
}