package com.dhu.dhu_timetable.util

import android.content.Context

object SharedPreferences {
    private val PREF_NAME = SharedPreferences::class.java.name
    private const val PREF_KEY_EMAIL = "email"

    fun setPrefEmail(context: Context, email: String) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(PREF_KEY_EMAIL, email).apply()
    }

    fun getPrefEmail(context: Context): String? {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PREF_KEY_EMAIL, "")
    }
}