package com.example.dhu_timetable.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dhu_timetable.repo.SubjectRepo;
import com.example.dhu_timetable.repo.TimetableRepo;
import com.example.dhu_timetable.ui.subject.SubjectModel;
import com.example.dhu_timetable.ui.timetable.TimetableModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<TimetableModel>> timetaleModels = new MutableLiveData<>();
    private MutableLiveData<List<SubjectModel>> subjectModels = new MutableLiveData<>();
    private List<TimetableModel> timetableList = new ArrayList<>();
    private TimetableRepo timetableRepo;
    private SubjectRepo subjectRepo;

    public MainActivityViewModel() {
        if (timetableRepo != null && subjectRepo != null) {
            return;
        }
        timetableRepo = TimetableRepo.getInstance();
        subjectRepo = SubjectRepo.getInstance();
    }

    // 시간표 라이브데이터 {
    public LiveData<List<TimetableModel>> getTimetable() {
        return timetableRepo.getTimetable();
    }

    public void setTimetable(String email) {
        timetableRepo.setTimetableApi(email);
    }

    public void insertTimetable(String email, String subjectName, String workDay, String cyber, String quarter) {
        timetableRepo.insertTimetableApi(email, subjectName, workDay, cyber, quarter);
    }

    public List<TimetableModel> getTimetableList() {
        return timetableRepo.getTimetable().getValue();
    }

    public void nextInsertTimetable(String email) {
        timetableRepo.nextInsertTimetableApi(email);
    }
    // }

    // 강의표 라이브 데이터 {
    public LiveData<List<SubjectModel>> getSubjectData() {
        return subjectRepo.getSubjectData();
    }

    public void setSubjectData(String year, String semester, String name, String level, String major, String cyber) {
        subjectRepo.setSubjectData(year, semester, name, level, major, cyber);
    }
    // }
}
