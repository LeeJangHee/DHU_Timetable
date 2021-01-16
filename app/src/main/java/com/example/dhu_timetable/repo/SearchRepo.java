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

public class SearchRepo {

    private static SearchRepo instance;

    public static SearchRepo getInstance() {
        if (instance == null)
            instance = new SearchRepo();
        return instance;
    }

    private final APIService service;
    String TAG = "janghee";

    public SearchRepo() {
        service = RetrofitConnect.getRetrofitClient().create(APIService.class);
        Log.d(TAG, "SubjectRepo: 성공");
    }

    public MutableLiveData<List<SubjectModel>> getSearchData(String year, String semester, String name, String level, String major, String cyber) {
        MutableLiveData<List<SubjectModel>> subjectData = new MutableLiveData<>();
        service.getSearch(year, semester, name, level, major, cyber).enqueue(new Callback<List<SubjectModel>>() {
            @Override
            public void onResponse(Call<List<SubjectModel>> call, Response<List<SubjectModel>> response) {
                if (response.isSuccessful()) {
                    subjectData.setValue(response.body());
                    Log.d(TAG, "Repo onResponse: 검색성공");
                }
            }

            @Override
            public void onFailure(Call<List<SubjectModel>> call, Throwable t) {
                Log.w(TAG, "Repo onFailure: " + t.getMessage(), t);
            }
        });

        return subjectData;
    }
}