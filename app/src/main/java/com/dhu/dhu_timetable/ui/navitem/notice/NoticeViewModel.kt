package com.dhu.dhu_timetable.ui.navitem.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhu.dhu_timetable.model.NoticeModel
import com.dhu.dhu_timetable.repo.NoticeRepo
import com.dhu.dhu_timetable.repo.NoticeRepo.Companion.getInstance
import java.lang.Exception


class NoticeViewModel : ViewModel() {
    private lateinit var noticeRepo: NoticeRepo

    val noticeData: LiveData<List<NoticeModel>>
        get() = noticeRepo.getNoticeData()

    fun setNoticeData() {
        noticeRepo.setNoticeData()
    }

//    fun getNoticeData(): LiveData<List<NoticeModel>> = noticeData

    init {
        try {
            noticeRepo = getInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}