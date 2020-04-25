package com.eroom.erooja.feature.signup.kakao

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.PutJobInterestsUseCase
import com.eroom.domain.api.usecase.member.PutNicknameUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class KakaoSignUpPresenter(
    override val view: KakaoSignUpContract.View,
    private val putNicknameUseCase: PutNicknameUseCase,
    private val putJobInterestsUseCase: PutJobInterestsUseCase,
    private val sharedPrefRepository: SharedPrefRepository
) : KakaoSignUpContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun requestUserInfo(nickname: String, ids: ArrayList<Long>) {
        putNicknameUseCase.putNickname(nickname)
            .subscribe({
                requestJobInterests(ids)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    private fun requestJobInterests(ids: ArrayList<Long>) {
        putJobInterestsUseCase.putJobInterests(ids)
            .subscribe({
                sharedPrefRepository.writePrefs(Consts.AUTO_LOGIN, true)
                sharedPrefRepository.writePrefs(Consts.IS_GUEST, false)
                view.navigateToMain()
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}