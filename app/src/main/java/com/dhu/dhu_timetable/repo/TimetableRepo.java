package com.dhu.dhu_timetable.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dhu.dhu_timetable.service.APIService;
import com.dhu.dhu_timetable.service.RetrofitConnect;
import com.dhu.dhu_timetable.network.TimetableApiClient;
import com.dhu.dhu_timetable.model.TimetableModel;

import java.util.List;

public class TimetableRepo {
    // 싱글톤 패턴
    private static TimetableRepo instance;

    private TimetableApiClient mTimetableApiClient;

    public static TimetableRepo getInstance() {
        if (instance == null)
            instance = new TimetableRepo();
        return instance;
    }
    private APIService service;
    String TAG = "janghee";

    public TimetableRepo() {
        mTimetableApiClient = TimetableApiClient.getInstance();
        service = RetrofitConnect.getRetrofitClient().create(APIService.class);
        Log.d(TAG, "TimetableRepo: 성공");
    }

    public LiveData<List<TimetableModel>> getTimetable() {
        return mTimetableApiClient.getTimetable();
    }

    // 3- Calling the method in repository
    public void setTimetableApi(String email) {
        mTimetableApiClient.getTimetableData(email);

    }

    public void insertTimetableApi(String email, String subjectName, String workDay, String cyber, String quarter) {
        mTimetableApiClient.setTimetableData(email, subjectName, workDay, cyber, quarter);
    }

    public void nextInsertTimetableApi(String email) {
        setTimetableApi(email);
    }

    public void deleteTimetableApi(String email, int id) {
        mTimetableApiClient.deleteTimetableData(email, id);
    }

}
