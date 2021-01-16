package com.example.dhu_timetable.ui.subject;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dhu_timetable.repo.SearchRepo;
import com.example.dhu_timetable.repo.SubjectRepo;

import java.util.List;

/**
 * 수강정보 비지니스 로직 구현
 */
public class SubjectViewModel extends ViewModel {
    private static final String TAG = "janghee";
    private MutableLiveData<List<SubjectModel>> subjectData;
    private SubjectRepo subjectRepo;
    private LiveData<String> email;
    private SearchRepo searchRepo;

    public SubjectViewModel() {
        if (subjectData != null) {
            return;
        }
        subjectRepo = SubjectRepo.getInstance();
        searchRepo = SearchRepo.getInstance();
    }


    /**
     * 라이브 데이터 초기화 부분
     */
    public void init() {
        subjectData = subjectRepo.getData();
        Log.d(TAG, "init: "+subjectData.getValue());
    }

    public void init(String year, String month) {
        String semester = "";
        // 학기 정하기
        if (Integer.parseInt(month) > 0 && Integer.parseInt(month) <= 6) {
            semester = "10";    // 1학기
        } else if (Integer.parseInt(month) > 6 && Integer.parseInt(month) <= 12) {
            semester = "20";    // 2학기
        }

        subjectData = subjectRepo.getDataDef(year, semester);
        Log.d(TAG, "init: "+subjectData.getValue());
    }

    // 검색 데이터 불러오기
    public void searchinit(String year, String semester, String name, String level, String major, String cyber) {
        searchRepo.getSearchData("","",name, level, major, cyber);
        subjectData = searchRepo.getSearchData("","",name,level,major,cyber);
        Log.d(TAG, "searchinit: " + subjectData.getValue());
    }

    public LiveData<List<SubjectModel>> getSubjectData() {
        return subjectData;
    }
}
