package com.example.dhu_timetable.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dhu_timetable.service.APIService;
import com.example.dhu_timetable.service.RetrofitConnect;
import com.example.dhu_timetable.ui.subject.SubjectModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 과목 데이터 백그라운드에 레트로핏 연결
 */
public class SubjectApiClient {
    private MutableLiveData<List<SubjectModel>> mSubjectModels;
    private RetrieveSubjectRunnable retrieveSubjectRunnable;

    private static SubjectApiClient instance;

    // 싱글톤
    public static SubjectApiClient getInstance() {
        if (instance == null) {
            instance = new SubjectApiClient();
        }
        return instance;
    }

    public SubjectApiClient() {
        mSubjectModels = new MutableLiveData<>();
    }

    // 겟터
    public LiveData<List<SubjectModel>> getSubjectData(){
        return mSubjectModels;
    }

    // 셋터
    public void setSubjectData(String year, String semester, String name, String level, String major, String cyber) {
        if (retrieveSubjectRunnable != null) {
            retrieveSubjectRunnable = null;
        }

        retrieveSubjectRunnable = new SubjectApiClient.RetrieveSubjectRunnable(year, semester, name, level, major, cyber);

        final Future mHandler = AppExecutors.getInstance()
                .networkIO()
                .submit(retrieveSubjectRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                mHandler.cancel(true);
            }
        },5, TimeUnit.SECONDS);
    }

    // 레트로핏 백그라운드 부분
    private class RetrieveSubjectRunnable implements Runnable {
        private String year;
        private String semester;
        private String name;
        private String level;
        private String major;
        private String cyber;
        boolean cancelRequest;

        public RetrieveSubjectRunnable(String year, String semester, String name, String level, String major, String cyber) {
            this.year = year;
            this.semester = semester;
            this.name = name;
            this.level = level;
            this.major = major;
            this.cyber = cyber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response<List<SubjectModel>> response =
                        getSearchSubject(year, semester, name, level, major, cyber).execute();

                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<SubjectModel> subjectModels = new ArrayList<>(response.body());
                    mSubjectModels.postValue(subjectModels);
                } else {
                    String error = response.errorBody().string();
                    Log.v("janghee", "getSearchSubject background error: "+error);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mSubjectModels.postValue(null);
            }
        }

        // 레트로핏
        private Call<List<SubjectModel>> getSearchSubject(String year, String semester, String name, String level, String major, String cyber) {
            return RetrofitConnect.getRetrofitClient()
                    .create(APIService.class)
                    .getSearch(year, semester, name, level, major, cyber);
        }

        private void cancelRequest() {
            Log.v("janghee", "Cancelling Request getSearchSubject");
            cancelRequest = true;
        }

    }


    // 참고 동영상 (영어)
    // https://youtu.be/3l4OShOR8jw
}
