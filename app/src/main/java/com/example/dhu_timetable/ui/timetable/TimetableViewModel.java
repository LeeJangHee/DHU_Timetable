package com.example.dhu_timetable.ui.timetable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dhu_timetable.repo.TimetableRepo;

import java.util.List;

public class TimetableViewModel extends ViewModel {
    private static MutableLiveData<List<TimetableModel>> timetableData;
    TimetableRepo timetableRepo;

    public TimetableViewModel() {
        if (timetableData != null) {
            return;
        }
        timetableRepo = TimetableRepo.getInstance();
        timetableData = new MutableLiveData<>();
    }

    public void init(String email) {
        timetableData.postValue(timetableRepo.getTimetableData(email));
    }

    public static LiveData<List<TimetableModel>> getTimetableData() {
        return timetableData;
    }

    public static void setTimetableData(List<TimetableModel> data) {
        timetableData.setValue(data);
    }
}
