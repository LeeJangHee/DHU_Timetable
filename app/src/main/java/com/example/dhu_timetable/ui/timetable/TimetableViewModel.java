package com.example.dhu_timetable.ui.timetable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dhu_timetable.repo.TimetableRepo;

import java.util.List;

public class TimetableViewModel extends ViewModel {
    MutableLiveData<List<TimetableModel>> timetableData;
    TimetableRepo timetableRepo;

    public TimetableViewModel() {
        if (timetableData != null) {
            return;
        }
        timetableRepo = TimetableRepo.getInstance();
    }

    public void init(String email){
        timetableData = timetableRepo.getTimetableData(email);
    }

    public void init(String email, String subjectName, String workDay) {
        timetableRepo.setTimetable(email, subjectName, workDay);
    }
    
}
