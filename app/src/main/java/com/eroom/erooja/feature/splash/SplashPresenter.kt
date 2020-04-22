package com.eroom.erooja.feature.splash

import android.os.Handler
import com.eroom.domain.api.usecase.PostRefreshTokenUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.ConverterUtil
import timber.log.Timber

class SplashPresenter(override val view: SplashContract.View,
                      private val sharedPrefRepository: SharedPrefRepository,
                      private val postRefreshTokenUseCase: PostRefreshTokenUseCase
) : SplashContract.Presenter {

    override fun initDelay() {
        Handler().postDelayed(AnimationHandler(), 800)
        Handler().postDelayed(SplashHandler(), 3000)
    }

    inner class AnimationHandler : Runnable {
        override fun run() {
            view.animation()
        }
    }

    inner class SplashHandler : Runnable {
        override fun run() {
            if (sharedPrefRepository.getPrefsBooleanValue(Consts.IS_FIRST_USER, true)) {
                sharedPrefRepository.writePrefs(Consts.IS_FIRST_USER, false)
                view.navigateToOnBoarding()
            }
            else if (sharedPrefRepository.getPrefsBooleanValue(Consts.AUTO_LOGIN, false)) {
                if (sharedPrefRepository.getPrefsBooleanValue(Consts.IS_GUEST, false))
                    view.navigateToMain()
                else {
                    postRefreshTokenUseCase.postRefreshToken()
                        .subscribe({
                            sharedPrefRepository.writePrefs(Consts.ACCESS_TOKEN, ConverterUtil._Encode(it.token))
                            sharedPrefRepository.writePrefs(Consts.REFRESH_TOKEN, ConverterUtil._Encode(it.token))
                            if (it.isAdditionalInfoNeeded)
                                view.navigateToSignUp()
                            else
                                view.navigateToMain()
                        },{
                            Timber.e(it.localizedMessage)
                        })
                }
            }
            else
                view.navigateToLogin()
        }
    }
}