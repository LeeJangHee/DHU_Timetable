package com.dhu.dhu_timetable.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnect {
    private static String baseURL = "http://wkdgml96.iptime.org:8080/timetable/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
