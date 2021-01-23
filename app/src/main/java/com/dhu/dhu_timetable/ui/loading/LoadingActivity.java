package com.dhu.dhu_timetable.ui.loading;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.dhu.dhu_timetable.R;
import com.dhu.dhu_timetable.ui.login.LoginActivity;

public class LoadingActivity extends AppCompatActivity {
    // 로딩 시간 3초
    private final int LOADING_TIME = 3000;


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
