package com.example.dhu_timetable.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhu_timetable.R;
import com.example.dhu_timetable.ui.main.MainActivity;
import com.example.dhu_timetable.ui.subject.SubjectFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private String email;
    private String subjectname;
    private String major;
    private String level;
    private String cyber;

    private EditText et_subjectname;

    private Button btn_confirm;
    private Button btn_reset;
    private Button btn_cancel;

    private Spinner spn_major;
    private Spinner spn_level;

    private CheckBox cb_cyber;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.search_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // 로그인 정보 유지를 위한 Intent
        Intent it = getIntent();
        email = it.getStringExtra("email");

        btn_confirm = findViewById(R.id.button_confirm);
        btn_reset = findViewById(R.id.button_reset);
        btn_cancel = findViewById(R.id.button_cancel);

        et_subjectname = findViewById(R.id.edit_subject);

        spn_major = findViewById(R.id.spinner_major);
        spn_level = findViewById(R.id.spinner_level);
        cb_cyber = findViewById(R.id.checkBox_cyber);

        btn_confirm.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // 상단바 뒤로가기 버튼
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 검색화면 버튼 이벤트
    @Override
    public void onClick(View v) {
        Intent it;
        switch(v.getId()){
            case R.id.button_confirm : // 검색 데이터 변환 -> MainActivity로 전송
                subjectname = String.valueOf(et_subjectname.getText());
                if(subjectname.isEmpty())
                    subjectname = "%";
                else
                    subjectname = "%" + subjectname + "%";

                major = String.valueOf(spn_major.getSelectedItem());
                if(major.isEmpty())
                    major = "%";
                else
                    major = "%" + major + "%";

                level = String.valueOf(spn_level.getSelectedItem());
                if(level.length() > 1)
                    level = level.substring(0, 1);
                else if(level.isEmpty())
                    level = "%";

                if(cb_cyber.isChecked())
                    cyber = "Y";
                else
                    cyber = "%";

                Log.d("button_confirm : ", subjectname + major + level + cyber);

                // SubjectFragment로 데이터 바로 전송 --> 현재 미사용
                SubjectFragment.searchInstance("","",subjectname, level, major, cyber);

                it = new Intent();
                it.putExtra("subjectname", subjectname);
                it.putExtra("major", major);
                it.putExtra("level", level);
                it.putExtra("cyber", cyber);
                setResult(RESULT_OK, it);
                finish();
                break;

            case R.id.button_cancel:
                finish();
                break;

            case R.id.button_reset:
                et_subjectname.setText("");
                spn_major.setSelection(0);
                spn_level.setSelection(0);
                cb_cyber.setChecked(false);
                break;


        }
    }
}
