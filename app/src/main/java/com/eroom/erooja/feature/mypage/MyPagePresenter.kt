package com.eroom.erooja.feature.mypage

import android.annotation.SuppressLint
import com.eroom.data.entity.JobClass
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

    @SuppressLint("CheckResult")
    override fun getMemberJobInterest() {
        getMemberJobInterestUseCase.getMemberJobInterests()
            .subscribe({
                val classList = ArrayList<JobClass>()
                for (group in it) {
                    group.jobInterests.map { jobClass ->
                        classList.add(jobClass)
                    }
                }
                view.setJobInterestInfo(classList)
            }, {
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable

    }

    fun isGuest() = sharedPrefRepository.getPrefsBooleanValue(Consts.IS_GUEST)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}