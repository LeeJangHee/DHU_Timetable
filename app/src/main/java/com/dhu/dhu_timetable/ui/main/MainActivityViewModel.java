package com.dhu.dhu_timetable.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dhu.dhu_timetable.model.SubjectModel;
import com.dhu.dhu_timetable.model.TimetableModel;
import com.dhu.dhu_timetable.repo.SubjectRepo;
import com.dhu.dhu_timetable.repo.TimetableRepo;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
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

    public void deleteTimetable(String email, int id) {
        timetableRepo.deleteTimetableApi(email, id);
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

    public boolean  isTimeCheck(String workDay) {
        for (TimetableModel t : getTimetableList()) {
            for (int i = 0; i < t.getWorkDay().length(); i += 3) {
                if (workDay.contains(t.getWorkDay().substring(i, i + 3))) {
                    Log.d("janghee", "timeCheck: false");
                    return false;
                }
            }
        }
        Log.d("janghee", "timeCheck: true");
        return true;
    }

    public void onAddSubject(String email, String subjectName, String workDay, String cyber, String quarter) {
        insertTimetable(email, subjectName, workDay, cyber, quarter);
    }
}
