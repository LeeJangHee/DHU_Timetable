package com.dhu.dhu_timetable.service

import com.dhu.dhu_timetable.util.Conts.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitConnect {

    var retrofit: Retrofit? = null

    fun getRetrofitClient(): Retrofit? {
        val okHttpClient = OkHttpClient()
        okHttpClient.newBuilder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit

    }

}