package com.dhu.dhu_timetable.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.ActivityLoginBinding
import com.dhu.dhu_timetable.repo.UsersRepo.Companion.getInstance
import com.dhu.dhu_timetable.ui.main.BackPressedForFinish
import com.dhu.dhu_timetable.ui.main.MainActivity
import com.dhu.dhu_timetable.util.Conts.DHU_EMAIL
import com.dhu.dhu_timetable.util.Conts.EMAIL
import com.dhu.dhu_timetable.util.Conts.MONTH
import com.dhu.dhu_timetable.util.Conts.RC_SIGN_IN
import com.dhu.dhu_timetable.util.Conts.YEAR
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*


class LoginActivity : AppCompatActivity() {
    private val TAG = "janghee"

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val backPressedForFinish: BackPressedForFinish by lazy { BackPressedForFinish(this) }

    private lateinit var googleSignInClient: GoogleSignInClient

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        closeProgressBar()

        // 로그인 버튼 클릭 -> 기본 옵션 정의
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // google login button
        binding.login.setOnClickListener { googleSignIn() }
    }

    override fun onStart() {
        super.onStart()

        // 사용자가 현재 로그인되어 있는지 확인
        val currentUser = auth.currentUser
        // 현재 로그인 되어 있다면
        if (currentUser != null) {
            if (currentUser.email!!.contains(DHU_EMAIL)) {
                googleSignIn()
            } else {
                googleSignOut()
            }
        }
    }

    /**
     * 구글 로그인 버튼 클릭시 발생
     */
    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        binding.isLogin = true
    }

    /**
     * 구글 로그아웃 & 인증 제거
     */
    private fun googleSignOut() {
        closeProgressBar()
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener {
            showToast("한의대 이메일로 로그인 해주세요.")
        }
        // Google revoke access
        googleSignInClient.revokeAccess()
    }

    /**
     * startActivityForResult(...) 실행시 발생되는 함수
     *
     * @param requestCode = 요청받은 코드번호
     * @param resultCode  = ??
     * @param data        = 인텐트 데이터
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // 구글 로그인 성공 -> 파이어베이스로 인증
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getEmail());
                // 이메일 분리
                account.email?.let {
                    if (it.contains(DHU_EMAIL)) {
                        // 한의대 이메일 true
                        firebaseAuthWithGoogle(account.idToken.toString())
                    } else {
                        // 한의대 이메일 false
                        googleSignOut()
                    }
                }
            } catch (e: ApiException) {
                // 구글 로그인 실패
                Log.w(TAG, "Google sign in failed" + e.message, e)
                closeProgressBar()
            }
        }
    }

    /**
     * 파이어베이스 인증과 구글 연동
     *
     * @param idToken = 구글 API 토큰 값
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        Log.d(TAG, "firebaseAuthWithGoogle: success")
                        val user: FirebaseUser = auth.currentUser!!

                        showToast("로그인 성공하였습니다.")
                        nextActivity(user.email!!, user.displayName!!, user.photoUrl.toString())

                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        closeProgressBar();
                    }
                }
    }

    /**
     * 로그인 성공 --> 다음 액티비티
     * 유저 정보 서버 저장
     *
     * @param email   = 유저 이메일
     * @param name    = 유저 이름
     * @param profile = 유저 프로필
     */
    private fun nextActivity(email: String, name: String, profile: String) {
        val date: Array<String> = currentDate()
        val usersRepo = getInstance()
        usersRepo.setUser(email, name, profile)
        val it = Intent(this, MainActivity::class.java)
        it.putExtra(YEAR, date[0])
        it.putExtra(MONTH, date[1])
        it.putExtra(EMAIL, email)
        startActivity(it)
        finish()
    }

    /**
     * 현재 날짜 정보 저장 --> 초기 리스트 불러올때 사용
     *
     * @return = {year, month}
     */
    private fun currentDate(): Array<String> {
        val date: Calendar = Calendar.getInstance()
        val year: String = date.get(Calendar.YEAR).toString()
        val month: String = date.get(Calendar.MONTH + 1).toString()
        return arrayOf(year, month)
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun closeProgressBar() {
        binding.isLogin = false
    }

    override fun onBackPressed() {
        backPressedForFinish.onBackPressed()
    }
}