package com.dhu.dhu_timetable.ui.navitem.notice;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dhu.dhu_timetable.R;
import com.dhu.dhu_timetable.model.NoticeModel;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private static final String TAG = "jaemin";

    private List<NoticeModel> noticeList = new ArrayList<>();
    private NoticeAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private NoticeViewModel noticeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.nav_notice_title));

        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        noticeApi();

        recyclerView = findViewById(R.id.notice_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoticeAdapter(this, noticeViewModel);
        recyclerView.setAdapter(adapter);



        noticeViewModel.getNoticeData().observe(this, new Observer<List<NoticeModel>>() {
            @Override
            public void onChanged(List<NoticeModel> noticeModels) {
                if(noticeList != null){
                    noticeList = noticeModels;
                    adapter.setNoticeList(noticeModels);
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void noticeApi() {
        noticeViewModel.setNoticeData();
    }


}
