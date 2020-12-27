package com.example.dhu_timetable.service;

import com.example.dhu_timetable.ui.subject.SubjectModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("subject.php")
    Call<List<SubjectModel>> getSubject();
}
