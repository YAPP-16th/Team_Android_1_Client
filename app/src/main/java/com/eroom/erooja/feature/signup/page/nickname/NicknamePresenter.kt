package com.eroom.erooja.feature.signup.page.nickname

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.GetNicknameDuplicityUseCase
import timber.log.Timber

class NicknamePresenter(
    override val view: NicknameContract.View,
    private val getNicknameDuplicityUseCase: GetNicknameDuplicityUseCase
) : NicknameContract.Presenter {

    @SuppressLint("CheckResult")
    fun checkNickname(nickname: String) {
        getNicknameDuplicityUseCase.postKakaoLogin(nickname)
            .subscribe({
                if (it) {
                    view.hideErrorImage()
                    view.showErrorImage()
                    view.unsetValidatedNickname()
                } else {
                    view.hideErrorImage()
                    view.showCheckImage()
                    view.setValidatedNickname()
                }
            },{
                Timber.e(it.localizedMessage)
                view.unsetValidatedNickname()
            })
    }
}