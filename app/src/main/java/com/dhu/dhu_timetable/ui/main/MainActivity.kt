package com.dhu.dhu_timetable.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.ActivityMainBinding
import com.dhu.dhu_timetable.databinding.NavHeaderMainBinding
import com.dhu.dhu_timetable.ui.login.LoginActivity
import com.dhu.dhu_timetable.ui.navitem.BugreportActivity
import com.dhu.dhu_timetable.ui.navitem.LicenseActivity
import com.dhu.dhu_timetable.ui.navitem.NavigationViewModel
import com.dhu.dhu_timetable.ui.navitem.notice.NoticeActivity
import com.dhu.dhu_timetable.ui.search.SearchActivity
import com.dhu.dhu_timetable.util.Conts.CYBER
import com.dhu.dhu_timetable.util.Conts.EMAIL
import com.dhu.dhu_timetable.util.Conts.LEVEL
import com.dhu.dhu_timetable.util.Conts.MAJOR
import com.dhu.dhu_timetable.util.Conts.MONTH
import com.dhu.dhu_timetable.util.Conts.REQUEST_CODE
import com.dhu.dhu_timetable.util.Conts.SEMESTER_FIRST
import com.dhu.dhu_timetable.util.Conts.SEMESTER_LAST
import com.dhu.dhu_timetable.util.Conts.SUBJECT_NAME
import com.dhu.dhu_timetable.util.Conts.YEAR
import com.dhu.dhu_timetable.util.SharedPreferences
import com.dhu.dhu_timetable.util.getCurrentMonth
import com.dhu.dhu_timetable.util.getCurrentYear
import com.dhu.dhu_timetable.util.setImageUrl
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), OnUpdateListener {
    private lateinit var binding: ActivityMainBinding
    private val navHeadBinding: NavHeaderMainBinding by lazy {
        NavHeaderMainBinding.inflate(LayoutInflater.from(this), binding.navView, true)
    }

    // Intent Data
    private lateinit var email: String
    private lateinit var currentYear: String
    private lateinit var currentMonth: String
    private lateinit var semester: String

    @StringRes
    private val tabTitle = listOf(R.string.tab_text_1, R.string.tab_text_2)
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()
    private val backPressedForFinish: BackPressedForFinish by lazy { BackPressedForFinish(this) }
    private val sectionsPagerAdapter: SectionsPagerAdapter by lazy {
        SectionsPagerAdapter(this, tabTitle.size, currentYear, currentMonth, email, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdMob()
        getCurrentTimeInfo()

        semester = currentMonth.let {
            if (it.toInt() in 1..6) {
                SEMESTER_FIRST
            } else {
                SEMESTER_LAST
            }
        }

        // 라이브데이터 초기화
        searchTimetableApi(email)
        searchSubjectApi(currentYear, semester)

        // 탭 레이아웃 설정
        setTabLayout()

        // 상단 바 검색버튼 클릭 이벤트
        binding.include.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_search -> {
                    Intent(applicationContext, SearchActivity()::class.java).apply {
                        putExtra(EMAIL, email)
                        startActivityForResult(this, REQUEST_CODE)
                    }
                }
            }
            false
        }

        // 상단 바 네비게이션 버튼 클릭 이벤트
        binding.include.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        // navigation view --> header view
        // 아이템 설정

        // 네비게이션 뷰 모델
        navigationViewModel.initLoginData(email)

        navigationViewModel.loginModel?.observe(this) {
            navHeadBinding.loginData = it
        }

        // 네비게이션 뷰 메뉴 클릭 이벤트
        binding.navView.setNavigationItemSelectedListener {
            val intent = when (it.itemId) {
                R.id.nav_notice -> Intent(this@MainActivity, NoticeActivity()::class.java)
                R.id.nav_bugreport -> Intent(this@MainActivity, BugreportActivity()::class.java)
                else -> Intent(this@MainActivity, LicenseActivity()::class.java)
            }
            startActivity(intent)
            false
        }

        // 네비게이션 로그아웃 버튼 이벤트
        navHeadBinding.layoutLogout.setOnClickListener {
            navLogout()
        }

    }

    private fun setTabLayout() {
        binding.include.viewPager.apply {
            adapter = sectionsPagerAdapter
        }
        TabLayoutMediator(binding.include.tabs, binding.include.viewPager) { tab, pos ->
            tab.text = resources.getString(tabTitle[pos])
        }.attach()
    }

    private fun setAdMob() {
        // 구글 광고
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.include.mainAdView.loadAd(adRequest)
    }

    private fun getCurrentTimeInfo() {
        intent.apply {
            currentYear = getStringExtra(YEAR).getCurrentYear()
            currentMonth = getStringExtra(MONTH).getCurrentMonth()
            email = getStringExtra(EMAIL) ?: ""
        }
        Log.d("janghee", "getCurrentTimeInfo: $email")
    }

    private fun navLogout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSigneInClient = GoogleSignIn.getClient(this, gso)

        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
            googleSigneInClient.signOut().addOnCompleteListener {
                SharedPreferences.setPrefEmail(this@MainActivity, "")
                showToast(getString(R.string.logout_message))
                startActivity(Intent(this@MainActivity, LoginActivity()::class.java))
                finish()
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun searchTimetableApi(email: String) {
        mainActivityViewModel.setTimetable(email)
    }

    private fun searchSubjectApi(year: String, semester: String, subjectName: String? = "%", major: String? = "%", level: String? = "%", cyber: String? = "%"
    ) {
        mainActivityViewModel.setSubjectData(year, semester, subjectName ?: "%", major ?: "%", level ?: "%", cyber ?: "%")
    }

    override fun OnUpdateTimetable(email: String?) {
        mainActivityViewModel.nextInsertTimetable(email!!)
    }

    // 뒤로가기 버튼 이벤트
    // 네비게이션뷰가 열려 있을 시 네비게이션만 닫게하고 안열려 있을 시에는 앱종료 함수 실행
    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            backPressedForFinish.onBackPressed()
        }
    }

    // Search 데이터
    private var subjectName: String? = null
    private var major: String? = null
    private var level: String? = null
    private var cyber: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("onActivityResult", "onActivityResult")
        if (requestCode == REQUEST_CODE) {
            if (resultCode != RESULT_OK) return

            subjectName = data?.extras?.getString(SUBJECT_NAME)
            major = data?.extras?.getString(MAJOR)
            level = data?.extras?.getString(LEVEL)
            cyber = data?.extras?.getString(CYBER)
            Log.v("Search_Confirm :", "인텐트 데이터 : $subjectName, $major, $level, $cyber")

            // 뷰모델과 연결 - 검색된 데이터로 라이브데이터 업데이트 - TEST 객체 (year, semester)
            searchSubjectApi(currentYear, semester, subjectName, level, major, cyber)
        }
    }
}