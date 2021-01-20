package com.example.dhu_timetable.ui.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dhu_timetable.repo.SearchRepo;
import com.example.dhu_timetable.repo.SubjectRepo;
import com.example.dhu_timetable.ui.subject.SubjectModel;

import java.util.List;

// 필요시 사용 --> 현재는 SubjectViewModel에 구현
public class SearchViewModel extends ViewModel {
    private static final String TAG = "jaemin";
    private MutableLiveData<List<SubjectModel>> subjectData;
    private SearchRepo searchRepo;

    public SearchViewModel() {
        if (subjectData != null) {
            return;
        }
        searchRepo = SearchRepo.getInstance();
    }

    public void init(String year, String semester, String name, String level, String major, String cyber) {
        //subjectData = searchRepo.getSearchData("", "", name, level, major, cyber);
        Log.d(TAG, "init: " + subjectData.getValue());
    }

    public MutableLiveData<List<SubjectModel>> getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(MutableLiveData<List<SubjectModel>> subjectData) {
        this.subjectData = subjectData;
    }
}