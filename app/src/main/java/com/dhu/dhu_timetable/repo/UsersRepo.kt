package com.dhu.dhu_timetable.repo

import androidx.lifecycle.MutableLiveData
import com.dhu.dhu_timetable.model.LoginModel
import com.dhu.dhu_timetable.network.APIService
import com.dhu.dhu_timetable.network.RetrofitConnect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepo {
    private val service: APIService by lazy { RetrofitConnect.getRetrofitClient()!!.create(APIService::class.java) }

    companion object {
        @Volatile private var instance: UsersRepo? = null
        @JvmStatic
        fun getInstance(): UsersRepo =
                instance ?: synchronized(this) {
                    instance ?: UsersRepo().also { instance = it }
                }
    }

    fun getUser(email: String): MutableLiveData<LoginModel> {
        val user = MutableLiveData<LoginModel>()
        service.getUser(email).enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.isSuccessful) {
                    user.value = response.body()

                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {

            }
        })
        return user
    }

    fun setUser(email: String, name: String, profile: String) {
        val user = MutableLiveData<LoginModel>()
        service.checkUser(email, name, profile).enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.isSuccessful) {
                    user.value = response.body()

                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {

            }
        })
    }
}