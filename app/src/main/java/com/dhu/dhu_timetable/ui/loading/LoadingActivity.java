package com.dhu.dhu_timetable.ui.loading;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.dhu.dhu_timetable.R;
import com.dhu.dhu_timetable.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import static com.dhu.dhu_timetable.util.Conts.*;

public class LoadingActivity extends AppCompatActivity {
    // 로딩 시간 2.5초
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private long newAppVersion = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);

        // 파이어베이스 버전관리
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        checkVersion(task.isSuccessful());
                    }
                });

    }

    private void checkVersion(boolean successful) {
        // 서버 연결 확인
        if (successful) {
            newAppVersion = mFirebaseRemoteConfig.getLong(NEW_APP_VERSION);
            Log.d("test", "checkVersion: "+newAppVersion);

            try {
                PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
                long appVersion;
                // build.gradle의 현재 앱 버전 가져오기
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    appVersion = pi.getLongVersionCode();
                } else {
                    appVersion = pi.versionCode;
                }

                // 새로운 버전과 현재 버전 비교 하여 업데이트
                if (newAppVersion > appVersion) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.loading_update_title));
                    builder.setMessage(getString(R.string.loading_update_content))
                            .setPositiveButton("업데이트", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent it = new Intent(Intent.ACTION_VIEW);
                                    it.setData(Uri.parse(APP_MARKET_URL));
                                    startActivity(it);
                                    dialog.cancel();
                                    finish();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            onBackPressed();
                        }
                    });
                } else {
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
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {    // 서버 연결이 실패했을 때
            Toast.makeText(this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
