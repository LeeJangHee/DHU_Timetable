package com.example.dhu_timetable.service;

import com.example.dhu_timetable.ui.login.LoginModel;
import com.example.dhu_timetable.ui.navitem.NavigationModel;
import com.example.dhu_timetable.ui.navitem.notice.NoticeModel;
import com.example.dhu_timetable.ui.subject.SubjectModel;
import com.example.dhu_timetable.ui.timetable.TimetableModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("subject.php")
    Call<List<SubjectModel>> test();

    @GET("subject{year}{semester}.php")
    Call<List<SubjectModel>> getSubject(@Path("year") String year,
                                        @Path("semester") String semester);

    @FormUrlEncoded
    @POST("users.php")
    Call<LoginModel> getUser(@Field("email") String email);

    @FormUrlEncoded
    @POST("insert_user.php")
    Call<LoginModel> checkUser(@Field("email") String email,
                               @Field("name") String name,
                               @Field("profile") String profile);

    @FormUrlEncoded
    @POST("insert_timetable.php")
    Call<TimetableModel> setTimetable(@Field("email") String email,
                                      @Field("subjectName") String subjectName,
                                      @Field("workDay") String workDay,
                                      @Field("cyberCheck") String cyberCheck,
                                      @Field("quarterCheck") String quarterCheck);

    @FormUrlEncoded
    @POST("timetable.php")
    Call<List<TimetableModel>> checkTimetable(@Field("email") String email);

    @GET("search.php")
    Call<List<SubjectModel>> getSearch(@Query("year") String year,
                                       @Query("semester") String semester,
                                       @Query("name") String name,
                                       @Query("level") String level,
                                       @Query("major") String major,
                                       @Query("cyber") String cyber);

    @GET("notice.php")
    Call<List<NoticeModel>> getNotice();
}
