package com.eroom.erooja.app.feature.setting.setting_nickname

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.GetMemberInfoUseCase
import com.eroom.domain.api.usecase.member.PostMemberInfoUseCase
import com.eroom.domain.api.usecase.member.PostNicknameDuplicityUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class NicknameChangePresenter(
    override var view: NicknameChangeContract.View,
    private val getNicknameDuplicityUseCase: PostNicknameDuplicityUseCase,
    private val getMemberInfoUseCase: GetMemberInfoUseCase,
    private val postMemberInfoUseCase: PostMemberInfoUseCase
) : NicknameChangeContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    //false - 중복 없음, true - 중복 존재
    override fun checkNickname(nickname: String) {
        getNicknameDuplicityUseCase.postCheckNickname(nickname)
            .subscribe({
                if (it) view.nicknameDuplicationError()
                else view.nicknameDuplicationPass()
            }, {
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    @SuppressLint("CheckResult")
    override fun getMyNickname() {
        getMemberInfoUseCase.getUserInfo()
            .subscribe({
                view.setMyNickname(it.nickname)
            }, {
                it.localizedMessage
            })
    }

    @SuppressLint("CheckResult")
    override fun updateNickname(nickname: String) {
        postMemberInfoUseCase.postMemberInfo(nickname)
            .subscribe({
                Timber.i(it.nickname)
            }, {
                it.localizedMessage
            })
    }
}