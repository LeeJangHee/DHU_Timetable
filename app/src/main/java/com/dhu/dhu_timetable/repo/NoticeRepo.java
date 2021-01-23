package com.dhu.dhu_timetable.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dhu.dhu_timetable.ui.navitem.notice.NoticeModel;
import com.dhu.dhu_timetable.network.NoticeApiClient;

import java.util.List;

public class NoticeRepo {
    private NoticeApiClient mNoticeApiClient;

    // 싱글톤 패턴
    private static NoticeRepo instance;

    public static NoticeRepo getInstance() {
        if (instance == null)
            instance = new NoticeRepo();
        return instance;
    }

    public NoticeRepo() {
        mNoticeApiClient = NoticeApiClient.getInstance();
        Log.d("jaemin", "NoticeRepo: 성공");
    }

    public LiveData<List<NoticeModel>> getNoticeData(){
        return mNoticeApiClient.getNoticeData();
    }

    public void setNoticeData(){
        mNoticeApiClient.setNoticeData();
    }

}

