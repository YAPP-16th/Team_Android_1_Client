package com.eroom.erooja.feature.signup.kakao

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eroom.domain.utils.toastLong
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.Profile
import com.kakao.usermgmt.response.model.UserAccount
import com.kakao.util.OptionalBoolean
import timber.log.Timber


class KakaoSignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_sign_up)

        requestMe()
    }

    private fun requestMe() {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult) {
                val message = "failed to get user info. msg=$errorResult"
                Timber.d(message)
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                redirectLoginActivity()
            }

            override fun onSuccess(response: MeV2Response) {
                Timber.d("user id : " + response.id)
                val kakaoAccount: UserAccount = response.kakaoAccount
                val email = kakaoAccount.email
                when {
                    email != null -> {
                        Timber.d("email: $email")
                    }
                    kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE -> {
                        // 동의 요청 후 이메일 획득 가능
                        // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.
                    }
                    else -> {
                        // 이메일 획득 불가
                    }
                }
                val profile: Profile? = kakaoAccount.profile
                when {
                    profile != null -> {
                        this@KakaoSignUpActivity.toastLong(profile.nickname)
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
                redirectMainActivity()
            }
        })
    }

    private fun redirectLoginActivity() {

    }

    private fun redirectMainActivity() {

    }
}
