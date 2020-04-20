package com.eroom.erooja.feature.signup.page.nickname

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.PostNicknameDuplicityUseCase
import timber.log.Timber

class NicknamePresenter(
    override val view: NicknameContract.View,
    private val getNicknameDuplicityUseCase: PostNicknameDuplicityUseCase
) : NicknameContract.Presenter {

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
            },{
                Timber.e(it.localizedMessage)
                view.unsetValidatedNickname()
                view.unsetDuplicatedNickname()
                view.hideCheckImage()
                view.hideErrorImage()
            })
    }
}