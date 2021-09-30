package com.dhu.dhu_timetable.ui.navitem

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.ActivityBugreportBinding
import com.dhu.dhu_timetable.util.Conts.KAKAO_OPENCHAT
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class BugreportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBugreportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBugreportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this) { }
        val adRequest = AdRequest.Builder().build()
        binding.burgAdView.loadAd(adRequest)

        // 상단 바 뒤로가기 버튼 생성
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.nav_bug_title)
        }

        binding.clickListener = object : BugReportListener {}
    }

    // 상단 바 뒤로가기 버튼 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

interface BugReportListener {
    fun onEmailListener(context: Context) {
        val emailSend = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            val address = arrayOf(context.getString(R.string.nav_bug_email))
            putExtra(Intent.EXTRA_EMAIL, address)
            putExtra(Intent.EXTRA_SUBJECT, "기린바구니 버그리포트 문의")
            putExtra(Intent.EXTRA_TEXT, "문의 사항 : ")
        }
        context.startActivity(emailSend)
    }
    fun onKakaoListener(context: Context) {
        val kakaoSend = Intent(Intent.ACTION_VIEW, Uri.parse(KAKAO_OPENCHAT))
        context.startActivity(kakaoSend)
    }
}