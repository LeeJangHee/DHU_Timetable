package com.example.dhu_timetable.ui.subject;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dhu_timetable.repo.SubjectRepo;

import java.util.List;

/**
 * 수강정보 비지니스 로직 구현
 */
public class SubjectViewModel extends ViewModel {
    private static final String TAG = "TAG";
    private MutableLiveData<List<SubjectModel>> subjectData;
    private SubjectRepo subjectRepo;

    /**
     * 라이브 데이터 초기화 부분
     */
    public void init() {
        if (subjectData != null) {
            return;
        }
        subjectRepo = SubjectRepo.getInstance();
        subjectData = subjectRepo.getData();
        Log.d(TAG, "init: "+subjectData.getValue());
    }

    public MutableLiveData<List<SubjectModel>> getSubjectData() {
        return subjectData;
    }
}
