package com.example.dhu_timetable.ui.navitem.notice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeModel {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("date")
    @Expose
    public String date;


    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}


