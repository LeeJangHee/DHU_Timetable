package com.example.dhu_timetable.ui.navitem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhu_timetable.R;

public class BugreportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugreport);

        LinearLayout layout_mail = findViewById(R.id.layout_mail);
        LinearLayout layout_kakao = findViewById(R.id.layout_kakao);

        // 상단 바 뒤로가기 버튼 생성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("기린바구니 버그리포트");

        // 버그리포트 문의하기 버튼 이벤트
        layout_mail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent email_send = new Intent(Intent.ACTION_SEND);
                email_send.setType("plain/text");
                String[] address = {"wkdgml96@dhu.ac.kr"};
                email_send.putExtra(Intent.EXTRA_EMAIL, address);
                email_send.putExtra(Intent.EXTRA_SUBJECT, "기린바구니 버그리포트 문의");
                email_send.putExtra(Intent.EXTRA_TEXT, "문의 사항 : ");
                startActivity(email_send);
            }
        });

        // 카카오톡 오픈채팅 문의하기 버튼 이벤트
        String kakao_openchat = "https://open.kakao.com/o/sNAUDYSc";
        layout_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kakao_send = new Intent(Intent.ACTION_VIEW, Uri.parse(kakao_openchat));
                startActivity(kakao_send);
            }
        });


    }

    // 상단 바 뒤로가기 버튼 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
