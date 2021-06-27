package com.dhu.dhu_timetable.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dhu.dhu_timetable.model.LoginModel;
import com.dhu.dhu_timetable.service.APIService;
import com.dhu.dhu_timetable.service.RetrofitConnect;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepo {

    // 싱글톤 패턴
    private static UsersRepo instance;

    public static UsersRepo getInstance() {
        if (instance == null)
            instance = new UsersRepo();
        return instance;
    }

    private final APIService service;
    String TAG = "janghee";

    public UsersRepo() {
        service = RetrofitConnect.INSTANCE.getRetrofitClient().create(APIService.class);
        Log.d(TAG, "UserRepo: 성공");
    }

    public MutableLiveData<LoginModel> getUser(String email) {
        MutableLiveData<LoginModel> user = new MutableLiveData<>();
        service.getUser(email).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    user.setValue(response.body());
                    Log.e(TAG, "onResponse: "+response.body().getName() + response.body().getEmail());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d(TAG, "Repo onFailure: "+t.getMessage());
            }
        });

        return user;
    }

    public void setUser(String email, String name, String profile) {
        MutableLiveData<LoginModel> user = new MutableLiveData<>();
        service.checkUser(email, name, profile).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    user.setValue(response.body());
                    Log.d(TAG, "onResponse: 유저등록");
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d(TAG, "Repo onFailure: "+t.getMessage());
            }
        });

    }

}
