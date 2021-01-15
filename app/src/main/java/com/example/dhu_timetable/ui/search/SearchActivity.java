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
import com.example.dhu_timetable.ui.navitem.notice.NoticeActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private String email;
    private String subjectname;
    private String major;
    private String day;
    private String cyber;

    private EditText et_subjectname;

    private Button btn_confirm;
    private Button btn_reset;
    private Button btn_cancel;

    private Spinner spn_major;
    private Spinner spn_day;

    private CheckBox cb_cyber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 로그인 정보 유지를 위한 Intent
        Intent it = getIntent();
        email = it.getStringExtra("email");

        btn_confirm = findViewById(R.id.button_confirm);
        btn_reset = findViewById(R.id.button_reset);
        btn_cancel = findViewById(R.id.button_cancel);

        et_subjectname = findViewById(R.id.edit_subject);

        spn_major = findViewById(R.id.spinner_major);
        spn_day = findViewById(R.id.spinner_day);
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
            case R.id.button_confirm :
                subjectname = String.valueOf(et_subjectname.getText());
                major = String.valueOf(spn_major.getSelectedItem());
                day = String.valueOf(spn_day.getSelectedItem());
                day = day.substring(0, 1);
                if(cb_cyber.isChecked())
                    cyber = "Y";
                else
                    cyber = "";

                Log.d("button_confirm : ", subjectname + major + day + cyber);

                it = new Intent(SearchActivity.this, MainActivity.class);
                it.putExtra("subjectname", subjectname);
                it.putExtra("major", major);
                it.putExtra("day", day);
                it.putExtra("cyber", cyber);
                ((MainActivity)MainActivity.mContext).Search_Confirm();
                finish();
                break;

            case R.id.button_cancel:
                finish();
                break;

            case R.id.button_reset:
                it = new Intent(SearchActivity.this, MainActivity.class);
                it.putExtra("email",email);
                startActivity(it);
                finish();
                break;


        }
    }
}
