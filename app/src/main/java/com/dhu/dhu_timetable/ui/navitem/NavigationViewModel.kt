package com.dhu.dhu_timetable.ui.navitem

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhu.dhu_timetable.model.LoginModel
import com.dhu.dhu_timetable.repo.UsersRepo
import com.dhu.dhu_timetable.repo.UsersRepo.Companion.getInstance
import java.lang.Exception

class NavigationViewModel : ViewModel() {
    var loginModel: MutableLiveData<LoginModel>? = null
    private lateinit var usersRepo: UsersRepo
    private var TAG = "janghee"

    fun initLoginData(email: String?) {
        loginModel = usersRepo.getUser(email!!)
        Log.d(TAG, "init: ")
    }

    init {
        try {
            if (loginModel == null) {
                usersRepo = getInstance()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}