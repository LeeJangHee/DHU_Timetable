package com.dhu.dhu_timetable.model

data class SubjectModel(
        var year: Int? = null,
        var semester: Int? = null,
        var subjectCode: String? = null,
        var subjectName: String? = null,
        var devideClass: Int? = null,
        var acaName: String? = null,
        var classCode: Int? = null,
        var className: String? = null,
        var majorName: String? = null,
        var level: Int? = null,
        var subjectCheck: String? = null,
        var processCheck: String? = null,
        var finishCheck: String? = null,
        var score: Int? = null,
        var notAct: Int? = null,
        var act: Int? = null,
        var professor: String? = null,
        var workDay: String? = null,
        var publishDay: String? = null,
        var classroom: String? = null,
        var cyberCheck: String? = null,
        var quarterCheck: String? = null,
        var bigo: String? = null
) {
    fun getStringSemester(): String {
        if (semester != null) {
            return "${semester!! / 10}학기"
        }
        return ""
    }

    fun getStringScore(): String {
        if (score != null) {
            return "${score}학점"
        }
        return ""
    }

    fun getReplacePublishDay(): String {
        if (!publishDay.isNullOrEmpty()) {
            return publishDay!!.replace(" ", "")
        }
        return ""
    }

    fun getReplaceProfessor(): String {
        return professor ?: "미정"
    }

    fun getStringClassroom(): String {
        return classroom?.let { "강의실: $classroom" } ?: "강의실: "
    }

    fun getStringLevel(): String {
        return level?.let { "학년: ${level}학년" } ?: "학년"
    }

    fun getStringFinishCheck(): String {
        return finishCheck?.let { "이수구분: $finishCheck" } ?: "이수구분: "
    }
}
