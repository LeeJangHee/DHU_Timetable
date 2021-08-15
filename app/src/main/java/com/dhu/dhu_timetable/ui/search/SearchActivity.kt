package com.dhu.dhu_timetable.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.ActivitySearchBinding
import com.dhu.dhu_timetable.ui.subject.SubjectFragment
import com.dhu.dhu_timetable.util.Conts.CYBER
import com.dhu.dhu_timetable.util.Conts.EMAIL
import com.dhu.dhu_timetable.util.Conts.LEVEL
import com.dhu.dhu_timetable.util.Conts.MAJOR
import com.dhu.dhu_timetable.util.Conts.SUBJECT_NAME
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    private var email: String? = null
    private var subjectname: String? = null
    private var major: String? = null
    private var level: String? = null
    private var cyber: String? = null
    private var etSubjectName: EditText? = null
    private var spnMajor: Spinner? = null
    private var spnLevel: Spinner? = null
    private var cbCyber: CheckBox? = null
    private var mAdView: AdView? = null

    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) { }
        mAdView = binding.searchAdView
        val adRequest = AdRequest.Builder().build()
        mAdView?.loadAd(adRequest)

        // 로그인 정보 유지를 위한 Intent
        email = intent.getStringExtra(EMAIL)
        etSubjectName = binding.editSubject
        spnMajor = binding.spinnerMajor
        spnLevel = binding.spinnerLevel
        cbCyber = binding.checkBoxCyber

        binding.buttonConfirm.setOnClickListener(this)
        binding.buttonReset.setOnClickListener(this)
        binding.buttonCancel.setOnClickListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // 상단바 뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 검색화면 버튼 이벤트
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_confirm -> {
                subjectname = etSubjectName?.text.toString()
                subjectname = subjectname?.let {
                    when {
                        it.isEmpty() -> "%"
                        else -> "%$it%"
                    }
                } ?: "%"

                major = spnMajor?.selectedItem.toString()
                major = major?.let {
                    when {
                        it.isEmpty() -> "%"
                        else -> "%$it%"
                    }
                } ?: "%"

                level = spnLevel?.selectedItem.toString()
                level = level?.let {
                    when {
                        it.length > 1 -> "%${it.substring(0, 1)}%"
                        else -> "%"
                    }
                } ?: "%"

                cyber = if (cbCyber!!.isChecked) "Y" else "%"
                Log.d("button_confirm : ", subjectname + major + level + cyber)

                // SubjectFragment로 데이터 바로 전송 --> 현재 미사용
                SubjectFragment.searchInstance("", "", subjectname, level, major, cyber)
                Intent().apply {
                    putExtra(SUBJECT_NAME, subjectname)
                    putExtra(MAJOR, major)
                    putExtra(LEVEL, level)
                    putExtra(CYBER, cyber)
                    setResult(Activity.RESULT_OK, this)
                }
                finish()
            }
            R.id.button_cancel -> finish()
            R.id.button_reset -> {
                etSubjectName?.setText("")
                spnMajor?.setSelection(0)
                spnLevel?.setSelection(0)
                cbCyber?.isChecked = false
            }
        }
    }
}
