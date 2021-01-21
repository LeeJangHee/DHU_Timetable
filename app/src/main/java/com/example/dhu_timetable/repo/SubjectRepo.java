package com.example.dhu_timetable.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dhu_timetable.network.SubjectApiClient;
import com.example.dhu_timetable.ui.subject.SubjectModel;

import java.util.List;

public class SubjectRepo {
    private SubjectApiClient mSubjectApiClient;

    // 싱글톤 패턴
    private static SubjectRepo instance;

    public static SubjectRepo getInstance() {
        if (instance == null)
            instance = new SubjectRepo();
        return instance;
    }

    public SubjectRepo() {
        mSubjectApiClient = SubjectApiClient.getInstance();
        Log.d("janghee", "SubjectRepo: 성공");
    }

    public LiveData<List<SubjectModel>> getSubjectData() {
        return mSubjectApiClient.getSubjectData();
    }

    public void setSubjectData(String year, String semester, String name, String level, String major, String cyber) {
        mSubjectApiClient.setSubjectData(year, semester, name, level, major, cyber);
    }

}
