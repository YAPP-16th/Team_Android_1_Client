package com.eroom.erooja.feature.signup.page.nickname

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.PostNicknameDuplicityUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class NicknamePresenter(
    override val view: NicknameContract.View,
    private val getNicknameDuplicityUseCase: PostNicknameDuplicityUseCase
) : NicknameContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun checkNickname(nickname: String) {
        getNicknameDuplicityUseCase.postCheckNickname(nickname)
            .subscribe({
                if (it) {
                    view.hideCheckImage()
                    view.showErrorImage()
                    view.unsetValidatedNickname()
                    view.setDuplicatedNickname()
                } else {
                    view.hideErrorImage()
                    view.showCheckImage()
                    view.setValidatedNickname()
                    view.unsetDuplicatedNickname()
                }
            }, {
                Timber.e(it.localizedMessage)
                view.unsetValidatedNickname()
                view.unsetDuplicatedNickname()
                view.hideCheckImage()
                view.hideErrorImage()
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}