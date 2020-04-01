package com.eroom.erooja.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityLoginBinding
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity
import com.eroom.erooja.feature.tab.TabActivity
import com.kakao.auth.ApiResponseCallback
import com.kakao.auth.AuthService
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.auth.network.response.AccessTokenInfoResponse
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.Profile
import com.kakao.usermgmt.response.model.UserAccount
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException
import timber.log.Timber
import java.util.concurrent.Future


class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter

    private lateinit var callback: SessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter =
            LoginPresenter(this)
    }

    private fun setUpDataBinding() {
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginBinding.activity = this
    }

    private fun initView() {
        callback = SessionCallback(requestMe)
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private class SessionCallback(val requestMe: () -> Future<MeV2Response>) : ISessionCallback {
        override fun onSessionOpened() {
            requestMe()
        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            Timber.e("$exception")
        }
    }




    private val requestMe =  {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult) {
                val message = "failed to get user info. msg=$errorResult"
                Timber.d(message)
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                this@LoginActivity.toastLong("세션 만료 다시 로그인 해주세요.")
            }

            override fun onSuccess(response: MeV2Response) {
                Timber.d("user id : " + response.id)
                val kakaoAccount: UserAccount = response.kakaoAccount
                val profile: Profile? = kakaoAccount.profile
                when {
                    profile != null -> {
                        requestToken()
                        Timber.d("nickname: " + profile.nickname)
                        Timber.d("profile image: " + profile.profileImageUrl)
                        Timber.d("thumbnail image: " + profile.thumbnailImageUrl)
                    }
                    kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE -> {
                        // 동의 요청 후 프로필 정보 획득 가능
                    }
                    else -> {
                        // 프로필 획득 불가
                    }
                }
                logout()
                //if 회원정보 없을 경우
                //redirectSignUpActivity()
            }
        })
    }

    fun logout() = UserManagement.getInstance()
        .requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                Timber.i("KAKAO_API 로그아웃 완료")
            }
        })

    fun requestToken() = AuthService.getInstance()
        .requestAccessTokenInfo(object : ApiResponseCallback<AccessTokenInfoResponse?>() {
            override fun onSessionClosed(errorResult: ErrorResult) {
                Timber.e("KAKAO_API", "세션이 닫혀 있음: $errorResult")
            }

            override fun onFailure(errorResult: ErrorResult) {
                Timber.e("KAKAO_API", "토큰 정보 요청 실패: $errorResult")
            }

            override fun onSuccess(result: AccessTokenInfoResponse?) {
                result?.let { this@LoginActivity.toastLong(it.toString()) }
            }
        })

    private val redirectSignUpActivity = {
        val intent = Intent(this, KakaoSignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun kakaoLoginButtonClicked() = loginBinding.comKakaoLogin.performClick()

    fun guestLoginButtonClicked() = startActivity(Intent(this, TabActivity::class.java))
}
