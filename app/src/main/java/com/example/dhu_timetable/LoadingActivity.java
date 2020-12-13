package com.example.dhu_timetable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {
    private int LOADING_TIME = 3000;  // 로딩 시간 = 3초
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            Intent it = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(it);
            finish();
            }
        }, LOADING_TIME);

        //TODO: 로딩시 서버 전처리 필요

    }
}
