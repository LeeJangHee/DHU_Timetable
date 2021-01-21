package com.example.dhu_timetable.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dhu_timetable.repo.TimetableRepo;
import com.example.dhu_timetable.service.APIService;
import com.example.dhu_timetable.service.RetrofitConnect;
import com.example.dhu_timetable.ui.timetable.TimetableModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class TimetableApiClient {
    private MutableLiveData<List<TimetableModel>> mTimetableModels;
    private MutableLiveData<TimetableModel> setTimetable;
    private RetrieveTimetableRunnable retrieveTimetableRunnable;
    private SetTimetableRunnable setTimetableRunnable;

    private static TimetableApiClient instance;

    // 싱글톤
    public static TimetableApiClient getInstance() {
        if (instance == null) {
            instance = new TimetableApiClient();
        }
        return instance;
    }

    public TimetableApiClient() {
        mTimetableModels = new MutableLiveData<>();
        setTimetable = new MutableLiveData<>();
    }

    public LiveData<List<TimetableModel>> getTimetable() {
        return mTimetableModels;
    }

    // 유저별 테이블 정보 가져오기
    public void getTimetableData(String email) {
        if (retrieveTimetableRunnable != null) {
            retrieveTimetableRunnable = null;
        }

        retrieveTimetableRunnable = new TimetableApiClient.RetrieveTimetableRunnable(email);

        final Future mHandler = AppExecutors.getInstance()
                .networkIO()
                .submit(retrieveTimetableRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // 타임아웃
                mHandler.cancel(true);
            }
        }, 5, TimeUnit.SECONDS);

    }

    // 유저별 테이블 정보 백그라운드 레트로핏 부분
    private class RetrieveTimetableRunnable implements Runnable {
        private String email;
        boolean cancelRequest;

        public RetrieveTimetableRunnable(String email) {
            this.email = email;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response<List<TimetableModel>> response = getMyTimetable(email).execute();

                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<TimetableModel> timetableModels = new ArrayList<>(response.body());
                    mTimetableModels.postValue(timetableModels);
                } else {
                    String error = response.errorBody().string();
                    Log.v("janghee", "getTimetable background error: "+error);
                    mTimetableModels.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mTimetableModels.postValue(null);
            }


        }

        // 레트로핏 검색
        private Call<List<TimetableModel>> getMyTimetable(String email) {
            return RetrofitConnect.getRetrofitClient()
                    .create(APIService.class)
                    .checkTimetable(email);
        }

        private void cancelRequest() {
            Log.v("janghee", "Cancelling Request getMyTimetable");
            cancelRequest = true;
        }
    }

    //시간표에 데이터 넣기 = insert into
    public void setTimetableData(String email, String subjectName, String workDay, String cyber, String quarter) {
        if (setTimetableRunnable != null) {
            setTimetableRunnable = null;
        }

        setTimetableRunnable = new TimetableApiClient.SetTimetableRunnable(email, subjectName, workDay, cyber, quarter);
        final Future mHandler = AppExecutors.getInstance()
                .networkIO()
                .submit(setTimetableRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // 타임아웃
                mHandler.cancel(true);
            }
        }, 6, TimeUnit.SECONDS);


    }

    // 시간표에 데이터 넣기 백그라운드 레트로핏 부분
    private class SetTimetableRunnable implements Runnable {
        private String email;
        private String subjectName;
        private String workDay;
        private String cyber;
        private String quarter;
        boolean cancelRequest;

        public SetTimetableRunnable(String email, String subjectName, String workDay, String cyber, String quarter) {
            this.email = email;
            this.subjectName = subjectName;
            this.workDay = workDay;
            this.cyber = cyber;
            this.quarter = quarter;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response<TimetableModel> response = setMyTimetable(
                        email,subjectName,workDay,cyber,quarter).execute();

                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    Log.v("janghee", "setTimetable ok");

                } else {
                    String error = response.errorBody().string();
                    Log.v("janghee", "setTimetable background error: "+error);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 레트로핏 검색
        private Call<TimetableModel> setMyTimetable(String email, String subjectName, String workDay, String cyber, String quarter) {
            return RetrofitConnect.getRetrofitClient()
                    .create(APIService.class)
                    .setTimetable(email, subjectName, workDay, cyber, quarter);
        }

        private void cancelRequest() {
            Log.v("janghee", "Cancelling Request SetMyTimetable");
            cancelRequest = true;
        }
    }
}
