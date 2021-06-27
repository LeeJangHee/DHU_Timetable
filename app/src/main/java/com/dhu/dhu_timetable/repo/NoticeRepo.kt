package com.dhu.dhu_timetable.repo

import androidx.lifecycle.LiveData
import com.dhu.dhu_timetable.model.NoticeModel
import com.dhu.dhu_timetable.network.NoticeApiClient

class NoticeRepo {
    private val mNoticeApiClient: NoticeApiClient by lazy { NoticeApiClient.getInstance() }

    companion object {
        @Volatile private var instance: NoticeRepo? = null
        @JvmStatic
        fun getInstance(): NoticeRepo =
                instance ?: synchronized(this) {
                    instance ?: NoticeRepo().also { instance = it }
                }
    }


    fun getNoticeData(): LiveData<List<NoticeModel>> {
        return mNoticeApiClient.noticeData
    }

    fun setNoticeData() {
        mNoticeApiClient.setNoticeData()
    }
}