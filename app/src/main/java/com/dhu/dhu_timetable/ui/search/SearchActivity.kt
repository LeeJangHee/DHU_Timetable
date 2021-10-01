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

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
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
            android.R.id.home -> {
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
                subjectname = subjectname.run {
                    when {
                        this.isNullOrEmpty() -> "%"
                        else -> "%$this%"
                    }
                }

                major = spnMajor?.selectedItem.toString()
                major = major.run {
                    when {
                        this.isNullOrEmpty() -> "%"
                        else -> "%$this%"
                    }
                }
                level = spnLevel?.selectedItem.toString()
                level = level.run {
                    when {
                        this.isNullOrEmpty() -> "%"
                        this.length > 1 -> "%${this.substring(0, 1)}%"
                        else -> "%"
                    }
                }

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

                Intent().apply {
                    putExtra(SUBJECT_NAME, "%")
                    putExtra(MAJOR, "%")
                    putExtra(LEVEL, "%")
                    putExtra(CYBER, "%")
                    setResult(Activity.RESULT_OK, this)
                }
                finish()
            }
        }
    }
}
