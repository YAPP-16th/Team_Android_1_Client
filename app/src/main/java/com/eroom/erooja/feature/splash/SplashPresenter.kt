package com.eroom.erooja.feature.splash

import android.os.Handler
import com.eroom.domain.api.usecase.auth.GetRefreshTokenUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.ConverterUtil
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SplashPresenter(override val view: SplashContract.View,
                      private val sharedPrefRepository: SharedPrefRepository,
                      private val postRefreshTokenUseCase: GetRefreshTokenUseCase
) : SplashContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

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
                    postRefreshTokenUseCase.getRefreshToken()
                        .subscribe({
                            sharedPrefRepository.writePrefs(Consts.ACCESS_TOKEN, ConverterUtil._Encode(it.token))
                            sharedPrefRepository.writePrefs(Consts.REFRESH_TOKEN, ConverterUtil._Encode(it.refreshToken))
                            if (it.isAdditionalInfoNeeded)
                                view.navigateToSignUp()
                            else
                                view.navigateToMain()
                        },{
                            Timber.e(it.localizedMessage)
                            view.navigateToLogin()
                        }) addTo compositeDisposable
                }
            }
            else
                view.navigateToLogin()
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}