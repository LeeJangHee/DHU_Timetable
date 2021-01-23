package com.dhu.dhu_timetable.ui.subject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectModel {

    @SerializedName("year")
    @Expose
    public Integer year;
    @SerializedName("semester")
    @Expose
    public Integer semester;
    @SerializedName("subjectCode")
    @Expose
    public String subjectCode;
    @SerializedName("subjectName")
    @Expose
    public String subjectName;
    @SerializedName("devideClass")
    @Expose
    public Integer devideClass;
    @SerializedName("acaName")
    @Expose
    public String acaName;
    @SerializedName("classCode")
    @Expose
    public Integer classCode;
    @SerializedName("className")
    @Expose
    public String className;
    @SerializedName("majorName")
    @Expose
    public String majorName;
    @SerializedName("level")
    @Expose
    public Integer level;
    @SerializedName("subjectCheck")
    @Expose
    public String subjectCheck;
    @SerializedName("processCheck")
    @Expose
    public String processCheck;
    @SerializedName("finishCheck")
    @Expose
    public String finishCheck;
    @SerializedName("score")
    @Expose
    public Integer score;
    @SerializedName("notAct")
    @Expose
    public Integer notAct;
    @SerializedName("Act")
    @Expose
    public Integer act;
    @SerializedName("professor")
    @Expose
    public String professor;
    @SerializedName("workDay")
    @Expose
    public String workDay;
    @SerializedName("publishDay")
    @Expose
    public String publishDay;
    @SerializedName("classroom")
    @Expose
    public String classroom;
    @SerializedName("cyberCheck")
    @Expose
    public String cyberCheck;
    @SerializedName("quarterCheck")
    @Expose
    public String quarterCheck;
    @SerializedName("bigo")
    @Expose
    public String bigo;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getDevideClass() {
        return devideClass;
    }

    public void setDevideClass(Integer devideClass) {
        this.devideClass = devideClass;
    }

    public String getAcaName() {
        return acaName;
    }

    public void setAcaName(String acaName) {
        this.acaName = acaName;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getSubjectCheck() {
        return subjectCheck;
    }

    public void setSubjectCheck(String subjectCheck) {
        this.subjectCheck = subjectCheck;
    }

    public String getProcessCheck() {
        return processCheck;
    }

    public void setProcessCheck(String processCheck) {
        this.processCheck = processCheck;
    }

    public String getFinishCheck() {
        return finishCheck;
    }

    public void setFinishCheck(String finishCheck) {
        this.finishCheck = finishCheck;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getNotAct() {
        return notAct;
    }

    public void setNotAct(Integer notAct) {
        this.notAct = notAct;
    }

    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getPublishDay() {
        return publishDay;
    }

    public void setPublishDay(String publishDay) {
        this.publishDay = publishDay;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getCyberCheck() {
        return cyberCheck;
    }

    public void setCyberCheck(String cyberCheck) {
        this.cyberCheck = cyberCheck;
    }

    public String getQuarterCheck() {
        return quarterCheck;
    }

    public void setQuarterCheck(String quarterCheck) {
        this.quarterCheck = quarterCheck;
    }

    public String getBigo() {
        return bigo;
    }

    public void setBigo(String bigo) {
        this.bigo = bigo;
    }
}
