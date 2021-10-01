package com.dhu.dhu_timetable.ui.main

import android.app.Activity
import android.widget.Toast
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.util.Conts.TIME_INTERVAL

class BackPressedForFinish(private val activity: Activity) {
    private var backKeyPressedTime = 0L   // '뒤로' 버튼을 클릭했을 때의 시간

    // 종료할 액티비티에서 호출할 함수
    fun onBackPressed() {
        // '뒤로' 버튼 클릭 시간과 현재 시간을 비교 게산한다.
        // 마지막 '뒤로'버튼 클릭 시간이 이전 '뒤로'버튼 클릭시간과의 차이가 TIME_INTERVAL(여기서는 2초)보다 클 때 true
        if (System.currentTimeMillis() > backKeyPressedTime + TIME_INTERVAL) {
            // 현재 시간을 backKeyPressedTime에 저장한다.
            backKeyPressedTime = System.currentTimeMillis()

            // 종료 안내문구를 노출한다.
            showMessage(true)
        } else {
            // 마지막 '뒤로'버튼 클릭시간이 이전 '뒤로'버튼 클릭시간과의 차이가 TIME_INTERVAL(2초)보다 작을때
            // Toast가 아직 노출중이라면 취소한다.
            showMessage(false)
            // 앱 종료
            activity.finish()
        }
    }

    private fun showMessage(isShow: Boolean) {
        val toast = Toast.makeText(activity, activity.getString(R.string.back_button_message), Toast.LENGTH_SHORT)

        fun show() {
            toast.show()
        }

        fun cancel() {
            toast.cancel()
        }

        if (isShow) show() else cancel()
    }
}