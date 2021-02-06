package com.dhu.dhu_timetable.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dhu.dhu_timetable.R;
import com.dhu.dhu_timetable.repo.UsersRepo;
import com.dhu.dhu_timetable.ui.main.BackPressedForFinish;
import com.dhu.dhu_timetable.ui.main.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "janghee";
    private static final int RC_SIGN_IN = 9001;             // 구글 로그인 결과 코드
    private static final String DHU_EMAIL = "dhu.ac.kr";    // 한의대 이메일

    private SignInButton signInButton;              // 구글 로그인 버튼
    private FirebaseAuth auth;                      // 파이어베이스 인증 객체

    private GoogleSignInClient googleSignInClient;
    private BackPressedForFinish backPressedForFinish;

    @Override
    protected void onStart() {
        super.onStart();
        // 사용자가 현재 로그인되어 있는지 확인
        FirebaseUser currentUser = auth.getCurrentUser();
        // 현재 로그인 되어 있다면
        if (currentUser != null) {
            if (currentUser.getEmail().contains(DHU_EMAIL)) {
                googleSignIn();
            } else {
                googleSignOut();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backPressedForFinish = new BackPressedForFinish(this);

        // 로그인 버튼이 눌릴 때 --> 기본 옵션 정의
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // 파이어베이스 인증 객체 초기화 ( 싱글톤 패턴 )
        auth = FirebaseAuth.getInstance();

        signInButton = (SignInButton) findViewById(R.id.login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    /**
     * 구글 로그인 버튼 클릭시 발생
     */
    private void googleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * 구글 로그아웃 & 인증 제거
     */
    private void googleSignOut() {
        // Firebase sign out
        auth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(LoginActivity.this, "한의대 이메일로 로그인 해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
        // Google revoke access
        googleSignInClient.revokeAccess();
    }

    /**
     * startActivityForResult(...) 실행시 발생되는 함수
     *
     * @param requestCode = 요청받은 코드번호
     * @param resultCode  = ??
     * @param data        = 인텐트 데이터
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 구글 로그인 성공 --> 파이어베이스로 인증
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getEmail());
                // 이메일 분리
                if (account.getEmail().contains(DHU_EMAIL)) {
                    // 한의대 이메일 = true
                    firebaseAuthWithGoogle(account.getIdToken());

                } else {
                    // 한의대 이메일 = false
                    googleSignOut();
                }

            } catch (ApiException e) {
                // 구글 로그인 실패 --> 실패 UI 적용
                Log.w(TAG, "Google sign in failed" + e.getMessage(), e);
                // ...
            }
        }
    }

    /**
     * 파이어베이스 인증과 구글 연동
     *
     * @param idToken = 구글 API 토큰 값
     */
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();

                            Toast.makeText(LoginActivity.this, "로그인 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            nextActivity(user.getEmail(), user.getDisplayName(), String.valueOf(user.getPhotoUrl()));

                        } else {
                            // Sign in fails
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    /**
     * 로그인 성공 --> 다음 액티비티
     * 유저 정보 서버 저장
     *
     * @param email   = 유저 이메일
     * @param name    = 유저 이름
     * @param profile = 유저 프로필
     */
    private void nextActivity(String email, String name, String profile) {
        String[] date = currentDate();
        UsersRepo usersRepo = UsersRepo.getInstance();
        usersRepo.setUser(email, name, profile);
        Intent it = new Intent(LoginActivity.this, MainActivity.class);
        it.putExtra("YEAR", date[0]);
        it.putExtra("MONTH", date[1]);
        it.putExtra("email", email);
        startActivity(it);
        finish();
    }

    /**
     * 현재 날짜 정보 저장 --> 초기 리스트 불러올때 사용
     *
     * @return = {year, month}
     */
    private String[] currentDate() {
        Calendar date = Calendar.getInstance();

        String year = String.valueOf(date.get(date.YEAR));
        String month = String.valueOf(date.get(date.MONTH) + 1);
        String[] dateArray = {year, month};
        return dateArray;
    }

    @Override
    public void onBackPressed() {
        backPressedForFinish.onBackPressed();
    }
}
