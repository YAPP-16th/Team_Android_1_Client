package com.eroom.erooja.feature.setting.setting_nickname

import com.eroom.domain.api.usecase.member.PostNicknameDuplicityUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class NicknameChangePresenter (override var view: NicknameChangeContract.View,
                               private val getNicknameDuplicityUseCase: PostNicknameDuplicityUseCase
)
    :NicknameChangeContract.Presenter {

    private val compositeDisposable = CompositeDisposable()


    override fun checkNickname(nickname: String) {
        getNicknameDuplicityUseCase.postCheckNickname(nickname)
            .subscribe({
                view.hideCheckImage()
                view.showErrorImage()
                view.unsetValidatedNickname()
                view.setDuplicatedNickname()
            },{
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