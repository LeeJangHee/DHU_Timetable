package com.dhu.dhu_timetable.network

import com.dhu.dhu_timetable.model.LoginModel
import com.dhu.dhu_timetable.model.NoticeModel
import com.dhu.dhu_timetable.model.SubjectModel
import com.dhu.dhu_timetable.model.TimetableModel
import retrofit2.Call
import retrofit2.http.*


interface APIService {
    @FormUrlEncoded
    @POST("users.php")
    fun getUser(@Field("email") email: String): Call<LoginModel>

    @FormUrlEncoded
    @POST("insert_user.php")
    fun checkUser(
            @Field("email") email: String,
            @Field("name") name: String,
            @Field("profile") profile: String
    ): Call<LoginModel>

    @FormUrlEncoded
    @POST("insert_timetable.php")
    fun setTimetable(
            @Field("email") email: String,
            @Field("subjectName") subjectName: String,
            @Field("workDay") workDay: String,
            @Field("cyberCheck") cyberCheck: String,
            @Field("quarterCheck") quarterCheck: String
    ): Call<List<TimetableModel>>

    @FormUrlEncoded
    @POST("timetable.php")
    fun checkTimetable(@Field("email") email: String): Call<List<TimetableModel>>

    @GET("search.php")
    fun getSearch(
            @Query("year") year: String,
            @Query("semester") semester: String,
            @Query("name") name: String,
            @Query("level") level: String,
            @Query("major") major: String,
            @Query("cyber") cyber: String
    ): Call<List<SubjectModel>>

    @FormUrlEncoded
    @POST("delete_timetable.php")
    fun deleteTimetable(
            @Field("email") email: String,
            @Field("id") id: Int
    ): Call<List<TimetableModel>>

    @GET("notice.php")
    fun getNotice(): Call<List<NoticeModel>>
}