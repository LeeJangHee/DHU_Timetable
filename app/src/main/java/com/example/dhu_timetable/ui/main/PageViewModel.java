package com.example.dhu_timetable.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    /**
     * 멀티플라이브데이터의 인덱스 설정
     * @param index = 멀티플라이브데이터의 인덱스
     */
    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    /**
     * 라이브 데이터의 텍스트 불러오기
     * @return mText의 초기화 된 데이터를 리턴한다.
     */
    public LiveData<String> getText() {
        return mText;
    }
}