package com.example.dhu_timetable.ui.subject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubjectViewModel extends ViewModel {
    public static MutableLiveData<String> subject_title = new MutableLiveData<>();

    public SubjectViewModel() {
        subject_title.setValue("");
    }

    public static void addSubjcct(){

    }
}
