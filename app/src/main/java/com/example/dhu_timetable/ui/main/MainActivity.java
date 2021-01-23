package com.example.dhu_timetable.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dhu_timetable.R;
import com.example.dhu_timetable.ui.login.LoginModel;
import com.example.dhu_timetable.ui.navitem.BugreportActivity;
import com.example.dhu_timetable.ui.navitem.LicenseActivity;
import com.example.dhu_timetable.ui.navitem.NavigationViewModel;
import com.example.dhu_timetable.ui.navitem.notice.NoticeActivity;
import com.example.dhu_timetable.ui.search.SearchActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements OnUpdateListener {
    @StringRes
    private List<Integer> tabTitle = Arrays.asList(R.string.tab_text_1, R.string.tab_text_2);

    // Intent Data
    private String currentYear;
    private String currentMonth;
    private String email;
    private String semester;

    // Navigation Data
    private MaterialToolbar toolbar;
    private NavigationView nav_view;
    private DrawerLayout drawerLayout;

    private NavigationViewModel navigationViewModel;
    private TextView tv_email;
    private TextView tv_name;
    private CircleImageView ig_profile;

    // onBackPressed
    private BackPressedForFinish backPressedForFinish;

    private final int REQUEST_CODE = 100;

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 현재 시간 정보 받아오기
        Intent it = getIntent();
        currentYear = it.getStringExtra("YEAR");
        currentMonth = it.getStringExtra("MONTH");
        email = it.getStringExtra("email");
        Log.d("janghee", "onCreate: "+email);

        // 1~6 = 1학기 / 6~12 = 2학기
        if (0 < Integer.parseInt(currentMonth) && Integer.parseInt(currentMonth) <= 6) {
            semester = "10";
        } else {
            semester = "20";
        }

        // 라이브데이터 초기화
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        searchTimetableApi(email);
        searchSubjectApi("2020", "20", "%", "%", "%", "%");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, tabTitle.size(), currentYear, currentMonth, email, this);
        toolbar = (MaterialToolbar) findViewById(R.id.toolbar);

        // BackPressedForFinish 객체 생성
        backPressedForFinish = new BackPressedForFinish(this);


        // 뷰 스와이프 기능
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        // 탭 제목 설정
                        tab.setText(getResources().getString(tabTitle.get(position)));
                    }
                }).attach();

        // 상단 바 검색버튼 클릭 이벤트
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_search) {
                    Intent it = new Intent(getApplicationContext(), SearchActivity.class);
                    it.putExtra("email", email);
                    startActivityForResult(it, REQUEST_CODE);

                }

                return false;
            }
        });

        // 상단 바 네비게이션 버튼 클릭 이벤트
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        // navigation view --> header view
        // 아이템 설정
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        View header = nav_view.getHeaderView(0);
        tv_name = (TextView) header.findViewById(R.id.navi_name);
        tv_email = (TextView) header.findViewById(R.id.navi_email);
        ig_profile = (CircleImageView) header.findViewById(R.id.navi_profile);

        // 네비게이션 뷰 모델
        navigationViewModel = new ViewModelProvider(this).get(NavigationViewModel.class);
        navigationViewModel.init(email);

        navigationViewModel.getTest().observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                tv_name.setText(loginModel.getName());
                tv_email.setText(loginModel.getEmail());
                Glide.with(MainActivity.this)
                        .load(loginModel.getProfile())
                        .error(R.drawable.app_icon)
                        .circleCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(ig_profile);
            }
        });

        // 네비게이션 뷰 메뉴 클릭 이벤트
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent it;
                switch (item.getItemId()) {
                    case R.id.nav_notice:
                        it = new Intent(MainActivity.this, NoticeActivity.class);
                        startActivity(it);
                        break;
                    case R.id.nav_bugreport:
                        it = new Intent(MainActivity.this, BugreportActivity.class);
                        startActivity(it);
                        break;
                    case R.id.nav_license:
                        it = new Intent(MainActivity.this, LicenseActivity.class);
                        startActivity(it);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void OnUpdateTimetable(String email) {
        mainActivityViewModel.nextInsertTimetable(email);
    }

    // 뒤로가기 버튼 이벤트
    // 네비게이션뷰가 열려 있을 시 네비게이션만 닫게하고 안열려 있을 시에는 앱종료 함수 실행
    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else {
            backPressedForFinish.onBackPressed();
        }
    }

    // Search 데이터
    private String subjectname;
    private String major;
    private String level;
    private String cyber;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("onActivityResult", "onActivityResult");
        if (requestCode == REQUEST_CODE) {
            if (resultCode != RESULT_OK)
                return;

            email = data.getExtras().getString("email");
            subjectname = data.getExtras().getString("subjectname");
            major = data.getExtras().getString("major");
            level = data.getExtras().getString("level");
            cyber = data.getExtras().getString("cyber");
            Log.v("Search_Confirm :", "인텐트 데이터 : " + subjectname + major + level + cyber);

            // 뷰모델과 연결 - 검색된 데이터로 라이브데이터 업데이트 - TEST 객체 (year, semester)
            mainActivityViewModel.setSubjectData("2020", "20", subjectname, level, major, cyber);
        }
    }

    // 4- Calling method in Fragment
    private void searchTimetableApi(String email) {
        mainActivityViewModel.setTimetable(email);
    }

    private void searchSubjectApi(String year, String semester, String subjectname, String major, String level, String cyber) {
        mainActivityViewModel.setSubjectData(year, semester, subjectname, major, level, cyber);
    }

}