package com.eroom.erooja.feature.login

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.auth.PostKakaoLoginUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.ConverterUtil
import com.eroom.domain.utils.addTo
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.Profile
import com.kakao.usermgmt.response.model.UserAccount
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class LoginPresenter(override val view: LoginContract.View,
                     private val postKakaoLoginUseCase: PostKakaoLoginUseCase,
                     private val sharedPrefRepository: SharedPrefRepository
) : LoginContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

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
                var nickname: String? = null
                var thumbnailImage: String? = null
                if(profile != null) {
                    nickname = profile.nickname
                    thumbnailImage = profile.thumbnailImageUrl
                    Timber.d("nickname: " + profile.nickname)
                    Timber.d("profile image: " + profile.profileImageUrl)
                    Timber.d("thumbnail image: " + profile.thumbnailImageUrl)
                }
                checkUserInfo(response.id, nickname, thumbnailImage)
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun checkUserInfo(userId: Long, nickname: String?, thumbnailImage: String?) {
        postKakaoLoginUseCase.postKakaoLogin(userId)
            .subscribe({
                sharedPrefRepository.writePrefs(Consts.ACCESS_TOKEN, ConverterUtil._Encode(it.token))
                sharedPrefRepository.writePrefs(Consts.REFRESH_TOKEN, ConverterUtil._Encode(it.refreshToken))
                logout()
                if (it.isAdditionalInfoNeeded)
                    view.redirectSignUpActivity(nickname)
                else {
                    sharedPrefRepository.writePrefs(Consts.AUTO_LOGIN, true)
                    view.redirectMainActivity()
                }
            },{
                Timber.e(it.localizedMessage)
                logout()
            }) addTo compositeDisposable
    }

    private fun logout() = UserManagement.getInstance()
        .requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                Timber.i("KAKAO_API 로그아웃 완료")
            }
        })

    override fun guestLoginSetting() {
        sharedPrefRepository.writePrefs(Consts.IS_GUEST, true)
        sharedPrefRepository.writePrefs(Consts.AUTO_LOGIN, true)
        sharedPrefRepository.writePrefs(Consts.ACCESS_TOKEN, "")
        sharedPrefRepository.writePrefs(Consts.REFRESH_TOKEN, "")
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}