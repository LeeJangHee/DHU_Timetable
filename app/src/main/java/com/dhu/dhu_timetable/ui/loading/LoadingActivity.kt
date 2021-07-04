package com.dhu.dhu_timetable.ui.loading

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.ActivityLoadingBinding
import com.dhu.dhu_timetable.ui.login.LoginActivity
import com.dhu.dhu_timetable.util.Conts.APP_MARKET_URL
import com.dhu.dhu_timetable.util.Conts.LOADING_TIME
import com.dhu.dhu_timetable.util.Conts.NEW_APP_VERSION
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class LoadingActivity: AppCompatActivity() {

    private var _binding: ActivityLoadingBinding? = null
    private val binding get() = _binding!!

    private var newAppVersion: Long = 0
    private val mFirebaseRemoteConfig: FirebaseRemoteConfig by lazy { FirebaseRemoteConfig.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animationView = findViewById<LottieAnimationView>(R.id.animation_view)

        val configSettings: FirebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
                .build()
        mFirebaseRemoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(R.xml.remote_config_defaults)
            fetchAndActivate().addOnCompleteListener { task ->
                checkVersion(task.isSuccessful)
            }
        }
    }

    private fun checkVersion(successful: Boolean) {
        // 서버 연결
        if (successful) {
            newAppVersion = mFirebaseRemoteConfig.getLong(NEW_APP_VERSION)
            Log.d("test", "checkVersion: $newAppVersion")

            try {
                val pi: PackageInfo = packageManager.getPackageInfo(packageName, 0)
                val appVersion: Long = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    pi.longVersionCode
                } else {
                    pi.versionCode.toLong()
                }

                // 새로운 버전과 현재 버전 비교 -> 업데이트
                if (newAppVersion > appVersion) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.apply {
                        setTitle(getString(R.string.loading_update_title))
                        setMessage(getString(R.string.loading_update_content))
                        setPositiveButton("업데이트") { dialog, which ->
                            val intent: Intent = Intent(Intent.ACTION_VIEW)
                            intent.setData(Uri.parse(APP_MARKET_URL))
                            startActivity(intent)
                            dialog.cancel()
                            finish()
                        }
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.show()
                    alertDialog.setOnDismissListener { onBackPressed() }
                } else {
                    // 로딩 딜레이
                    Handler().postDelayed(Runnable {
                        val intent: Intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, LOADING_TIME.toLong())
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}