package com.dhu.dhu_timetable.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginModel(
        var success: String? = null,
        var email: String? = null,
        var name: String? = null,
        var profile: String? = null
)