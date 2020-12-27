package com.example.dhu_timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity {

    private SignInButton signInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = (SignInButton) findViewById(R.id.login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        });

        //TODO: 구아로 서비스로 시행될 예정, 학교 이메일로만 로그인 가능

    }
}
