package com.example.dhu_timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dhu_timetable.ui.main.SectionsPagerAdapter;
import com.example.dhu_timetable.ui.search.SearchActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @StringRes
    private List<Integer> tabTitle = Arrays.asList(R.string.tab_text_1, R.string. tab_text_2);
    AppBarLayout topAppBar;
    MaterialToolbar toolbar;
    MenuItem Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, tabTitle.size());
        topAppBar = findViewById(R.id.topappbar);
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
    }

}