package com.eroom.erooja.feature.mypage

import com.eroom.domain.api.usecase.member.GetMemberInfoUseCase
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.addTo
import com.eroom.erooja.feature.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class MyPagePresenter(
    override val view: MyPageContract.View,
    private val sharedPrefRepository: SharedPrefRepository,
    private val getMemberInfoUseCase: GetMemberInfoUseCase,
    private val getMemberJobInterestUseCase: GetMemberJobInterestsUseCase
) : MyPageContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

    override fun getUserInfo() {
        getMemberInfoUseCase.getUserInfo()
            .subscribe({
                view.setNickname(it.nickname)
            }, {
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun getMemberJobInterest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isGuest() = sharedPrefRepository.getPrefsBooleanValue(Consts.IS_GUEST)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}