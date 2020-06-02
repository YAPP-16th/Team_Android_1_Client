package com.eroom.erooja.feature.splash

import android.annotation.SuppressLint
import android.os.Handler
import com.eroom.domain.api.usecase.auth.GetRefreshTokenUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.ConverterUtil
import com.eroom.domain.utils.addTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SplashPresenter(
    override val view: SplashContract.View,
    private val sharedPrefRepository: SharedPrefRepository,
    private val postRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val getJobGroupUseCase: GetJobGroupUseCase,
    private val getJobGroupAndClassUseCase: GetJobGroupAndClassUseCase
) : SplashContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

    override fun initDelay() {
        Handler().postDelayed(AnimationHandler(), 800)
    }

    inner class AnimationHandler : Runnable {
        override fun run() {
            view.animation()
            getJobGroups()
            Handler().postDelayed(SplashHandler(), 2800)
        }
    }

    inner class SplashHandler : Runnable {
        override fun run() {
            if (sharedPrefRepository.getPrefsBooleanValue(Consts.IS_FIRST_USER, true)) {
                sharedPrefRepository.writePrefs(Consts.IS_FIRST_USER, false)
                view.navigateToOnBoarding()
            } else if (sharedPrefRepository.getPrefsBooleanValue(Consts.AUTO_LOGIN, false)) {
                if (sharedPrefRepository.getPrefsBooleanValue(Consts.IS_GUEST, false))
                    view.navigateToMain()
                else {
                    postRefreshTokenUseCase.getRefreshToken()
                        .subscribe({
                            sharedPrefRepository.writePrefs(
                                Consts.ACCESS_TOKEN,
                                ConverterUtil._Encode(it.token)
                            )
                            sharedPrefRepository.writePrefs(
                                Consts.REFRESH_TOKEN,
                                ConverterUtil._Encode(it.refreshToken)
                            )
                            if (it.isAdditionalInfoNeeded)
                                view.navigateToSignUp()
                            else
                                view.navigateToMain()
                        }, {
                            Timber.e(it.localizedMessage)
                            view.navigateToLogin()
                        }) addTo compositeDisposable
                }
            } else
                view.navigateToLogin()
        }
    }

    @SuppressLint("CheckResult")
    override fun getJobGroups() {
        getJobGroupUseCase.getJobGroup()
            .subscribe({
                view.reRequestClassByGroup(it)
            }, {
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getJobGroupAndClasses(groupIds: List<Long>) {
        Observable.fromIterable(groupIds)
            .flatMap { getJobGroupAndClassUseCase.getJobGroupAndClass(it) }
            .map {
                it
            }.toList()
            .subscribe({
                view.updateJobGroupAndClass(it) //사용자의 직무, 직군 불러옴
            }, {
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}