package com.example.dhu_timetable.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.dhu_timetable.service.APIService;
import com.example.dhu_timetable.service.RetrofitConnect;
import com.example.dhu_timetable.ui.timetable.TimetableModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimetableRepo {
    // 싱글톤 패턴
    private static TimetableRepo instance;

    public static TimetableRepo getInstance() {
        if (instance == null)
            instance = new TimetableRepo();
        return instance;
    }

    private final APIService service;
    String TAG = "janghee";

    public TimetableRepo() {
        service = RetrofitConnect.getRetrofitClient().create(APIService.class);
        Log.d(TAG, "TimetableRepo: 성공");
    }

    /**
     * 서버에 원하는 값을 넣기
     * @param email : 유저
     * @param subjectName : 과목명
     * @param workDay : 요일 & 시간 ( DB column = workDay )
     */
    public void setTimetable(String email, String subjectName, String workDay, String cyber, String quarter) {
        service.setTimetable(email, subjectName, workDay, cyber, quarter)
                .enqueue(new Callback<TimetableModel>() {
            @Override
            public void onResponse(Call<TimetableModel> call, Response<TimetableModel> response) {
                Log.d(TAG, "onResponse: " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<TimetableModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getCause());
            }
        });
    }

    /**
     * 라이브 데이터에 레트로핏 값 넣기
     * @param email = 이메일에 있는 유저의 값
     * @return : 라이브데이터 리턴
     */
    public MutableLiveData<List<TimetableModel>> getTimetableData(String email) {
        MutableLiveData<List<TimetableModel>> timetableData = new MutableLiveData<>();
        service.checkTimetable(email).enqueue(new Callback<List<TimetableModel>>() {
            @Override
            public void onResponse(Call<List<TimetableModel>> call, Response<List<TimetableModel>> response) {
                if (response.isSuccessful()) {
                    timetableData.setValue(response.body());
                    Log.d(TAG, "timetable onResponse:\n"+timetableData.getValue());
                }
            }

            @Override
            public void onFailure(Call<List<TimetableModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return timetableData;
    }

    public List<TimetableModel> getCheckData(String email) {
        List<TimetableModel> timetableData = new ArrayList<>();
        service.checkTimetable(email).enqueue(new Callback<List<TimetableModel>>() {
            @Override
            public void onResponse(Call<List<TimetableModel>> call, Response<List<TimetableModel>> response) {
                if (response.isSuccessful()) {
                    timetableData.addAll(response.body());
                    Log.d(TAG, "timetable getCheckData onResponse: "+timetableData.size());
                }
            }

            @Override
            public void onFailure(Call<List<TimetableModel>> call, Throwable t) {
                Log.d(TAG, "timetable getCheckData onFailure: "+t.getMessage());
            }
        });

        return timetableData;
    }
}
