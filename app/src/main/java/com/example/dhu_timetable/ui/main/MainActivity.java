package com.example.dhu_timetable.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dhu_timetable.R;
import com.example.dhu_timetable.ui.login.LoginModel;
import com.example.dhu_timetable.ui.navitem.NavigationViewModel;
import com.example.dhu_timetable.ui.search.SearchActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @StringRes
    private List<Integer> tabTitle = Arrays.asList(R.string.tab_text_1, R.string. tab_text_2);

    // Intent Data
    private String currentYear;
    private String currentMonth;
    private String email;

    // Navigation Data
    private MaterialToolbar toolbar;
    private NavigationView nav_view;
    private DrawerLayout drawerLayout;

    private NavigationViewModel navigationViewModel;
    private TextView tv_email;
    private TextView tv_name;
    private ImageView ig_prifile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 현재 시간 정보 받아오기
        Intent it = getIntent();
        currentYear = it.getStringExtra("YEAR");
        currentMonth = it.getStringExtra("MONTH");
        email = it.getStringExtra("email");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, tabTitle.size(), currentYear, currentMonth);
        toolbar = (MaterialToolbar)findViewById(R.id.toolbar);


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

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.item_search){
                    Intent it = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(it);
                }

                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        // navigation view --> header view
        // 아이템 설정
        nav_view = (NavigationView)findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        View header = nav_view.getHeaderView(0);
        tv_name = (TextView) header.findViewById(R.id.navi_name);
        tv_email = (TextView) header.findViewById(R.id.navi_email);
        ig_prifile = (ImageView) header.findViewById(R.id.navi_profile);

        // 네비게이션 뷰 모델
        navigationViewModel = new ViewModelProvider(this).get(NavigationViewModel.class);
        navigationViewModel.init(email);

        navigationViewModel.getTest().observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                tv_name.setText(loginModel.getName());
                tv_email.setText(loginModel.getEmail());
            }
        });


    }


}