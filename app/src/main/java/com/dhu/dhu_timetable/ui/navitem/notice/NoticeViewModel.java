package com.dhu.dhu_timetable.ui.navitem.notice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dhu.dhu_timetable.model.NoticeModel;
import com.dhu.dhu_timetable.repo.NoticeRepo;
import java.util.List;

public class NoticeViewModel extends ViewModel {
    private MutableLiveData<List<NoticeModel>> noticeModels;
    private List<NoticeModel> noticeModelList;
    private NoticeRepo noticeRepo;

    public NoticeViewModel() {
        if (noticeRepo != null) {
            return;
        }
        noticeRepo = NoticeRepo.getInstance();
    }

    public LiveData<List<NoticeModel>> getNoticeData() {
        return noticeRepo.getNoticeData();
    }

    public void setNoticeData() {
        noticeRepo.setNoticeData();
    }
}
