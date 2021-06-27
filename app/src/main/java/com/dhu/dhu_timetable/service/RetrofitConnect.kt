package com.dhu.dhu_timetable.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitConnect {

    val baseURL = "http://wkdgml96.iptime.org:8080/timetable/"
    var retrofit: Retrofit? = null

    fun getRetrofitClient(): Retrofit? {
        val okHttpClient = OkHttpClient()
        okHttpClient.newBuilder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit

    }

}