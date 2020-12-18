package com.example.dhu_timetable.ui.subject;

import android.widget.ImageButton;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dhu_timetable.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * 수강정보 비지니스 로직 구현
 */
public class SubjectViewModel extends ViewModel {
    private MutableLiveData<List<SubjectModel>> subjectList = new MutableLiveData<>();
    private MutableLiveData<MaterialCardView> cardview = new MutableLiveData<>();

    /**
     * 라이브 데이터 초기화 부분
     */
    public SubjectViewModel() {
        List<SubjectModel> models = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            SubjectModel data = new SubjectModel();

            data.setText("TEST : " + (i + 1));
            models.add(data);
        }
        subjectList.setValue(models);
    }

    public MutableLiveData<MaterialCardView> getCardview() {
        return cardview;
    }

    public void setCardview(MutableLiveData<MaterialCardView> cardview) {
        this.cardview = cardview;
    }

    public MutableLiveData<List<SubjectModel>> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(MutableLiveData<List<SubjectModel>> subjectList) {
        this.subjectList = subjectList;
    }
}
