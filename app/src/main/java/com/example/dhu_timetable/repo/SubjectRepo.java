package com.example.dhu_timetable.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.dhu_timetable.service.APIService;
import com.example.dhu_timetable.service.RetrofitConnect;
import com.example.dhu_timetable.ui.subject.SubjectModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectRepo {

    // 싱글톤 패턴
    private static SubjectRepo instance;

    public static SubjectRepo getInstance() {
        if (instance == null)
            instance = new SubjectRepo();
        return instance;
    }

    private final APIService service;
    String TAG = "janghee";

    public SubjectRepo() {
        service = RetrofitConnect.getRetrofitClient().create(APIService.class);
        Log.d(TAG, "SubjectRepo: 성공");
    }

    public MutableLiveData<List<SubjectModel>> getData() {
        MutableLiveData<List<SubjectModel>> subjectData = new MutableLiveData<>();
        service.test().enqueue(new Callback<List<SubjectModel>>() {
            @Override
            public void onResponse(Call<List<SubjectModel>> call, Response<List<SubjectModel>> response) {
                if (response.isSuccessful()) {
                    subjectData.setValue(response.body());
                    Log.d(TAG, "Repo onResponse: 과목성공");
                }
            }

            @Override
            public void onFailure(Call<List<SubjectModel>> call, Throwable t) {
                Log.w(TAG, "Repo onFailure: "+t.getMessage(), t);
            }
        });

        return subjectData;
    }

    public MutableLiveData<List<SubjectModel>> getDataDef(String year, String semester) {
        MutableLiveData<List<SubjectModel>> subjectData = new MutableLiveData<>();
        service.getSubject(year, semester).enqueue(new Callback<List<SubjectModel>>() {
            @Override
            public void onResponse(Call<List<SubjectModel>> call, Response<List<SubjectModel>> response) {
                if (response.isSuccessful()) {
                    subjectData.setValue(response.body());
                    Log.d(TAG, "Repo onResponse: 과목 성공");
                }
            }

            @Override
            public void onFailure(Call<List<SubjectModel>> call, Throwable t) {
                Log.w(TAG, "Repo onFailure: "+t.getMessage(), t);
            }
        });
        
        return subjectData;
    }

}
