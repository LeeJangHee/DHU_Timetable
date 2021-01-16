package com.example.dhu_timetable.ui.timetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimetableModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("workDay")
    @Expose
    private String workDay;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

}
