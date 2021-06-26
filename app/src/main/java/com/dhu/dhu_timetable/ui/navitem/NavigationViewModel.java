package com.dhu.dhu_timetable.ui.navitem;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dhu.dhu_timetable.model.LoginModel;
import com.dhu.dhu_timetable.repo.UsersRepo;

public class NavigationViewModel extends ViewModel {
    MutableLiveData<LoginModel> loginModel;
    UsersRepo usersRepo;
    String TAG = "janghee";

    public NavigationViewModel() {
        if (loginModel != null) {
            return;
        }
        usersRepo = UsersRepo.getInstance();
    }

    public void init(String email) {
        loginModel = usersRepo.getUser(email);
        Log.d(TAG, "init: ");
    }

    public MutableLiveData<LoginModel> getTest() {
        return loginModel;
    }
}
