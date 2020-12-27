package com.example.dhu_timetable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class LoadingActivity extends AppCompatActivity {
    // 로딩 시간 4초
    private final int LOADING_TIME = 4000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);

        // 로딩 딜레이
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        }, LOADING_TIME);
    }
}
