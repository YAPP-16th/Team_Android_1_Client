package com.eroom.erooja.feature.login

import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.Profile
import com.kakao.usermgmt.response.model.UserAccount
import timber.log.Timber

class LoginPresenter(override val view: LoginContract.View) : LoginContract.Presenter {

    override val requestMe =  {
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult) {
                val message = "failed to get user info. msg=$errorResult"
                Timber.d(message)
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                Timber.d("세션 만료.")
            }

            override fun onSuccess(response: MeV2Response) {
                Timber.d("user id : " + response.id)
                val kakaoAccount: UserAccount = response.kakaoAccount
                val profile: Profile? = kakaoAccount.profile
                if(profile != null) {
                    Timber.d("nickname: " + profile.nickname)
                    Timber.d("profile image: " + profile.profileImageUrl)
                    Timber.d("thumbnail image: " + profile.thumbnailImageUrl)
                } else {
                    // 프로필 획득 불가
                }
                logout()
                //if 회원정보 없을 경우
                //redirectSignUpActivity()
            }
        })
    }

    private fun logout() = UserManagement.getInstance()
        .requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                Timber.i("KAKAO_API 로그아웃 완료")
            }
        })
}