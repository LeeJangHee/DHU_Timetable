package com.example.dhu_timetable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "janghee";
    private SignInButton signInButton;              // 구글 로그인 버튼
    private FirebaseAuth auth;                      // 파이어베이스 인증 객체
    private static final int RC_SIGN_IN = 9001;     // 구글 로그인 결과 코드

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();
        // 사용자가 현재 로그인되어 있는지 확인
        FirebaseUser currentUser = auth.getCurrentUser();
        // UI Update ...
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        // TODO: 구아로 서비스로 시행될 예정, 학교 이메일로만 로그인 가능,
        // 인증된 사용자를 서버에 저장 --> 유저 데이터 관리
    }

    /**
     * 구글 로그인 버튼 클릭시 발생
     */
    private void googleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * startActivityForResult(...) 실행시 발생되는 함수
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
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

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
                        } else {
                            // Sign in fails
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // ...

                    }
                });
    }

}
